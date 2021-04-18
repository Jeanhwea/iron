package com.avic.mti.iron.waste.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.asset.AssetInTypeEnum;
import com.avic.mti.iron.common.symbol.waste.WprdMaterialCateEnum;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.domain.entity.WprdMaterial;
import com.avic.mti.iron.waste.domain.repo.WprdInRepository;
import com.avic.mti.iron.waste.domain.repo.WprdMaterialRepository;
import com.avic.mti.iron.waste.helper.WprdMaterialHelper;
import com.avic.mti.iron.waste.service.WprdMaterialService;
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
public class WprdMaterialServiceImpl implements WprdMaterialService {

  public static final Logger logger = LoggerFactory.getLogger(WprdMaterialServiceImpl.class);

  private final WprdMaterialRepository wprdMaterialRepository;
  private final WprdInRepository wprdInRepository;

  private final MesConditionBuilder<WprdMaterial> mesConditionBuilder;

  @Autowired
  public WprdMaterialServiceImpl(
      WprdMaterialRepository wprdMaterialRepository,
      WprdInRepository wprdInRepository,
      MesConditionBuilder<WprdMaterial> mesConditionBuilder) {
    this.wprdMaterialRepository = wprdMaterialRepository;
    this.wprdInRepository = wprdInRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateWprdMaterial(WprdMaterial wprdMaterial) {
    if (AssetInTypeEnum.of(wprdMaterial.inType()) == AssetInTypeEnum.Unknown) {
      throw new UnknownEnumException("inType", wprdMaterial.inType(), AssetInTypeEnum.expect());
    }

    if (WprdMaterialCateEnum.of(wprdMaterial.wprdCate()) == WprdMaterialCateEnum.Unknown) {
      throw new UnknownEnumException(
          "wprdCate", wprdMaterial.wprdCate(), WprdMaterialCateEnum.expect());
    }
  }

  private void checkBeforeCreate(WprdMaterial wprdMaterial) {
    // pass
  }

  private void checkBeforeReplace(WprdMaterial wprdMaterial) {
    // pass
  }

  private void checkBeforeDelete(WprdMaterial wprdMaterial) {
    List<WprdIn> wprdIns = this.wprdInRepository.findByWprdId(wprdMaterial.id());
    if (!wprdIns.isEmpty()) {
      throw new BadRequestException("废品 {0} 存在 {1} 条对应的入库记录，不允许删除", wprdMaterial, wprdIns.size());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Page<WprdMaterial> findWprdMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<WprdMaterial> builder = this.mesConditionBuilder.init(params, fields);
    return this.wprdMaterialRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<WprdMaterial> findAllWprdMaterials(Map<String, Object> params) {
    List<String> wprdCodes =
        ParamReader.init(params)
            .listStringFromKey("wprdCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 wprdCodes 参数"));
    return this.wprdMaterialRepository.findAllByWprdCodeIn(wprdCodes);
  }

  @Override
  public List<WprdMaterial> findIdleWprdMaterials(Map<String, Object> params) {
    List<String> wprdCodes =
        ParamReader.init(params)
            .listStringFromKey("wprdCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 wprdCodes 参数"));
    if (wprdCodes.isEmpty()) {
      return this.wprdMaterialRepository.fetchAll();
    }

    return this.wprdMaterialRepository.findAllByWprdCodeNotIn(wprdCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<WprdMaterial> findAllWprdMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<WprdMaterial> builder = this.mesConditionBuilder.init(params, fields);
    return this.wprdMaterialRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public WprdMaterial findById(long wprdMaterialId) {
    return this.wprdMaterialRepository
        .fetchById(wprdMaterialId)
        .orElseThrow(() -> new DatumNotExistException("WprdMaterial", wprdMaterialId));
  }

  @Override
  public WprdMaterial createWprdMaterial(Map<String, Object> params) {
    WprdMaterial newWprdMaterial =
        WprdMaterialHelper.assignWprdMaterial(new WprdMaterial(), params);

    boolean conflict =
        this.wprdMaterialRepository.findFirstByWprdCode(newWprdMaterial.wprdCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("废品编码 {0} 已经存在", newWprdMaterial.wprdCode());
    }

    this.checkBeforeCreate(newWprdMaterial);
    this.validateWprdMaterial(newWprdMaterial);
    return this.wprdMaterialRepository.save(newWprdMaterial);
  }

  @Override
  public WprdMaterial replaceWprdMaterial(long wprdMaterialId, Map<String, Object> params) {
    WprdMaterial prevWprdMaterial = this.findById(wprdMaterialId);

    ParamReader reader = ParamReader.init(params);
    String newWprdMaterialCode = reader.stringFromKey("wprdCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newWprdMaterialCode)
            && !newWprdMaterialCode.equals(prevWprdMaterial.wprdCode())
            && this.wprdMaterialRepository.findFirstByWprdCode(newWprdMaterialCode).isPresent();
    if (conflict) {
      throw new BadRequestException("废品编码 {0} 已经存在", newWprdMaterialCode);
    }

    this.checkBeforeReplace(prevWprdMaterial);
    WprdMaterial currWprdMaterial = WprdMaterialHelper.assignWprdMaterial(prevWprdMaterial, params);
    this.validateWprdMaterial(currWprdMaterial);
    return this.wprdMaterialRepository.save(currWprdMaterial);
  }

  @Override
  public void deleteWprdMaterial(long wprdMaterialId) {
    WprdMaterial prevWprdMaterial = this.findById(wprdMaterialId);
    this.checkBeforeDelete(prevWprdMaterial);
    this.wprdMaterialRepository.delete(prevWprdMaterial);
  }

  @Override
  @Transactional(readOnly = true)
  public List<WprdMaterial> findIdleWprdMaterials() {
    return this.wprdMaterialRepository.findIdleWprdMaterials();
  }

  @Override
  @Transactional(readOnly = true)
  public List<WprdMaterial> findRuleIdleWprdMaterials() {
    return this.wprdMaterialRepository.findRuleIdleWprdMaterials();
  }
}
