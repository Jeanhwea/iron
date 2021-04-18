package com.avic.mti.iron.main.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.exception.WrongParameterFormatException;
import com.avic.mti.iron.common.helper.NameCodeHelper;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.symbol.main.*;
import com.avic.mti.iron.ledger.domain.entity.LedgerStubDetail;
import com.avic.mti.iron.ledger.service.LedgerStubDetailService;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.MainOut;
import com.avic.mti.iron.main.domain.entity.MainShelf;
import com.avic.mti.iron.main.domain.repo.MainOutRepository;
import com.avic.mti.iron.main.helper.MainOutHelper;
import com.avic.mti.iron.main.service.MainInService;
import com.avic.mti.iron.main.service.MainLogService;
import com.avic.mti.iron.main.service.MainOutService;
import com.avic.mti.iron.main.service.MainShelfService;
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
public class MainOutServiceImpl implements MainOutService {

  public static final Logger logger = LoggerFactory.getLogger(MainOutServiceImpl.class);

  private final MainOutRepository mainOutRepository;

  private final MainInService mainInService;
  private final MainShelfService mainShelfService;
  private final LedgerStubDetailService ledgerStubDetailService;

  private final MainLogService mainLogService;

  private final MesConditionBuilder<MainOut> mesConditionBuilder;

