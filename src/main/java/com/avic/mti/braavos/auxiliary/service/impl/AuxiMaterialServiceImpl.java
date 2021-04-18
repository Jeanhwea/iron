package com.avic.mti.iron.auxiliary.service.impl;

import com.avic.mti.iron.auxiliary.domain.entity.AuxiMaterial;
import com.avic.mti.iron.auxiliary.domain.repo.AuxiMaterialRepository;
import com.avic.mti.iron.auxiliary.helper.AuxiMaterialHelper;
import com.avic.mti.iron.auxiliary.service.AuxiMaterialService;
import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.asset.AssetInTypeEnum;
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
public class AuxiMaterialServiceImpl implements AuxiMaterialService {

  public static final Logger logger = LoggerFactory.getLogger(AuxiMaterialServiceImpl.class);

  private final AuxiMaterialRepository auxiMaterialRepository;
  private final MesConditionBuilder<AuxiMaterial> mesConditionBuilder;

  @Autowired
  public AuxiMaterialServiceImpl(
      AuxiMaterialRepository auxiMaterialRepository,
      MesConditionBuilder<AuxiMaterial> mesConditionBuilder) {
    this.auxiMaterialRepository = auxiMaterialRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateAuxiMaterial(AuxiMaterial auxiMaterial) {
    if (AssetInTypeEnum.of(auxiMaterial.inType()) == AssetInTypeEnum.Unknown) {
      throw new UnknownEnumException("inType", auxiMaterial.inType(), AssetInTypeEnum.expect());
    }
  }

  private void checkBeforeCreate(AuxiMaterial auxiMaterial) {
    // pass
  }

  private void checkBeforeReplace(AuxiMaterial auxiMaterial) {
    // pass
  }

  private void checkBeforeDelete(AuxiMaterial auxiMaterial) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AuxiMaterial> findAuxiMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<AuxiMaterial> builder = this.mesConditionBuilder.init(params, fields);
    return this.auxiMaterialRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<AuxiMaterial> findAllAuxiMaterials(Map<String, Object> params) {
    List<String> auxiCodes =
        ParamReader.init(params)
            .listStringFromKey("auxiCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 auxiCodes 参数"));
    return this.auxiMaterialRepository.findAllByAuxiCodeIn(auxiCodes);
  }

  @Override
  public List<AuxiMaterial> findIdleAuxiMaterials(Map<String, Object> params) {
    List<String> auxiCodes =
        ParamReader.init(params)
            .listStringFromKey("auxiCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 auxiCodes 参数"));
    if (auxiCodes.isEmpty()) {
      return this.auxiMaterialRepository.fetchAll();
    }

    return this.auxiMaterialRepository.findAllByAuxiCodeNotIn(auxiCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<AuxiMaterial> findAllAuxiMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<AuxiMaterial> builder = this.mesConditionBuilder.init(params, fields);
    return this.auxiMaterialRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public AuxiMaterial findById(long auxiMaterialId) {
    return this.auxiMaterialRepository
        .fetchById(auxiMaterialId)
        .orElseThrow(() -> new DatumNotExistException("AuxiMaterial", auxiMaterialId));
  }

  @Override
  public AuxiMaterial createAuxiMaterial(Map<String, Object> params) {
    AuxiMaterial newAuxiMaterial =
        AuxiMaterialHelper.assignAuxiMaterial(new AuxiMaterial(), params);

    boolean conflict =
        this.auxiMaterialRepository.findFirstByAuxiCode(newAuxiMaterial.auxiCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("辅材编码 {0} 已经存在", newAuxiMaterial.auxiCode());
    }

    this.checkBeforeCreate(newAuxiMaterial);
    this.validateAuxiMaterial(newAuxiMaterial);
    return this.auxiMaterialRepository.save(newAuxiMaterial);
  }

  @Override
  public AuxiMaterial replaceAuxiMaterial(long auxiMaterialId, Map<String, Object> params) {
    AuxiMaterial prevAuxiMaterial = this.findById(auxiMaterialId);

    ParamReader reader = ParamReader.init(params);
    String newAuxiMaterialCode = reader.stringFromKey("auxiCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newAuxiMaterialCode)
            && !newAuxiMaterialCode.equals(prevAuxiMaterial.auxiCode())
            && this.auxiMaterialRepository.findFirstByAuxiCode(newAuxiMaterialCode).isPresent();
    if (conflict) {
      throw new BadRequestException("辅材编码 {0} 已经存在", newAuxiMaterialCode);
    }

    this.checkBeforeReplace(prevAuxiMaterial);
    AuxiMaterial currAuxiMaterial = AuxiMaterialHelper.assignAuxiMaterial(prevAuxiMaterial, params);
    this.validateAuxiMaterial(currAuxiMaterial);
    return this.auxiMaterialRepository.save(currAuxiMaterial);
  }

  @Override
  public void deleteAuxiMaterial(long auxiMaterialId) {
    AuxiMaterial prevAuxiMaterial = this.findById(auxiMaterialId);
    this.checkBeforeDelete(prevAuxiMaterial);
    this.auxiMaterialRepository.delete(prevAuxiMaterial);
  }

  @Override
  public List<AuxiMaterial> findIdleAuxiMaterials() {
    return this.auxiMaterialRepository.findIdleAuxiMaterials();
  }
}
