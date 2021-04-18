package com.avic.mti.iron.measure.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.DateTimeHelper;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.measure.MeasInCateEnum;
import com.avic.mti.iron.common.symbol.measure.MeasInTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import com.avic.mti.iron.measure.domain.repo.MeasInRepository;
import com.avic.mti.iron.measure.domain.repo.MeasOutRepository;
import com.avic.mti.iron.measure.helper.MeasInHelper;
import com.avic.mti.iron.measure.service.MeasInService;
import com.avic.mti.iron.measure.service.MeasLogService;
import com.avic.mti.iron.measure.service.MeasShelfService;
import com.avic.mti.iron.proxy.service.ProxyEncoderService;
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
public class MeasInServiceImpl implements MeasInService {

  public static final Logger logger = LoggerFactory.getLogger(MeasInServiceImpl.class);

  private final MeasInRepository measInRepository;
  private final MeasOutRepository measOutRepository;

  private final MeasLogService measLogService;
  private final MeasShelfService measShelfService;

  private final ProxyEncoderService proxyEncoderService;

  private final MesConditionBuilder<MeasIn> mesConditionBuilder;

  @Autowired
  public MeasInServiceImpl(
      MeasInRepository measInRepository,
      MeasOutRepository measOutRepository,
      MeasLogService measLogService,
      MeasShelfService measShelfService,
      ProxyEncoderService proxyEncoderService,
      MesConditionBuilder<MeasIn> mesConditionBuilder) {
    this.measInRepository = measInRepository;
    this.measOutRepository = measOutRepository;
    this.measLogService = measLogService;
    this.measShelfService = measShelfService;
    this.proxyEncoderService = proxyEncoderService;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateMeasIn(MeasIn measIn) {
    if (MeasInCateEnum.of(measIn.inCate()) == MeasInCateEnum.Unknown) {
      throw new UnknownEnumException("inCate", measIn.inCate(), MeasInCateEnum.expect());
    }

    if (MeasInTypeEnum.of(measIn.inType()) == MeasInTypeEnum.Unknown) {
      throw new UnknownEnumException("inType", measIn.inType(), MeasInTypeEnum.expect());
    }
  }

  private void checkBeforeCreate(MeasIn measIn) {
    if (null == measIn.inCode()) {
      String inCode = this.proxyEncoderService.nextval("MEAS_IN");
      measIn.inCode(inCode);
    }
  }

  private void checkBeforeReplace(MeasIn measIn) {
    // pass
  }

  private void checkBeforeDelete(MeasIn measIn) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MeasIn> findMeasIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MeasIn> builder = this.mesConditionBuilder.init(params, fields);
    return this.measInRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<MeasIn> findAllMeasIns(Map<String, Object> params) {
    List<String> measCodes =
        ParamReader.init(params)
            .listStringFromKey("measCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 measCodes 参数"));
    return this.measInRepository.findAllByInCodeIn(measCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MeasIn> findAllMeasIns(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MeasIn> builder = this.mesConditionBuilder.init(params, fields);
    return this.measInRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public MeasIn findById(long measInId) {
    return this.measInRepository
        .fetchById(measInId)
        .orElseThrow(() -> new DatumNotExistException("MeasIn", measInId));
  }

  @Override
  public MeasIn createMeasIn(Map<String, Object> params) {
    MeasIn newMeasIn = MeasInHelper.assignMeasIn(new MeasIn(), params);

    this.checkBeforeCreate(newMeasIn);

    boolean conflict = this.measInRepository.findFirstByInCode(newMeasIn.inCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("工具入库编码 {0} 已经存在", newMeasIn.inCode());
    }

    this.validateMeasIn(newMeasIn);

    MeasIn savedMeasIn = this.measInRepository.save(newMeasIn);

    // List<MeasShelf> shelfList = this.measShelfService.enterShelfList(savedMeasIn);
    // logger.info("工具入库时 {} 更新库位信息为 shelfList = {}", savedMeasIn, shelfList);

    this.measLogService.createMeasLogForIn(savedMeasIn);

    return savedMeasIn;
  }

  @Override
  public MeasIn replaceMeasIn(long measInId, Map<String, Object> params) {
    MeasIn prevMeasIn = this.findById(measInId);

    ParamReader reader = ParamReader.init(params);
    String newMeasInCode = reader.stringFromKey("measCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newMeasInCode)
            && !newMeasInCode.equals(prevMeasIn.inCode())
            && this.measInRepository.findFirstByInCode(newMeasInCode).isPresent();
    if (conflict) {
      throw new BadRequestException("工具入库编码 {0} 已经存在", newMeasInCode);
    }

    this.checkBeforeReplace(prevMeasIn);
    MeasIn currMeasIn = MeasInHelper.assignMeasIn(prevMeasIn, params);
    this.validateMeasIn(currMeasIn);
    return this.measInRepository.save(currMeasIn);
  }

  @Override
  public void deleteMeasIn(long measInId) {
    MeasIn prevMeasIn = this.findById(measInId);
    this.checkBeforeDelete(prevMeasIn);
    this.measInRepository.delete(prevMeasIn);
  }

  @Override
  public MeasIn exitMeasIn(MeasOut measOut) {
    MeasIn measIn =
        this.measInRepository
            .findById(measOut.inId())
            .orElseThrow(() -> new DatumNotExistException("MeasIn", measOut.inId()));

    // logger.debug("开始修改入库 {} 的库位信息: {}", measIn, measIn.shelfJson());
    // MeasIn newMeasIn = MeasInHelper.exitMeasIn2(measOut.updateUser(), measIn, measOut);
    // logger.debug("完成修改入库 {} 的库位信息: {}", newMeasIn, newMeasIn.shelfJson());

    MeasIn newMeasIn =
        MeasInHelper.updateNumbers(measIn, -measOut.planEnum(), -measOut.planEnum(), 0L);
    return this.measInRepository.save(newMeasIn);
  }

  @Override
  public void updateNumbers(long measInId, long deltaStkNum, long deltaAvlNum, long deltaPlnNum) {
    MeasIn measIn =
        this.measInRepository
            .findById(measInId)
            .orElseThrow(() -> new DatumNotExistException("MeasIn", measInId));

    measIn = MeasInHelper.updateNumbers(measIn, deltaStkNum, deltaAvlNum, deltaPlnNum);

    this.measInRepository.save(measIn);
  }

  @Override
  public MeasIn returnMeasIn(Map<String, Object> params) {
    MeasIn newMeasIn = MeasInHelper.assignMeasIn(new MeasIn(), params);

    boolean conflict = this.measInRepository.findFirstByInCode(newMeasIn.inCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("工具入库编码 {0} 已经存在", newMeasIn.inCode());
    }

    this.checkBeforeCreate(newMeasIn);
    this.validateMeasIn(newMeasIn);

    MeasIn savedMeasIn = this.measInRepository.save(newMeasIn);
    logger.debug("开始归还入库时修改入库记录创建 {}", savedMeasIn);

    // List<MeasShelf> shelfList = this.measShelfService.enterShelfList(savedMeasIn);
    // logger.info("工具入库时 {} 更新库位信息为 shelfList = {}", savedMeasIn, shelfList);

    this.measLogService.createMeasLogForIn(savedMeasIn);

    ParamReader reader = ParamReader.init(params);
    long outId =
        reader.longFromKey("outId").orElseThrow(() -> new BadRequestException("缺少 outId 参数"));
    String updateUser =
        reader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少 updateUser 参数"));
    MeasOut measOut =
        measOutRepository
            .fetchById(outId)
            .orElseThrow(() -> new BadRequestException("归还入库时对应的出库 {0,number,#} 不存在", outId));
    measOut.finishOperNC(updateUser);
    measOut.finishDate(DateTimeHelper.now());
    this.measOutRepository.save(measOut);
    logger.debug("归还入库时修改出库记录 {}", measOut);

    return savedMeasIn;
  }
}
