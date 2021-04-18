package com.avic.mti.iron.ledger.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.exception.WrongParameterFormatException;
import com.avic.mti.iron.common.helper.NameCodeHelper;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.resource.*;
import com.avic.mti.iron.ledger.domain.entity.LedgerStub;
import com.avic.mti.iron.ledger.domain.repo.LedgerStubDetailRepository;
import com.avic.mti.iron.ledger.domain.repo.LedgerStubRepository;
import com.avic.mti.iron.ledger.helper.LedgerStubHelper;
import com.avic.mti.iron.ledger.service.LedgerStubService;
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
public class LedgerStubServiceImpl implements LedgerStubService {

  public static final Logger logger = LoggerFactory.getLogger(LedgerStubServiceImpl.class);

  private final LedgerStubRepository ledgerStubRepository;
  private final LedgerStubDetailRepository ledgerStubDetailRepository;

  private final MesConditionBuilder<LedgerStub> mesConditionBuilder;

  @Autowired
  public LedgerStubServiceImpl(
      LedgerStubRepository ledgerStubRepository,
      LedgerStubDetailRepository ledgerStubDetailRepository,
      MesConditionBuilder<LedgerStub> mesConditionBuilder) {
    this.ledgerStubRepository = ledgerStubRepository;
    this.ledgerStubDetailRepository = ledgerStubDetailRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateLedgerStub(LedgerStub ledgerStub) {
    if (StubStatusEnum.of(ledgerStub.stubStatus()) == StubStatusEnum.Unknown) {
      throw new UnknownEnumException(
          "stubStatus", ledgerStub.stubStatus(), StubStatusEnum.expect());
    }

    if (StubCateEnum.of(ledgerStub.stubCate()) == StubCateEnum.Unknown) {
      throw new UnknownEnumException("stubCate", ledgerStub.stubCate(), StubCateEnum.expect());
    }

    if (StubTypeEnum.of(ledgerStub.stubType()) == StubTypeEnum.Unknown) {
      throw new UnknownEnumException("stubType", ledgerStub.stubType(), StubTypeEnum.expect());
    }

    if (StubFlagEnum.of(ledgerStub.stubFlag()) == StubFlagEnum.Unknown) {
      throw new UnknownEnumException("stubFlag", ledgerStub.stubFlag(), StubFlagEnum.expect());
    }

    if (StubTagEnum.of(ledgerStub.stubTag()) == StubTagEnum.Unknown) {
      throw new UnknownEnumException("stubTag", ledgerStub.stubTag(), StubTagEnum.expect());
    }

    if (NameCodeHelper.validateNameCode(ledgerStub.creatorNC())) {
      throw new WrongParameterFormatException("创建人", "名称[编码]", ledgerStub.creatorNC());
    }
  }

  private void checkBeforeCreate(LedgerStub ledgerStub) {
    // pass
  }

  private void checkBeforeReplace(LedgerStub ledgerStub) {
    // pass
  }

  private void checkBeforeDelete(LedgerStub ledgerStub) {
    // pass
  }

  private int enableLedgerStub(long ledgerStubId) {
    int countLedgerStub =
        this.ledgerStubRepository.updateStubStatusByStubId(
            StubStatusEnum.Enum1_YSX.symbol(), ledgerStubId);
    int countLedgerStubDetail =
        this.ledgerStubDetailRepository.updateStubDetStatusByStubId(
            StubDetStatusEnum.Enum1_YSX.symbol(), ledgerStubId);
    logger.debug(
        "生效发货单[{}], 影响了 {} 条发货单，及 {} 条明细", ledgerStubId, countLedgerStub, countLedgerStubDetail);
    return countLedgerStubDetail;
  }

  private int disableLedgerStub(long ledgerStubId) {
    int countLedgerStub =
        this.ledgerStubRepository.updateStubStatusByStubId(
            StubStatusEnum.Enum0_WSX.symbol(), ledgerStubId);
    int countLedgerStubDetail =
        this.ledgerStubDetailRepository.updateStubDetStatusByStubId(
            StubDetStatusEnum.Enum0_WSX.symbol(), ledgerStubId);
    logger.debug(
        "失效发货单[{}], 影响了 {} 条发货单，及 {} 条明细", ledgerStubId, countLedgerStub, countLedgerStubDetail);
    return countLedgerStubDetail;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<LedgerStub> findLedgerStubs(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<LedgerStub> builder = this.mesConditionBuilder.init(params, fields);
    return this.ledgerStubRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<LedgerStub> findAllLedgerStubs(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<LedgerStub> builder = this.mesConditionBuilder.init(params, fields);
    return this.ledgerStubRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public LedgerStub findById(long ledgerStubId) {
    return this.ledgerStubRepository
        .fetchById(ledgerStubId)
        .orElseThrow(() -> new DatumNotExistException("LedgerStub", ledgerStubId));
  }

  @Override
  public LedgerStub createLedgerStub(Map<String, Object> params) {
    LedgerStub newLedgerStub = LedgerStubHelper.assignLedgerStub(new LedgerStub(), params);

    boolean conflict =
        this.ledgerStubRepository.findFirstByStubCode(newLedgerStub.stubCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("资源出库单编码 {0} 已经存在", newLedgerStub.stubCode());
    }

    this.checkBeforeCreate(newLedgerStub);
    this.validateLedgerStub(newLedgerStub);
    return this.ledgerStubRepository.save(newLedgerStub);
  }

  @Override
  public LedgerStub replaceLedgerStub(long ledgerStubId, Map<String, Object> params) {
    LedgerStub prevLedgerStub = this.findById(ledgerStubId);

    ParamReader reader = ParamReader.init(params);
    String newLedgerStubCode = reader.stringFromKey("stubCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newLedgerStubCode)
            && !newLedgerStubCode.equals(prevLedgerStub.stubCode())
            && this.ledgerStubRepository.findFirstByStubCode(newLedgerStubCode).isPresent();
    if (conflict) {
      throw new BadRequestException("资源出库单编码 {0} 已经存在", newLedgerStubCode);
    }

    this.checkBeforeReplace(prevLedgerStub);
    LedgerStub currLedgerStub = LedgerStubHelper.assignLedgerStub(prevLedgerStub, params);
    this.validateLedgerStub(currLedgerStub);
    return this.ledgerStubRepository.save(currLedgerStub);
  }

  @Override
  public void deleteLedgerStub(long ledgerStubId) {
    LedgerStub prevLedgerStub = this.findById(ledgerStubId);
    this.checkBeforeDelete(prevLedgerStub);
    this.ledgerStubRepository.delete(prevLedgerStub);
  }

  @Override
  public Map<String, Object> enableLedgerStubs(Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    List<Long> stubIdList =
        reader
            .listLongFromKey("stubIds")
            .orElseThrow(() -> new BadRequestException("无法获取 stubIds 参数"));

    long countEnabledDetail = 0L;
    long countEnabled = 0L;
    for (Long stubId : stubIdList) {
      countEnabled += 1;
      countEnabledDetail += enableLedgerStub(stubId);
    }

    return ParamBuilder.init()
        .put("countEnabled", countEnabled)
        .put("countEnabledDetail", countEnabledDetail)
        .params();
  }

  @Override
  public Map<String, Object> disableLedgerStubs(Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    List<Long> stubIdList =
        reader
            .listLongFromKey("stubIds")
            .orElseThrow(() -> new BadRequestException("无法获取 stubIds 参数"));

    long countDisabledDetail = 0L;
    long countDisabled = 0L;
    for (Long stubId : stubIdList) {
      countDisabled += 1;
      countDisabledDetail += disableLedgerStub(stubId);
    }

    return ParamBuilder.init()
        .put("countDisabled", countDisabled)
        .put("countDisabledDetail", countDisabledDetail)
        .params();
  }
}
