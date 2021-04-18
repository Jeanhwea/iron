package com.avic.mti.iron.tool.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.tool.ToolInCateEnum;
import com.avic.mti.iron.common.symbol.tool.ToolInTypeEnum;
import com.avic.mti.iron.proxy.service.ProxyEncoderService;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import com.avic.mti.iron.tool.domain.repo.ToolInRepository;
import com.avic.mti.iron.tool.helper.ToolInHelper;
import com.avic.mti.iron.tool.service.ToolInService;
import com.avic.mti.iron.tool.service.ToolLogService;
import com.avic.mti.iron.tool.service.ToolShelfService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ToolInServiceImpl implements ToolInService {

  public static final Logger logger = LoggerFactory.getLogger(ToolInServiceImpl.class);

  private final ToolInRepository toolInRepository;

  private final ToolLogService toolLogService;
  private final ToolShelfService toolShelfService;
  private final ProxyEncoderService proxyEncoderService;

  private final MesConditionBuilder<ToolIn> mesConditionBuilder;

  @Autowired
  public ToolInServiceImpl(
      ToolInRepository toolInRepository,
      ToolLogService toolLogService,
      ToolShelfService toolShelfService,
      ProxyEncoderService proxyEncoderService,
      MesConditionBuilder<ToolIn> mesConditionBuilder) {
    this.toolInRepository = toolInRepository;
    this.toolLogService = toolLogService;
    this.toolShelfService = toolShelfService;
    this.proxyEncoderService = proxyEncoderService;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateToolIn(ToolIn toolIn) {
    if (ToolInCateEnum.of(toolIn.inCate()) == ToolInCateEnum.Unknown) {
      throw new UnknownEnumException("inCate", toolIn.inCate(), ToolInCateEnum.expect());
    }

    if (ToolInTypeEnum.of(toolIn.inType()) == ToolInTypeEnum.Unknown) {
      throw new UnknownEnumException("inType", toolIn.inType(), ToolInTypeEnum.expect());
    }
  }

  private void checkBeforeCreate(ToolIn toolIn) {
    if (null == toolIn.inCode()) {
      String inCode = this.proxyEncoderService.nextval("TOOL_IN");
      toolIn.inCode(inCode);
    }
  }

  private void checkBeforeReplace(ToolIn toolIn) {
    // pass
  }

  private void checkBeforeDelete(ToolIn toolIn) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ToolIn> findToolIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<ToolIn> builder = this.mesConditionBuilder.init(params, fields);
    return this.toolInRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<ToolIn> findAllToolIns(Map<String, Object> params) {
    List<String> toolCodes =
        ParamReader.init(params)
            .listStringFromKey("toolCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 toolCodes 参数"));
    return this.toolInRepository.findAllByInCodeIn(toolCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ToolIn> findAllToolIns(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<ToolIn> builder = this.mesConditionBuilder.init(params, fields);
    return this.toolInRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public ToolIn findById(long toolInId) {
    return this.toolInRepository
        .fetchById(toolInId)
        .orElseThrow(() -> new DatumNotExistException("ToolIn", toolInId));
  }

  @Override
  public ToolIn createToolIn(Map<String, Object> params) {
    ToolIn newToolIn = ToolInHelper.assignToolIn(new ToolIn(), params);

    this.checkBeforeCreate(newToolIn);

    boolean conflict = this.toolInRepository.findFirstByInCode(newToolIn.inCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("工具入库编码 {0} 已经存在", newToolIn.inCode());
    }

    this.validateToolIn(newToolIn);

    ToolIn savedToolIn = this.toolInRepository.save(newToolIn);

    // List<ToolShelf> shelfList = this.toolShelfService.enterShelfList(savedToolIn);
    // logger.info("工具入库时 {} 更新库位信息为 shelfList = {}", savedToolIn, shelfList);

    this.toolLogService.createToolLogForIn(savedToolIn);

    return savedToolIn;
  }

  @Override
  public ToolIn replaceToolIn(long toolInId, Map<String, Object> params) {
    ToolIn prevToolIn = this.findById(toolInId);

    ParamReader reader = ParamReader.init(params);
    String newToolInCode = reader.stringFromKey("toolCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newToolInCode)
            && !newToolInCode.equals(prevToolIn.inCode())
            && this.toolInRepository.findFirstByInCode(newToolInCode).isPresent();
    if (conflict) {
      throw new BadRequestException("工具入库编码 {0} 已经存在", newToolInCode);
    }

    this.checkBeforeReplace(prevToolIn);
    ToolIn currToolIn = ToolInHelper.assignToolIn(prevToolIn, params);
    this.validateToolIn(currToolIn);
    return this.toolInRepository.save(currToolIn);
  }

  @Override
  public void deleteToolIn(long toolInId) {
    ToolIn prevToolIn = this.findById(toolInId);
    this.checkBeforeDelete(prevToolIn);
    this.toolInRepository.delete(prevToolIn);
  }

  @Override
  public ToolIn exitToolIn(ToolOut toolOut) {
    ToolIn toolIn =
        this.toolInRepository
            .findById(toolOut.inId())
            .orElseThrow(() -> new DatumNotExistException("ToolIn", toolOut.inId()));

    // logger.debug("开始修改入库 {} 的库位信息: {}", toolIn, toolIn.shelfJson());
    // ToolIn newToolIn = ToolInHelper.exitToolIn2(toolOut.updateUser(), toolIn, toolOut);
    // logger.debug("完成修改入库 {} 的库位信息: {}", newToolIn, newToolIn.shelfJson());

    ToolIn newToolIn =
        ToolInHelper.updateNumbers(toolIn, -toolOut.planEnum(), -toolOut.planEnum(), 0L);
    return this.toolInRepository.save(newToolIn);
  }

  @Override
  public void updateNumbers(long toolInId, long deltaStkNum, long deltaAvlNum, long deltaPlnNum) {
    ToolIn toolIn =
        this.toolInRepository
            .findById(toolInId)
            .orElseThrow(() -> new DatumNotExistException("ToolIn", toolInId));

    toolIn = ToolInHelper.updateNumbers(toolIn, deltaStkNum, deltaAvlNum, deltaPlnNum);

    this.toolInRepository.save(toolIn);
  }
}
