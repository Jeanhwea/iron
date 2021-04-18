package com.avic.mti.iron.ledger.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.exception.WrongParameterFormatException;
import com.avic.mti.iron.common.helper.NameCodeHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.resource.*;
import com.avic.mti.iron.ledger.domain.entity.LedgerStub;
import com.avic.mti.iron.ledger.domain.entity.LedgerStubDetail;
import com.avic.mti.iron.ledger.domain.repo.LedgerStubDetailRepository;
import com.avic.mti.iron.ledger.domain.repo.LedgerStubRepository;
import com.avic.mti.iron.ledger.helper.LedgerStubDetailHelper;
import com.avic.mti.iron.ledger.service.LedgerStubDetailService;
import com.avic.mti.iron.main.domain.entity.MainOut;
import com.avic.mti.iron.main.service.MainInService;
import java.util.LinkedList;
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
public class LedgerStubDetailServiceImpl implements LedgerStubDetailService {

  public static final Logger logger = LoggerFactory.getLogger(LedgerStubDetailServiceImpl.class);

  private final LedgerStubRepository ledgerStubRepository;
  private final LedgerStubDetailRepository ledgerStubDetailRepository;

  private final MainInService mainInService;

  private final MesConditionBuilder<LedgerStubDetail> mesConditionBuilder;

