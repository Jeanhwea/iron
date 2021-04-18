package com.avic.mti.iron.main.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.main.MainInCateEnum;
import com.avic.mti.iron.common.symbol.main.MainInTypeEnum;
import com.avic.mti.iron.common.symbol.main.MainMaterialTypeEnum;
import com.avic.mti.iron.common.symbol.main.RetestStatusEnum;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.MainOut;
import com.avic.mti.iron.main.domain.entity.MainShelf;
import com.avic.mti.iron.main.domain.repo.MainInRepository;
import com.avic.mti.iron.main.helper.MainInHelper;
import com.avic.mti.iron.main.service.MainInService;
import com.avic.mti.iron.main.service.MainLogService;
import com.avic.mti.iron.main.service.MainShelfService;
import com.avic.mti.iron.proxy.service.ProxyEncoderService;
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
public class MainInServiceImpl implements MainInService {

  public static final Logger logger = LoggerFactory.getLogger(MainInServiceImpl.class);

  private final MainInRepository mainInRepository;

  private final MainLogService mainLogService;
  private final MainShelfService mainShelfService;

  private final ProxyEncoderService proxyEncoderService;

  private final MesConditionBuilder<MainIn> mesConditionBuilder;

  @Autowired
  public MainInServiceImpl(
      MainInRepository mainInRepository,
      MainLogService mainLogService,
      MainShelfService mainShelfService,
      ProxyEncoderService proxyEncoderService,
      MesConditionBuilder<MainIn> mesConditionBuilder) {
    this.mainInRepository = mainInRepository;
    this.mainLogService = mainLogService;
    this.mainShelfService = mainShelfService;
    this.proxyEncoderService = proxyEncoderService;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateMainIn(MainIn mainIn) {
    if (MainInCateEnum.of(mainIn.inCate()) == MainInCateEnum.Unknown) {
      throw new UnknownEnumException("inCate", mainIn.inCate(), MainInCateEnum.expect());
    }

    if (MainInTypeEnum.of(mainIn.inType()) == MainInTypeEnum.Unknown) {
      throw new UnknownEnumException("inType", mainIn.inType(), MainInTypeEnum.expect());
    }

    if (RetestStatusEnum.of(mainIn.retestStatus()) == RetestStatusEnum.Unknown) {
      throw new UnknownEnumException(
          "retestStatus", mainIn.retestStatus(), RetestStatusEnum.expect());
    }

    if (MainMaterialTypeEnum.of(mainIn.mainType()) == MainMaterialTypeEnum.Unknown) {
      throw new UnknownEnumException("mainType", mainIn.mainType(), MainMaterialTypeEnum.expect());
    }
  }

  private void checkBeforeCreate(MainIn mainIn) {
    if (null == mainIn.inCode()) {
      String inCode = this.proxyEncoderService.nextval("MAIN_IN");
      mainIn.inCode(inCode);
    }
  }

  private void checkBeforeReplace(MainIn mainIn) {
    // pass
  }

  private void checkBeforeDelete(MainIn mainIn) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MainIn> findMainIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MainIn> builder = this.mesConditionBuilder.init(params, fields);
    return this.mainInRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<MainIn> findAllMainIns(Map<String, Object> params) {
    List<String> mainCodes =
        ParamReader.init(params)
            .listStringFromKey("mainCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 mainCodes 参数"));
    return this.mainInRepository.findAllByInCodeIn(mainCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MainIn> findAllMainIns(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MainIn> builder = this.mesConditionBuilder.init(params, fields);
    return this.mainInRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public MainIn findById(long mainInId) {
    return this.mainInRepository
        .fetchById(mainInId)
        .orElseThrow(() -> new DatumNotExistException("MainIn", mainInId));
  }

  @Override
  public MainIn createMainIn(Map<String, Object> params) {
    MainIn newMainIn = MainInHelper.assignMainIn(new MainIn(), params);

    this.checkBeforeCreate(newMainIn);

    boolean conflict = this.mainInRepository.findFirstByInCode(newMainIn.inCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("主材入库编码 {0} 已经存在", newMainIn.inCode());
    }

    this.validateMainIn(newMainIn);

    MainIn savedMainIn = this.mainInRepository.save(newMainIn);

    List<MainShelf> shelfList = this.mainShelfService.enterShelfList(savedMainIn);
    logger.info("主材入库时 {} 更新库位信息为 shelfList = {}", savedMainIn, shelfList);

    this.mainLogService.createMainLogForIn(savedMainIn);

    return savedMainIn;
  }

  @Override
  public MainIn replaceMainIn(long mainInId, Map<String, Object> params) {
    MainIn prevMainIn = this.findById(mainInId);

    ParamReader reader = ParamReader.init(params);
    String newMainInCode = reader.stringFromKey("mainCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newMainInCode)
            && !newMainInCode.equals(prevMainIn.inCode())
            && this.mainInRepository.findFirstByInCode(newMainInCode).isPresent();
    if (conflict) {
      throw new BadRequestException("主材入库编码 {0} 已经存在", newMainInCode);
    }

    this.checkBeforeReplace(prevMainIn);
    MainIn currMainIn = MainInHelper.assignMainIn(prevMainIn, params);
    this.validateMainIn(currMainIn);
    return this.mainInRepository.save(currMainIn);
  }

  @Override
  public void deleteMainIn(long mainInId) {
    MainIn prevMainIn = this.findById(mainInId);
    this.checkBeforeDelete(prevMainIn);
    this.mainInRepository.delete(prevMainIn);
  }

  @Override
  public MainIn exitMainIn(MainOut mainOut) {
    MainIn mainIn =
        this.mainInRepository
            .findById(mainOut.inId())
            .orElseThrow(() -> new DatumNotExistException("MainIn", mainOut.inId()));

    logger.debug("开始修改入库 {} 的库位信息: {}", mainIn, mainIn.shelfJson());
    MainIn newMainIn = MainInHelper.exitMainIn(mainOut.updateUser(), mainIn, mainOut);
    logger.debug("完成修改入库 {} 的库位信息: {}", newMainIn, newMainIn.shelfJson());

    return this.mainInRepository.save(newMainIn);
  }

  @Override
  public void updateNumbers(long mainInId, long deltaStkNum, long deltaAvlNum, long deltaPlnNum) {
    MainIn mainIn =
        this.mainInRepository
            .findById(mainInId)
            .orElseThrow(() -> new DatumNotExistException("MainIn", mainInId));

    mainIn = MainInHelper.updateNumbers(mainIn, deltaStkNum, deltaAvlNum, deltaPlnNum);

    this.mainInRepository.save(mainIn);
  }

  @Override
  public void fixMainPlnNum(Long prevInId, Long deltaPlnNum) {
    MainIn mainIn = this.findById(prevInId);
    long newMainPlnNum = mainIn.mainPlnNum() + deltaPlnNum;
    if (newMainPlnNum < 0L) {
      logger.error("主材的计划数不足【mainPlnNum = {}, deltaPlnNum = {}】", mainIn.mainPlnNum(), deltaPlnNum);
    }
    MainIn savedMainIn = this.mainInRepository.save(mainIn);
    logger.debug("修改主材入库 {} 的计划数: {}", savedMainIn, savedMainIn.mainPlnNum());
  }

  @Override
  public List<MainIn> createMainInsInBulk(List<Map<String, Object>> paramList) {
    Map<String, Object> genRespMap = this.proxyEncoderService.generate("MAIN_IN", paramList.size());
    List<String> mainInCodeList =
        ParamReader.init(genRespMap)
            .listStringFromKey("codes")
            .orElseThrow(() -> new BadRequestException("批量生成编码时无法获取 codes 列表"));
    if (mainInCodeList.size() < paramList.size()) {
      throw new BadRequestException(
          "获取的编码数量 {0} 小于入库数量 {0}", mainInCodeList.size(), paramList.size());
    }
    String mainBatchCode = this.proxyEncoderService.nextval("MAIN_BATCH");
    logger.info(
        "主材批量入库批号【mainBatchCode = {}】 -> mainInCodeList = {}", mainBatchCode, mainInCodeList);

    List<MainIn> mainIns = new LinkedList<>();
    for (int i = 0; i < paramList.size(); i++) {
      Map<String, Object> params = paramList.get(i);
      params.put("inCode", mainInCodeList.get(i));
      params.put("mainVar01Str", mainBatchCode);
      MainIn mainIn = this.createMainIn(params);
      mainIns.add(mainIn);
    }

    return mainIns;
  }
}
