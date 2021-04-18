package com.avic.mti.iron.auxiliary.service.impl;

import com.avic.mti.iron.auxiliary.domain.entity.AuxiIn;
import com.avic.mti.iron.auxiliary.domain.entity.AuxiOut;
import com.avic.mti.iron.auxiliary.domain.repo.AuxiInRepository;
import com.avic.mti.iron.auxiliary.helper.AuxiInHelper;
import com.avic.mti.iron.auxiliary.service.AuxiInService;
import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
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
public class AuxiInServiceImpl implements AuxiInService {

  public static final Logger logger = LoggerFactory.getLogger(AuxiInServiceImpl.class);

  private final AuxiInRepository auxiInRepository;
  private final MesConditionBuilder<AuxiIn> mesConditionBuilder;

  @Autowired
  public AuxiInServiceImpl(
      AuxiInRepository auxiInRepository, MesConditionBuilder<AuxiIn> mesConditionBuilder) {
    this.auxiInRepository = auxiInRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateAuxiIn(AuxiIn auxiIn) {
    // if (AuxiMaterialTypeEnum.of(auxiIn.auxiType()) == AuxiMaterialTypeEnum.Unknown) {
    //   throw new UnknownEnumException("auxiType", auxiIn.auxiType(),
    // AuxiMaterialTypeEnum.expect());
    // }
  }

  private void checkBeforeCreate(AuxiIn auxiIn) {
    // pass
  }

  private void checkBeforeReplace(AuxiIn auxiIn) {
    // pass
  }

  private void checkBeforeDelete(AuxiIn auxiIn) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AuxiIn> findAuxiIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<AuxiIn> builder = this.mesConditionBuilder.init(params, fields);
    return this.auxiInRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<AuxiIn> findAllAuxiIns(Map<String, Object> params) {
    List<String> auxiCodes =
        ParamReader.init(params)
            .listStringFromKey("auxiCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 auxiCodes 参数"));
    return this.auxiInRepository.findAllByInCodeIn(auxiCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<AuxiIn> findAllAuxiIns(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<AuxiIn> builder = this.mesConditionBuilder.init(params, fields);
    return this.auxiInRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public AuxiIn findById(long auxiInId) {
    return this.auxiInRepository
        .fetchById(auxiInId)
        .orElseThrow(() -> new DatumNotExistException("AuxiIn", auxiInId));
  }

  @Override
  public AuxiIn createAuxiIn(Map<String, Object> params) {
    AuxiIn newAuxiIn = AuxiInHelper.assignAuxiIn(new AuxiIn(), params);

    boolean conflict = this.auxiInRepository.findFirstByInCode(newAuxiIn.inCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("辅材入库编码 {0} 已经存在", newAuxiIn.inCode());
    }

    this.checkBeforeCreate(newAuxiIn);
    this.validateAuxiIn(newAuxiIn);

    AuxiIn savedAuxiIn = this.auxiInRepository.save(newAuxiIn);
    return savedAuxiIn;
  }

  @Override
  public AuxiIn replaceAuxiIn(long auxiInId, Map<String, Object> params) {
    AuxiIn prevAuxiIn = this.findById(auxiInId);

    ParamReader reader = ParamReader.init(params);
    String newAuxiInCode = reader.stringFromKey("auxiCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newAuxiInCode)
            && !newAuxiInCode.equals(prevAuxiIn.inCode())
            && this.auxiInRepository.findFirstByInCode(newAuxiInCode).isPresent();
    if (conflict) {
      throw new BadRequestException("辅材入库编码 {0} 已经存在", newAuxiInCode);
    }

    this.checkBeforeReplace(prevAuxiIn);
    AuxiIn currAuxiIn = AuxiInHelper.assignAuxiIn(prevAuxiIn, params);
    this.validateAuxiIn(currAuxiIn);
    return this.auxiInRepository.save(currAuxiIn);
  }

  @Override
  public void deleteAuxiIn(long auxiInId) {
    AuxiIn prevAuxiIn = this.findById(auxiInId);
    this.checkBeforeDelete(prevAuxiIn);
    this.auxiInRepository.delete(prevAuxiIn);
  }

  @Override
  public AuxiIn exitAuxiIn(AuxiOut auxiOut) {
    AuxiIn auxiIn =
        this.auxiInRepository
            .findById(auxiOut.inId())
            .orElseThrow(() -> new DatumNotExistException("AuxiIn", auxiOut.inId()));

    logger.debug("开始修改入库 {} 的库位信息: {}", auxiIn, auxiIn.shelfJson());
    AuxiIn newAuxiIn = AuxiInHelper.exitAuxiIn(auxiOut.updateUser(), auxiIn, auxiOut);
    logger.debug("完成修改入库 {} 的库位信息: {}", newAuxiIn, newAuxiIn.shelfJson());

    return this.auxiInRepository.save(newAuxiIn);
  }

  @Override
  public void updateNumbers(long auxiInId, long deltaStkNum, long deltaAvlNum, long deltaPlnNum) {
    AuxiIn auxiIn =
        this.auxiInRepository
            .findById(auxiInId)
            .orElseThrow(() -> new DatumNotExistException("AuxiIn", auxiInId));

    auxiIn = AuxiInHelper.updateNumbers(auxiIn, deltaStkNum, deltaAvlNum, deltaPlnNum);

    this.auxiInRepository.save(auxiIn);
  }
}
