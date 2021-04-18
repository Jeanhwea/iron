package com.avic.mti.iron.main.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainMaterial;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MainMaterialService {
  Page<MainMaterial> findMainMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<MainMaterial> findAllMainMaterials(Map<String, Object> params);

  List<MainMaterial> findIdleMainMaterials(Map<String, Object> params);

  List<MainMaterial> findAllMainMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  MainMaterial findById(long mainMaterialId);

  MainMaterial createMainMaterial(Map<String, Object> params);

  MainMaterial replaceMainMaterial(long mainMaterialId, Map<String, Object> params);

  void deleteMainMaterial(long mainMaterialId);

  List<MainMaterial> findIdleMainMaterials();

  List<MainMaterial> findRuleIdleMainMaterials();
}
