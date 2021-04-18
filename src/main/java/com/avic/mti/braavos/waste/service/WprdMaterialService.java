package com.avic.mti.iron.waste.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdMaterial;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface WprdMaterialService {
  Page<WprdMaterial> findWprdMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<WprdMaterial> findAllWprdMaterials(Map<String, Object> params);

  List<WprdMaterial> findIdleWprdMaterials(Map<String, Object> params);

  List<WprdMaterial> findAllWprdMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  WprdMaterial findById(long wprdMaterialId);

  WprdMaterial createWprdMaterial(Map<String, Object> params);

  WprdMaterial replaceWprdMaterial(long wprdMaterialId, Map<String, Object> params);

  void deleteWprdMaterial(long wprdMaterialId);

  List<WprdMaterial> findIdleWprdMaterials();

  List<WprdMaterial> findRuleIdleWprdMaterials();
}
