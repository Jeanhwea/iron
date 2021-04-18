package com.avic.mti.iron.measure.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.asset.AssetInTypeEnum;
import com.avic.mti.iron.common.symbol.measure.MeasMaterialCateEnum;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
import com.avic.mti.iron.measure.domain.entity.MeasMaterial;
import com.avic.mti.iron.measure.domain.repo.MeasInRepository;
import com.avic.mti.iron.measure.domain.repo.MeasMaterialRepository;
import com.avic.mti.iron.measure.helper.MeasMaterialHelper;
import com.avic.mti.iron.measure.service.MeasMaterialService;
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
public class MeasMaterialServiceImpl implements MeasMaterialService {

  public static final Logger logger = LoggerFactory.getLogger(MeasMaterialServiceImpl.class);

  private final MeasMaterialRepository measMaterialRepository;
  private final MeasInRepository measInRepository;

  private final MesConditionBuilder<MeasMaterial> mesConditionBuilder;

  @Autowired
  public MeasMaterialServiceImpl(
      MeasMaterialRepository measMaterialRepository,
      MeasInRepository measInRepository,
      MesConditionBuilder<MeasMaterial> mesConditionBuilder) {
    this.measMaterialRepository = measMaterialRepository;
    this.measInRepository = measInRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateMeasMaterial(MeasMaterial measMaterial) {
    if (AssetInTypeEnum.of(measMaterial.inType()) == AssetInTypeEnum.Unknown) {
      throw new UnknownEnumException("inType", measMaterial.inType(), AssetInTypeEnum.expect());
    }

    if (MeasMaterialCateEnum.of(measMaterial.measCate()) == MeasMaterialCateEnum.Unknown) {
      throw new UnknownEnumException(
          "measCate", measMaterial.measCate(), MeasMaterialCateEnum.expect());
    }
  }

  private void checkBeforeCreate(MeasMaterial measMaterial) {
    // pass
  }

  private void checkBeforeReplace(MeasMaterial measMaterial) {
    // pass
  }

  private void checkBeforeDelete(MeasMaterial measMaterial) {
    List<MeasIn> measIns = this.measInRepository.findByMeasId(measMaterial.id());
    if (!measIns.isEmpty()) {
      throw new BadRequestException("工具 {0} 存在 {1} 条对应的入库记录，不允许删除", measMaterial, measIns.size());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MeasMaterial> findMeasMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MeasMaterial> builder = this.mesConditionBuilder.init(params, fields);
    return this.measMaterialRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<MeasMaterial> findAllMeasMaterials(Map<String, Object> params) {
    List<String> measCodes =
        ParamReader.init(params)
            .listStringFromKey("measCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 measCodes 参数"));
    return this.measMaterialRepository.findAllByMeasCodeIn(measCodes);
  }

  @Override
  public List<MeasMaterial> findIdleMeasMaterials(Map<String, Object> params) {
    List<String> measCodes =
        ParamReader.init(params)
            .listStringFromKey("measCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 measCodes 参数"));
    if (measCodes.isEmpty()) {
      return this.measMaterialRepository.fetchAll();
    }

    return this.measMaterialRepository.findAllByMeasCodeNotIn(measCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MeasMaterial> findAllMeasMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MeasMaterial> builder = this.mesConditionBuilder.init(params, fields);
    return this.measMaterialRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public MeasMaterial findById(long measMaterialId) {
    return this.measMaterialRepository
        .fetchById(measMaterialId)
        .orElseThrow(() -> new DatumNotExistException("MeasMaterial", measMaterialId));
  }

  @Override
  public MeasMaterial createMeasMaterial(Map<String, Object> params) {
    MeasMaterial newMeasMaterial =
        MeasMaterialHelper.assignMeasMaterial(new MeasMaterial(), params);

    boolean conflict =
        this.measMaterialRepository.findFirstByMeasCode(newMeasMaterial.measCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("工具编码 {0} 已经存在", newMeasMaterial.measCode());
    }

    this.checkBeforeCreate(newMeasMaterial);
    this.validateMeasMaterial(newMeasMaterial);
    return this.measMaterialRepository.save(newMeasMaterial);
  }

  @Override
  public MeasMaterial replaceMeasMaterial(long measMaterialId, Map<String, Object> params) {
    MeasMaterial prevMeasMaterial = this.findById(measMaterialId);

    ParamReader reader = ParamReader.init(params);
    String newMeasMaterialCode = reader.stringFromKey("measCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newMeasMaterialCode)
            && !newMeasMaterialCode.equals(prevMeasMaterial.measCode())
            && this.measMaterialRepository.findFirstByMeasCode(newMeasMaterialCode).isPresent();
    if (conflict) {
      throw new BadRequestException("工具编码 {0} 已经存在", newMeasMaterialCode);
    }

    this.checkBeforeReplace(prevMeasMaterial);
    MeasMaterial currMeasMaterial = MeasMaterialHelper.assignMeasMaterial(prevMeasMaterial, params);
    this.validateMeasMaterial(currMeasMaterial);
    return this.measMaterialRepository.save(currMeasMaterial);
  }

  @Override
  public void deleteMeasMaterial(long measMaterialId) {
    MeasMaterial prevMeasMaterial = this.findById(measMaterialId);
    this.checkBeforeDelete(prevMeasMaterial);
    this.measMaterialRepository.delete(prevMeasMaterial);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MeasMaterial> findIdleMeasMaterials() {
    return this.measMaterialRepository.findIdleMeasMaterials();
  }

  @Override
  @Transactional(readOnly = true)
  public List<MeasMaterial> findRuleIdleMeasMaterials() {
    return this.measMaterialRepository.findRuleIdleMeasMaterials();
  }
}
