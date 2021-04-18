package com.avic.mti.iron.tool.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.asset.AssetInTypeEnum;
import com.avic.mti.iron.common.symbol.tool.ToolMaterialCateEnum;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.domain.entity.ToolMaterial;
import com.avic.mti.iron.tool.domain.repo.ToolInRepository;
import com.avic.mti.iron.tool.domain.repo.ToolMaterialRepository;
import com.avic.mti.iron.tool.helper.ToolMaterialHelper;
import com.avic.mti.iron.tool.service.ToolMaterialService;
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
public class ToolMaterialServiceImpl implements ToolMaterialService {

  public static final Logger logger = LoggerFactory.getLogger(ToolMaterialServiceImpl.class);

  private final ToolMaterialRepository toolMaterialRepository;
  private final ToolInRepository toolInRepository;

  private final MesConditionBuilder<ToolMaterial> mesConditionBuilder;

  @Autowired
  public ToolMaterialServiceImpl(
      ToolMaterialRepository toolMaterialRepository,
      ToolInRepository toolInRepository,
      MesConditionBuilder<ToolMaterial> mesConditionBuilder) {
    this.toolMaterialRepository = toolMaterialRepository;
    this.toolInRepository = toolInRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateToolMaterial(ToolMaterial toolMaterial) {
    if (AssetInTypeEnum.of(toolMaterial.inType()) == AssetInTypeEnum.Unknown) {
      throw new UnknownEnumException("inType", toolMaterial.inType(), AssetInTypeEnum.expect());
    }

    if (ToolMaterialCateEnum.of(toolMaterial.toolCate()) == ToolMaterialCateEnum.Unknown) {
      throw new UnknownEnumException(
          "toolCate", toolMaterial.toolCate(), ToolMaterialCateEnum.expect());
    }
  }

  private void checkBeforeCreate(ToolMaterial toolMaterial) {
    // pass
  }

  private void checkBeforeReplace(ToolMaterial toolMaterial) {
    // pass
  }

  private void checkBeforeDelete(ToolMaterial toolMaterial) {
    List<ToolIn> toolIns = this.toolInRepository.findByToolId(toolMaterial.id());
    if (!toolIns.isEmpty()) {
      throw new BadRequestException("工具 {0} 存在 {1} 条对应的入库记录，不允许删除", toolMaterial, toolIns.size());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ToolMaterial> findToolMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<ToolMaterial> builder = this.mesConditionBuilder.init(params, fields);
    return this.toolMaterialRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<ToolMaterial> findAllToolMaterials(Map<String, Object> params) {
    List<String> toolCodes =
        ParamReader.init(params)
            .listStringFromKey("toolCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 toolCodes 参数"));
    return this.toolMaterialRepository.findAllByToolCodeIn(toolCodes);
  }

  @Override
  public List<ToolMaterial> findIdleToolMaterials(Map<String, Object> params) {
    List<String> toolCodes =
        ParamReader.init(params)
            .listStringFromKey("toolCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 toolCodes 参数"));
    if (toolCodes.isEmpty()) {
      return this.toolMaterialRepository.fetchAll();
    }

    return this.toolMaterialRepository.findAllByToolCodeNotIn(toolCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ToolMaterial> findAllToolMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<ToolMaterial> builder = this.mesConditionBuilder.init(params, fields);
    return this.toolMaterialRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public ToolMaterial findById(long toolMaterialId) {
    return this.toolMaterialRepository
        .fetchById(toolMaterialId)
        .orElseThrow(() -> new DatumNotExistException("ToolMaterial", toolMaterialId));
  }

  @Override
  public ToolMaterial createToolMaterial(Map<String, Object> params) {
    ToolMaterial newToolMaterial =
        ToolMaterialHelper.assignToolMaterial(new ToolMaterial(), params);

    boolean conflict =
        this.toolMaterialRepository.findFirstByToolCode(newToolMaterial.toolCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("工具编码 {0} 已经存在", newToolMaterial.toolCode());
    }

    this.checkBeforeCreate(newToolMaterial);
    this.validateToolMaterial(newToolMaterial);
    return this.toolMaterialRepository.save(newToolMaterial);
  }

  @Override
  public ToolMaterial replaceToolMaterial(long toolMaterialId, Map<String, Object> params) {
    ToolMaterial prevToolMaterial = this.findById(toolMaterialId);

    ParamReader reader = ParamReader.init(params);
    String newToolMaterialCode = reader.stringFromKey("toolCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newToolMaterialCode)
            && !newToolMaterialCode.equals(prevToolMaterial.toolCode())
            && this.toolMaterialRepository.findFirstByToolCode(newToolMaterialCode).isPresent();
    if (conflict) {
      throw new BadRequestException("工具编码 {0} 已经存在", newToolMaterialCode);
    }

    this.checkBeforeReplace(prevToolMaterial);
    ToolMaterial currToolMaterial = ToolMaterialHelper.assignToolMaterial(prevToolMaterial, params);
    this.validateToolMaterial(currToolMaterial);
    return this.toolMaterialRepository.save(currToolMaterial);
  }

  @Override
  public void deleteToolMaterial(long toolMaterialId) {
    ToolMaterial prevToolMaterial = this.findById(toolMaterialId);
    this.checkBeforeDelete(prevToolMaterial);
    this.toolMaterialRepository.delete(prevToolMaterial);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ToolMaterial> findIdleToolMaterials() {
    return this.toolMaterialRepository.findIdleToolMaterials();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ToolMaterial> findRuleIdleToolMaterials() {
    return this.toolMaterialRepository.findRuleIdleToolMaterials();
  }
}
