package com.avic.mti.iron.waste.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.waste.WprdInCateEnum;
import com.avic.mti.iron.common.symbol.waste.WprdInTypeEnum;
import com.avic.mti.iron.proxy.service.ProxyEncoderService;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import com.avic.mti.iron.waste.domain.repo.WprdInRepository;
import com.avic.mti.iron.waste.helper.WprdInHelper;
import com.avic.mti.iron.waste.service.WprdInService;
import com.avic.mti.iron.waste.service.WprdLogService;
import com.avic.mti.iron.waste.service.WprdShelfService;
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
public class WprdInServiceImpl implements WprdInService {

  public static final Logger logger = LoggerFactory.getLogger(WprdInServiceImpl.class);

  private final WprdInRepository wprdInRepository;

  private final WprdLogService wprdLogService;
  private final WprdShelfService wprdShelfService;
  private final ProxyEncoderService proxyEncoderService;

  private final MesConditionBuilder<WprdIn> mesConditionBuilder;

  @Autowired
  public WprdInServiceImpl(
      WprdInRepository wprdInRepository,
      WprdLogService wprdLogService,
      WprdShelfService wprdShelfService,
      ProxyEncoderService proxyEncoderService,
      MesConditionBuilder<WprdIn> mesConditionBuilder) {
    this.wprdInRepository = wprdInRepository;
    this.wprdLogService = wprdLogService;
    this.wprdShelfService = wprdShelfService;
    this.proxyEncoderService = proxyEncoderService;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateWprdIn(WprdIn wprdIn) {
    if (WprdInCateEnum.of(wprdIn.inCate()) == WprdInCateEnum.Unknown) {
      throw new UnknownEnumException("inCate", wprdIn.inCate(), WprdInCateEnum.expect());
    }

    if (WprdInTypeEnum.of(wprdIn.inType()) == WprdInTypeEnum.Unknown) {
      throw new UnknownEnumException("inType", wprdIn.inType(), WprdInTypeEnum.expect());
    }
  }

  private void checkBeforeCreate(WprdIn wprdIn) {
    if (null == wprdIn.inCode()) {
      String inCode = this.proxyEncoderService.nextval("WPRD_IN");
      wprdIn.inCode(inCode);
    }
  }

  private void checkBeforeReplace(WprdIn wprdIn) {
    // pass
  }

  private void checkBeforeDelete(WprdIn wprdIn) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<WprdIn> findWprdIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<WprdIn> builder = this.mesConditionBuilder.init(params, fields);
    return this.wprdInRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<WprdIn> findAllWprdIns(Map<String, Object> params) {
    List<String> wprdCodes =
        ParamReader.init(params)
            .listStringFromKey("wprdCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 wprdCodes 参数"));
    return this.wprdInRepository.findAllByInCodeIn(wprdCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<WprdIn> findAllWprdIns(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<WprdIn> builder = this.mesConditionBuilder.init(params, fields);
    return this.wprdInRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public WprdIn findById(long wprdInId) {
    return this.wprdInRepository
        .fetchById(wprdInId)
        .orElseThrow(() -> new DatumNotExistException("WprdIn", wprdInId));
  }

  @Override
  public WprdIn createWprdIn(Map<String, Object> params) {
    WprdIn newWprdIn = WprdInHelper.assignWprdIn(new WprdIn(), params);

    this.checkBeforeCreate(newWprdIn);

    boolean conflict = this.wprdInRepository.findFirstByInCode(newWprdIn.inCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("废品入库编码 {0} 已经存在", newWprdIn.inCode());
    }

    this.validateWprdIn(newWprdIn);

    WprdIn savedWprdIn = this.wprdInRepository.save(newWprdIn);

    // List<WprdShelf> shelfList = this.wprdShelfService.enterShelfList(savedWprdIn);
    // logger.info("废品入库时 {} 更新库位信息为 shelfList = {}", savedWprdIn, shelfList);

    this.wprdLogService.createWprdLogForIn(savedWprdIn);

    return savedWprdIn;
  }

  @Override
  public WprdIn replaceWprdIn(long wprdInId, Map<String, Object> params) {
    WprdIn prevWprdIn = this.findById(wprdInId);

    ParamReader reader = ParamReader.init(params);
    String newWprdInCode = reader.stringFromKey("wprdCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newWprdInCode)
            && !newWprdInCode.equals(prevWprdIn.inCode())
            && this.wprdInRepository.findFirstByInCode(newWprdInCode).isPresent();
    if (conflict) {
      throw new BadRequestException("废品入库编码 {0} 已经存在", newWprdInCode);
    }

    this.checkBeforeReplace(prevWprdIn);
    WprdIn currWprdIn = WprdInHelper.assignWprdIn(prevWprdIn, params);
    this.validateWprdIn(currWprdIn);
    return this.wprdInRepository.save(currWprdIn);
  }

  @Override
  public void deleteWprdIn(long wprdInId) {
    WprdIn prevWprdIn = this.findById(wprdInId);
    this.checkBeforeDelete(prevWprdIn);
    this.wprdInRepository.delete(prevWprdIn);
  }

  @Override
  public WprdIn exitWprdIn(WprdOut wprdOut) {
    WprdIn wprdIn =
        this.wprdInRepository
            .findById(wprdOut.inId())
            .orElseThrow(() -> new DatumNotExistException("WprdIn", wprdOut.inId()));

    // logger.debug("开始修改入库 {} 的库位信息: {}", wprdIn, wprdIn.shelfJson());
    // WprdIn newWprdIn = WprdInHelper.exitWprdIn2(wprdOut.updateUser(), wprdIn, wprdOut);
    // logger.debug("完成修改入库 {} 的库位信息: {}", newWprdIn, newWprdIn.shelfJson());

    WprdIn newWprdIn =
        WprdInHelper.updateNumbers(wprdIn, -wprdOut.planEnum(), -wprdOut.planEnum(), 0L);
    return this.wprdInRepository.save(newWprdIn);
  }

  @Override
  public void updateNumbers(long wprdInId, long deltaStkNum, long deltaAvlNum, long deltaPlnNum) {
    WprdIn wprdIn =
        this.wprdInRepository
            .findById(wprdInId)
            .orElseThrow(() -> new DatumNotExistException("WprdIn", wprdInId));

    wprdIn = WprdInHelper.updateNumbers(wprdIn, deltaStkNum, deltaAvlNum, deltaPlnNum);

    this.wprdInRepository.save(wprdIn);
  }
}
