package com.avic.mti.iron.waste.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.exception.WrongParameterFormatException;
import com.avic.mti.iron.common.helper.NameCodeHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.symbol.waste.WprdOutCateEnum;
import com.avic.mti.iron.common.symbol.waste.WprdOutFlagEnum;
import com.avic.mti.iron.common.symbol.waste.WprdOutStatusEnum;
import com.avic.mti.iron.common.symbol.waste.WprdOutTypeEnum;
import com.avic.mti.iron.ledger.service.LedgerStubDetailService;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import com.avic.mti.iron.waste.domain.repo.WprdOutRepository;
import com.avic.mti.iron.waste.helper.WprdOutHelper;
import com.avic.mti.iron.waste.service.WprdInService;
import com.avic.mti.iron.waste.service.WprdLogService;
import com.avic.mti.iron.waste.service.WprdOutService;
import com.avic.mti.iron.waste.service.WprdShelfService;
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
public class WprdOutServiceImpl implements WprdOutService {

  public static final Logger logger = LoggerFactory.getLogger(WprdOutServiceImpl.class);

  private final WprdOutRepository wprdOutRepository;

  private final WprdInService wprdInService;
  private final WprdShelfService wprdShelfService;
  private final LedgerStubDetailService ledgerStubDetailService;

  private final WprdLogService wprdLogService;

  private final MesConditionBuilder<WprdOut> mesConditionBuilder;

  @Autowired
  public WprdOutServiceImpl(
      WprdOutRepository wprdOutRepository,
      WprdInService wprdInService,
      WprdShelfService wprdShelfService,
      LedgerStubDetailService ledgerStubDetailService,
      WprdLogService wprdLogService,
      MesConditionBuilder<WprdOut> mesConditionBuilder) {
    this.wprdOutRepository = wprdOutRepository;
    this.wprdInService = wprdInService;
    this.wprdShelfService = wprdShelfService;
    this.ledgerStubDetailService = ledgerStubDetailService;
    this.wprdLogService = wprdLogService;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateWprdOut(WprdOut wprdOut) {
    if (WprdOutStatusEnum.of(wprdOut.outStatus()) == WprdOutStatusEnum.Unknown) {
      throw new UnknownEnumException("outStatus", wprdOut.outStatus(), WprdOutStatusEnum.expect());
    }

    if (WprdOutCateEnum.of(wprdOut.outCate()) == WprdOutCateEnum.Unknown) {
      throw new UnknownEnumException("outCate", wprdOut.outCate(), WprdOutCateEnum.expect());
    }

    if (WprdOutTypeEnum.of(wprdOut.outType()) == WprdOutTypeEnum.Unknown) {
      throw new UnknownEnumException("outType", wprdOut.outType(), WprdOutTypeEnum.expect());
    }

    if (WprdOutFlagEnum.of(wprdOut.outFlag()) == WprdOutFlagEnum.Unknown) {
      throw new UnknownEnumException("outFlag", wprdOut.outFlag(), WprdOutFlagEnum.expect());
    }

    if (NameCodeHelper.validateNameCode(wprdOut.creatorNC())) {
      throw new WrongParameterFormatException("创建人", "名称[编码]", wprdOut.creatorNC());
    }
  }

  private void checkBeforeCreate(WprdOut wprdOut) {
    // pass
  }

  private void checkBeforeReplace(WprdOut wprdOut) {
    // pass
  }

  private void checkBeforeDelete(WprdOut wprdOut) {
    // pass
  }

  private void syncWhenCreate(WprdOut wprdOut) {
    WprdIn wprdIn = this.wprdInService.exitWprdIn(wprdOut);
    // List<WprdShelf> shelfList = this.wprdShelfService.exitShelfList(wprdOut);
    // LedgerStubDetail stubDetail = this.ledgerStubDetailService.exitStubDetail(wprdOut);
    logger.info("创建废品出库 {} 时，影响了废品入库 {}", wprdOut, wprdIn);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<WprdOut> findWprdOuts(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<WprdOut> builder = this.mesConditionBuilder.init(params, fields);
    return this.wprdOutRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<WprdOut> findAllWprdOuts(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<WprdOut> builder = this.mesConditionBuilder.init(params, fields);
    return this.wprdOutRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public WprdOut findById(long wprdOutId) {
    return this.wprdOutRepository
        .fetchById(wprdOutId)
        .orElseThrow(() -> new DatumNotExistException("WprdOut", wprdOutId));
  }

  @Override
  public WprdOut createWprdOut(Map<String, Object> params) {
    WprdOut newWprdOut = WprdOutHelper.assignWprdOut(new WprdOut(), params);

    this.checkBeforeCreate(newWprdOut);
    this.validateWprdOut(newWprdOut);

    WprdOut savedWprdOut = this.wprdOutRepository.save(newWprdOut);
    syncWhenCreate(savedWprdOut);

    this.wprdLogService.createWprdLogForOut(savedWprdOut);
    return savedWprdOut;
  }

  @Override
  public WprdOut replaceWprdOut(long wprdOutId, Map<String, Object> params) {
    WprdOut prevWprdOut = this.findById(wprdOutId);

    this.checkBeforeReplace(prevWprdOut);
    WprdOut currWprdOut = WprdOutHelper.assignWprdOut(prevWprdOut, params);
    this.validateWprdOut(currWprdOut);
    return this.wprdOutRepository.save(currWprdOut);
  }

  @Override
  public void deleteWprdOut(long wprdOutId) {
    WprdOut prevWprdOut = this.findById(wprdOutId);
    this.checkBeforeDelete(prevWprdOut);
    this.wprdOutRepository.delete(prevWprdOut);
  }

  @Override
  public List<WprdOut> createWprdOutInBulk(List<Map<String, Object>> paramList) {
    List<WprdOut> wprdOutList =
        paramList.stream().map(this::createWprdOut).collect(Collectors.toList());
    logger.debug("创建了 {} 条出库记录", wprdOutList.size());
    return wprdOutList;
  }
}