  @Autowired
  public LedgerStubDetailServiceImpl(
      LedgerStubRepository ledgerStubRepository,
      LedgerStubDetailRepository ledgerStubDetailRepository,
      MainInService mainInService,
      MesConditionBuilder<LedgerStubDetail> mesConditionBuilder) {
    this.ledgerStubRepository = ledgerStubRepository;
    this.ledgerStubDetailRepository = ledgerStubDetailRepository;
    this.mainInService = mainInService;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateLedgerStubDetail(LedgerStubDetail ledgerStubDetail) {
    if (StubDetStatusEnum.of(ledgerStubDetail.stubDetStatus()) == StubDetStatusEnum.Unknown) {
      throw new UnknownEnumException(
          "stubDetStatus", ledgerStubDetail.stubDetStatus(), StubDetStatusEnum.expect());
    }

    if (StubDetCateEnum.of(ledgerStubDetail.stubDetCate()) == StubDetCateEnum.Unknown) {
      throw new UnknownEnumException(
          "stubDetCate", ledgerStubDetail.stubDetCate(), StubDetCateEnum.expect());
    }

    if (StubDetTypeEnum.of(ledgerStubDetail.stubDetType()) == StubDetTypeEnum.Unknown) {
      throw new UnknownEnumException(
          "stubDetType", ledgerStubDetail.stubDetType(), StubDetTypeEnum.expect());
    }

    if (NameCodeHelper.validateNameCode(ledgerStubDetail.creatorNC())) {
      throw new WrongParameterFormatException("创建人", "名称[编码]", ledgerStubDetail.creatorNC());
    }
  }

  private void checkBeforeCreate(LedgerStubDetail ledgerStubDetail) {
    // pass
  }

  private void checkBeforeReplace(LedgerStubDetail ledgerStubDetail) {
    if (StubDetStatusEnum.of(ledgerStubDetail.stubDetStatus()) != StubDetStatusEnum.Enum0_WSX) {
      throw new BadRequestException("不允许修改状态为 {0} 的出库单明细", ledgerStubDetail.stubDetStatus());
    }
  }

  private void checkBeforeDelete(LedgerStubDetail ledgerStubDetail) {
    if (StubDetStatusEnum.of(ledgerStubDetail.stubDetStatus()) != StubDetStatusEnum.Enum0_WSX) {
      throw new BadRequestException("不允许删除状态为 {0} 的出库单明细", ledgerStubDetail.stubDetStatus());
    }
  }

  private void syncInNumbersWhenCreate(LedgerStubDetail ledgerStubDetail) {
    // switch (StubDetTypeEnum.of(ledgerStubDetail.stubDetType())) {
    //   case Enum2_ZC:
    //     this.mainInService.updateNumbers(
    //         ledgerStubDetail.inId(), 0, -ledgerStubDetail.outNum(), ledgerStubDetail.outNum());
    //     break;
    //   case Unknown:
    //     throw new BadRequestException("错误的资源类型: {0}", ledgerStubDetail.stubDetType());
    //   default:
    //     logger.warn("无需占货的资源类型: {}", ledgerStubDetail.stubDetType());
    // }

    this.mainInService.updateNumbers(ledgerStubDetail.inId(), 0, -ledgerStubDetail.outNum(), 0);
  }

  private void syncInNumbersWhenDelete(LedgerStubDetail ledgerStubDetail) {
    // switch (StubDetTypeEnum.of(ledgerStubDetail.stubDetType())) {
    //   case Enum2_ZC:
    //     this.mainInService.updateNumbers(
    //         ledgerStubDetail.inId(), 0, ledgerStubDetail.outNum(), -ledgerStubDetail.outNum());
    //     break;
    //   case Unknown:
    //     throw new BadRequestException("错误的资源类型: {0}", ledgerStubDetail.stubDetType());
    //   default:
    //     logger.warn("无需占货的资源类型: {}", ledgerStubDetail.stubDetType());
    // }

    this.mainInService.updateNumbers(ledgerStubDetail.inId(), 0, ledgerStubDetail.outNum(), 0);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<LedgerStubDetail> findLedgerStubDetails(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<LedgerStubDetail> builder = this.mesConditionBuilder.init(params, fields);
    return this.ledgerStubDetailRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<LedgerStubDetail> findAllLedgerStubDetails(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<LedgerStubDetail> builder = this.mesConditionBuilder.init(params, fields);
    return this.ledgerStubDetailRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public LedgerStubDetail findById(long ledgerStubDetailId) {
    return this.ledgerStubDetailRepository
        .fetchById(ledgerStubDetailId)
        .orElseThrow(() -> new DatumNotExistException("LedgerStubDetail", ledgerStubDetailId));
  }

  @Override
  public LedgerStubDetail createLedgerStubDetail(Map<String, Object> params) {
    LedgerStubDetail newLedgerStubDetail =
        LedgerStubDetailHelper.assignLedgerStubDetail(new LedgerStubDetail(), params);

    this.checkBeforeCreate(newLedgerStubDetail);
    this.validateLedgerStubDetail(newLedgerStubDetail);
    LedgerStubDetail savedDetail = this.ledgerStubDetailRepository.save(newLedgerStubDetail);

    // syncInNumbersWhenCreate(savedDetail);

    return savedDetail;
  }

  @Override
  public List<LedgerStubDetail> createLedgerStubDetailInBulk(List<Map<String, Object>> paramList) {
    List<LedgerStubDetail> details = new LinkedList<>();
    for (Map<String, Object> params : paramList) {

      LedgerStubDetail newLedgerStubDetail =
          LedgerStubDetailHelper.assignLedgerStubDetail(new LedgerStubDetail(), params);

      this.checkBeforeCreate(newLedgerStubDetail);
      this.validateLedgerStubDetail(newLedgerStubDetail);
      LedgerStubDetail savedDetail = this.ledgerStubDetailRepository.save(newLedgerStubDetail);

      // syncInNumbersWhenCreate(savedDetail);

      details.add(savedDetail);
    }
    return details;
  }

  @Override
  public LedgerStubDetail replaceLedgerStubDetail(
      long ledgerStubDetailId, Map<String, Object> params) {
    LedgerStubDetail prevLedgerStubDetail = this.findById(ledgerStubDetailId);

    this.checkBeforeReplace(prevLedgerStubDetail);
    LedgerStubDetail currLedgerStubDetail =
        LedgerStubDetailHelper.assignLedgerStubDetail(prevLedgerStubDetail, params);
    this.validateLedgerStubDetail(currLedgerStubDetail);

    syncInNumbersWhenDelete(currLedgerStubDetail);

    return this.ledgerStubDetailRepository.save(currLedgerStubDetail);
  }

  @Override
  public void deleteLedgerStubDetail(long ledgerStubDetailId) {
    LedgerStubDetail prevLedgerStubDetail = this.findById(ledgerStubDetailId);
    this.checkBeforeDelete(prevLedgerStubDetail);
    this.ledgerStubDetailRepository.delete(prevLedgerStubDetail);
  }

  @Override
  public void deleteLedgerStubDetailInBulk(Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    List<Long> stubDetIds =
        reader
            .listLongFromKey("stubDetIds")
            .orElseThrow(() -> new BadRequestException("无法获取 stubDetIds 参数"));
    String updateUser =
        reader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("无法获取 updateUser 参数"));
    stubDetIds.forEach(this::deleteLedgerStubDetail);
    logger.debug("用户 {} 删除 {} 条出库单明细数据", updateUser, stubDetIds.size());
  }

  @Override
  public LedgerStubDetail exitStubDetail(MainOut mainOut) {
    LedgerStub stub =
        this.ledgerStubRepository
            .findById(mainOut.stubId())
            .orElseThrow(() -> new DatumNotExistException("LedgerStub", mainOut.stubId()));

    LedgerStubDetail stubDetail =
        this.ledgerStubDetailRepository
            .findById(mainOut.stubDetId())
            .orElseThrow(() -> new DatumNotExistException("LedgerStubDetail", mainOut.stubDetId()));

    long newOutNum = stubDetail.outNum() + mainOut.planEnum();
    if (newOutNum < 0L) {
      throw new BadRequestException("主材出库明细 {0} 的出库数量小于零", stubDetail);
    }
    stubDetail.outNum(newOutNum);

    stubDetail.stubDetStatus(StubDetStatusEnum.Enum2_YCK.symbol());
    LedgerStubDetail savedStubDetail = this.ledgerStubDetailRepository.save(stubDetail);
    logger.debug("修改资源出库单明细 {} 的状态为 {}", savedStubDetail, savedStubDetail.stubDetStatus());

    int countNotExited =
        this.ledgerStubDetailRepository.countAllByStubIdAndStubDetStatusNot(
            stub.id(), StubDetStatusEnum.Enum2_YCK.symbol());
    if (countNotExited > 0) {
      stub.stubStatus(StubStatusEnum.Enum2_CKZ.symbol());
    } else {
      stub.stubStatus(StubStatusEnum.Enum3_YCK.symbol());
    }
    LedgerStub savedStub = this.ledgerStubRepository.save(stub);
    logger.debug("修改资源出库单 {} 的状态为 {}", stub, savedStub.stubStatus());

    return savedStubDetail;
  }
}
