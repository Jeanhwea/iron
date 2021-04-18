package com.avic.mti.iron.measure.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.exception.WrongParameterFormatException;
import com.avic.mti.iron.common.helper.NameCodeHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.symbol.measure.MeasOutCateEnum;
import com.avic.mti.iron.common.symbol.measure.MeasOutFlagEnum;
import com.avic.mti.iron.common.symbol.measure.MeasOutStatusEnum;
import com.avic.mti.iron.common.symbol.measure.MeasOutTypeEnum;
import com.avic.mti.iron.ledger.service.LedgerStubDetailService;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import com.avic.mti.iron.measure.domain.repo.MeasOutRepository;
import com.avic.mti.iron.measure.helper.MeasOutHelper;
import com.avic.mti.iron.measure.service.MeasInService;
import com.avic.mti.iron.measure.service.MeasLogService;
import com.avic.mti.iron.measure.service.MeasOutService;
import com.avic.mti.iron.measure.service.MeasShelfService;
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
public class MeasOutServiceImpl implements MeasOutService {

  public static final Logger logger = LoggerFactory.getLogger(MeasOutServiceImpl.class);

  private final MeasOutRepository measOutRepository;

  private final MeasInService measInService;
  private final MeasShelfService measShelfService;
  private final LedgerStubDetailService ledgerStubDetailService;

  private final MeasLogService measLogService;

  private final MesConditionBuilder<MeasOut> mesConditionBuilder;

  @Autowired
  public MeasOutServiceImpl(
      MeasOutRepository measOutRepository,
      MeasInService measInService,
      MeasShelfService measShelfService,
      LedgerStubDetailService ledgerStubDetailService,
      MeasLogService measLogService,
      MesConditionBuilder<MeasOut> mesConditionBuilder) {
    this.measOutRepository = measOutRepository;
    this.measInService = measInService;
    this.measShelfService = measShelfService;
    this.ledgerStubDetailService = ledgerStubDetailService;
    this.measLogService = measLogService;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateMeasOut(MeasOut measOut) {
    if (MeasOutStatusEnum.of(measOut.outStatus()) == MeasOutStatusEnum.Unknown) {
      throw new UnknownEnumException("outStatus", measOut.outStatus(), MeasOutStatusEnum.expect());
    }

    if (MeasOutCateEnum.of(measOut.outCate()) == MeasOutCateEnum.Unknown) {
      throw new UnknownEnumException("outCate", measOut.outCate(), MeasOutCateEnum.expect());
    }

    if (MeasOutTypeEnum.of(measOut.outType()) == MeasOutTypeEnum.Unknown) {
      throw new UnknownEnumException("outType", measOut.outType(), MeasOutTypeEnum.expect());
    }

    if (MeasOutFlagEnum.of(measOut.outFlag()) == MeasOutFlagEnum.Unknown) {
      throw new UnknownEnumException("outFlag", measOut.outFlag(), MeasOutFlagEnum.expect());
    }

    if (NameCodeHelper.validateNameCode(measOut.creatorNC())) {
      throw new WrongParameterFormatException("创建人", "名称[编码]", measOut.creatorNC());
    }
  }

  private void checkBeforeCreate(MeasOut measOut) {
    // pass
  }

  private void checkBeforeReplace(MeasOut measOut) {
    // pass
  }

  private void checkBeforeDelete(MeasOut measOut) {
    // pass
  }

  private void syncWhenCreate(MeasOut measOut) {
    MeasIn measIn = this.measInService.exitMeasIn(measOut);
    // List<MeasShelf> shelfList = this.measShelfService.exitShelfList(measOut);
    // LedgerStubDetail stubDetail = this.ledgerStubDetailService.exitStubDetail(measOut);
    logger.info("创建工具出库 {} 时，影响了工具入库 {}", measOut, measIn);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MeasOut> findMeasOuts(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MeasOut> builder = this.mesConditionBuilder.init(params, fields);
    return this.measOutRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<MeasOut> findAllMeasOuts(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MeasOut> builder = this.mesConditionBuilder.init(params, fields);
    return this.measOutRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public MeasOut findById(long measOutId) {
    return this.measOutRepository
        .fetchById(measOutId)
        .orElseThrow(() -> new DatumNotExistException("MeasOut", measOutId));
  }

  @Override
  public MeasOut createMeasOut(Map<String, Object> params) {
    MeasOut newMeasOut = MeasOutHelper.assignMeasOut(new MeasOut(), params);

    this.checkBeforeCreate(newMeasOut);
    this.validateMeasOut(newMeasOut);

    MeasOut savedMeasOut = this.measOutRepository.save(newMeasOut);
    syncWhenCreate(savedMeasOut);

    this.measLogService.createMeasLogForOut(savedMeasOut);
    return savedMeasOut;
  }

  @Override
  public MeasOut replaceMeasOut(long measOutId, Map<String, Object> params) {
    MeasOut prevMeasOut = this.findById(measOutId);

    this.checkBeforeReplace(prevMeasOut);
    MeasOut currMeasOut = MeasOutHelper.assignMeasOut(prevMeasOut, params);
    this.validateMeasOut(currMeasOut);
    return this.measOutRepository.save(currMeasOut);
  }

  @Override
  public void deleteMeasOut(long measOutId) {
    MeasOut prevMeasOut = this.findById(measOutId);
    this.checkBeforeDelete(prevMeasOut);
    this.measOutRepository.delete(prevMeasOut);
  }

  @Override
  public List<MeasOut> createMeasOutInBulk(List<Map<String, Object>> paramList) {
    List<MeasOut> measOutList =
        paramList.stream().map(this::createMeasOut).collect(Collectors.toList());
    logger.debug("创建了 {} 条出库记录", measOutList.size());
    return measOutList;
  }
}
