package com.avic.mti.iron.auxiliary.service;

import com.avic.mti.iron.auxiliary.domain.entity.AuxiMaterial;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface AuxiMaterialService {
  Page<AuxiMaterial> findAuxiMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<AuxiMaterial> findAllAuxiMaterials(Map<String, Object> params);

  List<AuxiMaterial> findIdleAuxiMaterials(Map<String, Object> params);

  List<AuxiMaterial> findAllAuxiMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  AuxiMaterial findById(long auxiMaterialId);

  AuxiMaterial createAuxiMaterial(Map<String, Object> params);

  AuxiMaterial replaceAuxiMaterial(long auxiMaterialId, Map<String, Object> params);

  void deleteAuxiMaterial(long auxiMaterialId);

  List<AuxiMaterial> findIdleAuxiMaterials();
}
