package com.avic.mti.iron.main.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.asset.AssetInTypeEnum;
import com.avic.mti.iron.common.symbol.main.MainMaterialCateEnum;
import com.avic.mti.iron.common.symbol.main.MainMaterialTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.MainMaterial;
import com.avic.mti.iron.main.domain.repo.MainInRepository;
import com.avic.mti.iron.main.domain.repo.MainMaterialRepository;
import com.avic.mti.iron.main.helper.MainMaterialHelper;
import com.avic.mti.iron.main.service.MainMaterialService;
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
public class MainMaterialServiceImpl implements MainMaterialService {

  public static final Logger logger = LoggerFactory.getLogger(MainMaterialServiceImpl.class);

  private final MainMaterialRepository mainMaterialRepository;
  private final MainInRepository mainInRepository;

  private final MesConditionBuilder<MainMaterial> mesConditionBuilder;

  @Autowired
  public MainMaterialServiceImpl(
      MainMaterialRepository mainMaterialRepository,
      MainInRepository mainInRepository,
      MesConditionBuilder<MainMaterial> mesConditionBuilder) {
    this.mainMaterialRepository = mainMaterialRepository;
    this.mainInRepository = mainInRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateMainMaterial(MainMaterial mainMaterial) {
    if (AssetInTypeEnum.of(mainMaterial.inType()) == AssetInTypeEnum.Unknown) {
      throw new UnknownEnumException("inType", mainMaterial.inType(), AssetInTypeEnum.expect());
    }

    if (MainMaterialCateEnum.of(mainMaterial.mainCate()) == MainMaterialCateEnum.Unknown) {
      throw new UnknownEnumException(
          "mainCate", mainMaterial.mainCate(), MainMaterialCateEnum.expect());
    }

    if (MainMaterialTypeEnum.of(mainMaterial.mainType()) == MainMaterialTypeEnum.Unknown) {
      throw new UnknownEnumException(
          "mainType", mainMaterial.mainType(), MainMaterialTypeEnum.expect());
    }
  }

  private void checkBeforeCreate(MainMaterial mainMaterial) {
    // pass
  }

  private void checkBeforeReplace(MainMaterial mainMaterial) {
    // pass
  }

  private void checkBeforeDelete(MainMaterial mainMaterial) {
    List<MainIn> mainIns = this.mainInRepository.findByMainId(mainMaterial.id());
    if (!mainIns.isEmpty()) {
      throw new BadRequestException("主材 {0} 存在 {1} 条对应的入库记录，不允许删除", mainMaterial, mainIns.size());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MainMaterial> findMainMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MainMaterial> builder = this.mesConditionBuilder.init(params, fields);
    return this.mainMaterialRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<MainMaterial> findAllMainMaterials(Map<String, Object> params) {
    List<String> mainCodes =
        ParamReader.init(params)
            .listStringFromKey("mainCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 mainCodes 参数"));
    return this.mainMaterialRepository.findAllByMainCodeIn(mainCodes);
  }

  @Override
  public List<MainMaterial> findIdleMainMaterials(Map<String, Object> params) {
    List<String> mainCodes =
        ParamReader.init(params)
            .listStringFromKey("mainCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 mainCodes 参数"));
    if (mainCodes.isEmpty()) {
      return this.mainMaterialRepository.fetchAll();
    }

    return this.mainMaterialRepository.findAllByMainCodeNotIn(mainCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MainMaterial> findAllMainMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MainMaterial> builder = this.mesConditionBuilder.init(params, fields);
    return this.mainMaterialRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public MainMaterial findById(long mainMaterialId) {
    return this.mainMaterialRepository
        .fetchById(mainMaterialId)
        .orElseThrow(() -> new DatumNotExistException("MainMaterial", mainMaterialId));
  }

  @Override
  public MainMaterial createMainMaterial(Map<String, Object> params) {
    MainMaterial newMainMaterial =
        MainMaterialHelper.assignMainMaterial(new MainMaterial(), params);

    boolean conflict =
        this.mainMaterialRepository.findFirstByMainCode(newMainMaterial.mainCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("主材编码 {0} 已经存在", newMainMaterial.mainCode());
    }

    this.checkBeforeCreate(newMainMaterial);
    this.validateMainMaterial(newMainMaterial);
    return this.mainMaterialRepository.save(newMainMaterial);
  }

  @Override
  public MainMaterial replaceMainMaterial(long mainMaterialId, Map<String, Object> params) {
    MainMaterial prevMainMaterial = this.findById(mainMaterialId);

    ParamReader reader = ParamReader.init(params);
    String newMainMaterialCode = reader.stringFromKey("mainCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newMainMaterialCode)
            && !newMainMaterialCode.equals(prevMainMaterial.mainCode())
            && this.mainMaterialRepository.findFirstByMainCode(newMainMaterialCode).isPresent();
    if (conflict) {
      throw new BadRequestException("主材编码 {0} 已经存在", newMainMaterialCode);
    }

    this.checkBeforeReplace(prevMainMaterial);
    MainMaterial currMainMaterial = MainMaterialHelper.assignMainMaterial(prevMainMaterial, params);
    this.validateMainMaterial(currMainMaterial);
    return this.mainMaterialRepository.save(currMainMaterial);
  }

  @Override
  public void deleteMainMaterial(long mainMaterialId) {
    MainMaterial prevMainMaterial = this.findById(mainMaterialId);
    this.checkBeforeDelete(prevMainMaterial);
    this.mainMaterialRepository.delete(prevMainMaterial);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MainMaterial> findIdleMainMaterials() {
    return this.mainMaterialRepository.findIdleMainMaterials();
  }

  @Override
  @Transactional(readOnly = true)
  public List<MainMaterial> findRuleIdleMainMaterials() {
    return this.mainMaterialRepository.findRuleIdleMainMaterials();
  }
}