  @Autowired
  public MainOutServiceImpl(
      MainOutRepository mainOutRepository,
      MainInService mainInService,
      MainShelfService mainShelfService,
      LedgerStubDetailService ledgerStubDetailService,
      MainLogService mainLogService,
      MesConditionBuilder<MainOut> mesConditionBuilder) {
    this.mainOutRepository = mainOutRepository;
    this.mainInService = mainInService;
    this.mainShelfService = mainShelfService;
    this.ledgerStubDetailService = ledgerStubDetailService;
    this.mainLogService = mainLogService;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateMainOut(MainOut mainOut) {
    if (StringHelper.isNonBlank(mainOut.outStatus())
        && MainOutStatusEnum.of(mainOut.outStatus()) == MainOutStatusEnum.Unknown) {
      throw new UnknownEnumException("outStatus", mainOut.outStatus(), MainOutStatusEnum.expect());
    }

    if (StringHelper.isNonBlank(mainOut.outCate())
        && MainOutCateEnum.of(mainOut.outCate()) == MainOutCateEnum.Unknown) {
      throw new UnknownEnumException("outCate", mainOut.outCate(), MainOutCateEnum.expect());
    }

    if (StringHelper.isNonBlank(mainOut.outType())
        && MainOutTypeEnum.of(mainOut.outType()) == MainOutTypeEnum.Unknown) {
      throw new UnknownEnumException("outType", mainOut.outType(), MainOutTypeEnum.expect());
    }

    if (StringHelper.isNonBlank(mainOut.outFlag())
        && MainOutFlagEnum.of(mainOut.outFlag()) == MainOutFlagEnum.Unknown) {
      throw new UnknownEnumException("outFlag", mainOut.outFlag(), MainOutFlagEnum.expect());
    }

    if (NameCodeHelper.validateNameCode(mainOut.creatorNC())) {
      throw new WrongParameterFormatException("创建人", "名称[编码]", mainOut.creatorNC());
    }
  }

  private void checkBeforeCreate(MainOut mainOut) {
    // pass
  }

  private void checkBeforeReplace(MainOut mainOut) {
    // pass
  }

  private void checkBeforeDelete(MainOut mainOut) {
    // pass
  }

  private void syncWhenCreate(MainOut mainOut) {
    MainIn mainIn = this.mainInService.exitMainIn(mainOut);
    List<MainShelf> shelfList = this.mainShelfService.exitShelfList(mainOut);
    LedgerStubDetail stubDetail = this.ledgerStubDetailService.exitStubDetail(mainOut);
    logger.info(
        "创建主材出库 {} 时，影响了主材入库 {} 和主材库位 {} 和主材出库单明细 {}", mainOut, mainIn, shelfList, stubDetail);
  }

  private void syncWhenCreate2(MainOut mainOut) {
    MainIn mainIn = this.mainInService.exitMainIn(mainOut);
    List<MainShelf> shelfList = this.mainShelfService.exitShelfList(mainOut);
    // LedgerStubDetail stubDetail = this.ledgerStubDetailService.exitStubDetail(mainOut);
    logger.info("创建主材出库 {} 时，影响了主材入库 {} 和主材库位 {}", mainOut, mainIn, shelfList);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MainOut> findMainOuts(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MainOut> builder = this.mesConditionBuilder.init(params, fields);
    return this.mainOutRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<MainOut> findAllMainOuts(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MainOut> builder = this.mesConditionBuilder.init(params, fields);
    return this.mainOutRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public MainOut findById(long mainOutId) {
    return this.mainOutRepository
        .fetchById(mainOutId)
        .orElseThrow(() -> new DatumNotExistException("MainOut", mainOutId));
  }

  @Override
  public MainOut createMainOut(Map<String, Object> params) {
    MainOut newMainOut = MainOutHelper.assignMainOut(new MainOut(), params);

    this.checkBeforeCreate(newMainOut);
    this.validateMainOut(newMainOut);

    MainOut savedMainOut = this.mainOutRepository.save(newMainOut);
    syncWhenCreate(savedMainOut);

    this.mainLogService.createMainLogForOut(savedMainOut);
    return savedMainOut;
  }

  @Override
  public MainOut createMainOut2(Map<String, Object> params) {
    MainOut newMainOut = MainOutHelper.assignMainOut(new MainOut(), params);

    this.checkBeforeCreate(newMainOut);
    this.validateMainOut(newMainOut);

    MainOut savedMainOut = this.mainOutRepository.save(newMainOut);
    syncWhenCreate2(savedMainOut);

    this.mainLogService.createMainLogForOut(savedMainOut);
    return savedMainOut;
  }

  @Override
  public MainOut replaceMainOut(long mainOutId, Map<String, Object> params) {
    MainOut prevMainOut = this.findById(mainOutId);

    this.checkBeforeReplace(prevMainOut);
    MainOut currMainOut = MainOutHelper.assignMainOut(prevMainOut, params);
    this.validateMainOut(currMainOut);
    return this.mainOutRepository.save(currMainOut);
  }

  @Override
  public void deleteMainOut(long mainOutId) {
    MainOut prevMainOut = this.findById(mainOutId);
    this.checkBeforeDelete(prevMainOut);
    this.mainOutRepository.delete(prevMainOut);
  }

  @Override
  public List<MainOut> createMainOutInBulk(List<Map<String, Object>> paramList) {
    List<MainOut> mainOutList =
        paramList.stream().map(this::createMainOut).collect(Collectors.toList());
    logger.debug("主材制单出库时，创建了 {} 条出库记录", mainOutList.size());
    return mainOutList;
  }

  @Override
  public List<MainOut> createMainOutInBulk2(List<Map<String, Object>> paramList) {
    List<MainOut> mainOutList =
        paramList.stream().map(this::createMainOut2).collect(Collectors.toList());
    logger.debug("主材不制单出库时，创建了 {} 条出库记录", mainOutList.size());
    return mainOutList;
  }

  @Override
  public List<MainOut> transferMainOutInBulk(
      List<Map<String, Object>> paramOutList, Map<String, Object> mainInParam) {
    List<MainOut> mainOutList =
        paramOutList.stream().map(this::createMainOut2).collect(Collectors.toList());
    logger.debug("主材移库出库时，创建了 {} 条出库记录", mainOutList.size());

    MainIn mainIn = this.mainInService.createMainIn(mainInParam);
    logger.debug("主材移库出库时，创建了条入库记录: {}", mainIn);

    if (null != mainIn.prevInId()) {
      this.mainInService.fixMainPlnNum(mainIn.prevInId(), -mainIn.mainPlnNum());
    } else {
      throw new BadRequestException("主材移库时缺少前入库外键");
    }

    return mainOutList;
  }
}
