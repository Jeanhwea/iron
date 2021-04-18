package com.avic.mti.iron.tool.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolMaterial;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface ToolMaterialService {
  Page<ToolMaterial> findToolMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<ToolMaterial> findAllToolMaterials(Map<String, Object> params);

  List<ToolMaterial> findIdleToolMaterials(Map<String, Object> params);

  List<ToolMaterial> findAllToolMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  ToolMaterial findById(long toolMaterialId);

  ToolMaterial createToolMaterial(Map<String, Object> params);

  ToolMaterial replaceToolMaterial(long toolMaterialId, Map<String, Object> params);

  void deleteToolMaterial(long toolMaterialId);

  List<ToolMaterial> findIdleToolMaterials();

  List<ToolMaterial> findRuleIdleToolMaterials();
}
