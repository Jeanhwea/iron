package com.avic.mti.iron.measure.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasMaterialVo;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MeasMaterialVoService {
  Page<MeasMaterialVo> findMeasMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<MeasMaterialVo> findAllMeasMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);
}
