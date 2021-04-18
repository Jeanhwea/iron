package com.avic.mti.iron.tool.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.exception.WrongParameterFormatException;
import com.avic.mti.iron.common.helper.NameCodeHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.symbol.tool.ToolOutCateEnum;
import com.avic.mti.iron.common.symbol.tool.ToolOutFlagEnum;
import com.avic.mti.iron.common.symbol.tool.ToolOutStatusEnum;
import com.avic.mti.iron.common.symbol.tool.ToolOutTypeEnum;
import com.avic.mti.iron.ledger.service.LedgerStubDetailService;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import com.avic.mti.iron.tool.domain.repo.ToolOutRepository;
import com.avic.mti.iron.tool.helper.ToolOutHelper;
import com.avic.mti.iron.tool.service.ToolInService;
import com.avic.mti.iron.tool.service.ToolLogService;
import com.avic.mti.iron.tool.service.ToolOutService;
import com.avic.mti.iron.tool.service.ToolShelfService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ToolOutServiceImpl implements ToolOutService {

  public static final Logger logger = LoggerFactory.getLogger(ToolOutServiceImpl.class);

  private final ToolOutRepository toolOutRepository;

  private final ToolInService toolInService;
  private final ToolShelfService toolShelfService;
  private final LedgerStubDetailService ledgerStubDetailService;

  private final ToolLogService toolLogService;

  private final MesConditionBuilder<ToolOut> mesConditionBuilder;

  @Autowired
  public ToolOutServiceImpl(
      ToolOutRepository toolOutRepository,
      ToolInService toolInService,
      ToolShelfService toolShelfService,
      LedgerStubDetailService ledgerStubDetailService,
      ToolLogService toolLogService,
      MesConditionBuilder<ToolOut> mesConditionBuilder) {
    this.toolOutRepository = toolOutRepository;
    this.toolInService = toolInService;
    this.toolShelfService = toolShelfService;
    this.ledgerStubDetailService = ledgerStubDetailService;
    this.toolLogService = toolLogService;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateToolOut(ToolOut toolOut) {
    if (ToolOutStatusEnum.of(toolOut.outStatus()) == ToolOutStatusEnum.Unknown) {
      throw new UnknownEnumException("outStatus", toolOut.outStatus(), ToolOutStatusEnum.expect());
    }

    if (ToolOutCateEnum.of(toolOut.outCate()) == ToolOutCateEnum.Unknown) {
      throw new UnknownEnumException("outCate", toolOut.outCate(), ToolOutCateEnum.expect());
    }

    if (ToolOutTypeEnum.of(toolOut.outType()) == ToolOutTypeEnum.Unknown) {
      throw new UnknownEnumException("outType", toolOut.outType(), ToolOutTypeEnum.expect());
    }

    if (ToolOutFlagEnum.of(toolOut.outFlag()) == ToolOutFlagEnum.Unknown) {
      throw new UnknownEnumException("outFlag", toolOut.outFlag(), ToolOutFlagEnum.expect());
    }

    if (NameCodeHelper.validateNameCode(toolOut.creatorNC())) {
      throw new WrongParameterFormatException("创建人", "名称[编码]", toolOut.creatorNC());
    }
  }

  private void checkBeforeCreate(ToolOut toolOut) {
    // pass
  }

  private void checkBeforeReplace(ToolOut toolOut) {
    // pass
  }

  private void checkBeforeDelete(ToolOut toolOut) {
    // pass
  }

  private void syncWhenCreate(ToolOut toolOut) {
    ToolIn toolIn = this.toolInService.exitToolIn(toolOut);
    // List<ToolShelf> shelfList = this.toolShelfService.exitShelfList(toolOut);
    // LedgerStubDetail stubDetail = this.ledgerStubDetailService.exitStubDetail(toolOut);
    logger.info("创建工具出库 {} 时，影响了工具入库 {}", toolOut, toolIn);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ToolOut> findToolOuts(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<ToolOut> builder = this.mesConditionBuilder.init(params, fields);
    return this.toolOutRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<ToolOut> findAllToolOuts(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<ToolOut> builder = this.mesConditionBuilder.init(params, fields);
    return this.toolOutRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public ToolOut findById(long toolOutId) {
    return this.toolOutRepository
        .fetchById(toolOutId)
        .orElseThrow(() -> new DatumNotExistException("ToolOut", toolOutId));
  }

  @Override
  public ToolOut createToolOut(Map<String, Object> params) {
    ToolOut newToolOut = ToolOutHelper.assignToolOut(new ToolOut(), params);

    this.checkBeforeCreate(newToolOut);
    this.validateToolOut(newToolOut);

    ToolOut savedToolOut = this.toolOutRepository.save(newToolOut);
    syncWhenCreate(savedToolOut);

    this.toolLogService.createToolLogForOut(savedToolOut);
    return savedToolOut;
  }

  @Override
  public ToolOut replaceToolOut(long toolOutId, Map<String, Object> params) {
    ToolOut prevToolOut = this.findById(toolOutId);

    this.checkBeforeReplace(prevToolOut);
    ToolOut currToolOut = ToolOutHelper.assignToolOut(prevToolOut, params);
    this.validateToolOut(currToolOut);
    return this.toolOutRepository.save(currToolOut);
  }

  @Override
  public void deleteToolOut(long toolOutId) {
    ToolOut prevToolOut = this.findById(toolOutId);
    this.checkBeforeDelete(prevToolOut);
    this.toolOutRepository.delete(prevToolOut);
  }

  @Override
  public List<ToolOut> createToolOutInBulk(List<Map<String, Object>> paramList) {
    List<ToolOut> toolOutList =
        paramList.stream().map(this::createToolOut).collect(Collectors.toList());
    logger.debug("创建了 {} 条出库记录", toolOutList.size());
    return toolOutList;
  }
}
