package com.avic.mti.iron.measure.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasMaterial;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MeasMaterialService {
  Page<MeasMaterial> findMeasMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<MeasMaterial> findAllMeasMaterials(Map<String, Object> params);

  List<MeasMaterial> findIdleMeasMaterials(Map<String, Object> params);

  List<MeasMaterial> findAllMeasMaterials(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  MeasMaterial findById(long measMaterialId);

  MeasMaterial createMeasMaterial(Map<String, Object> params);

  MeasMaterial replaceMeasMaterial(long measMaterialId, Map<String, Object> params);

  void deleteMeasMaterial(long measMaterialId);

  List<MeasMaterial> findIdleMeasMaterials();

  List<MeasMaterial> findRuleIdleMeasMaterials();
}
