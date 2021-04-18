package com.avic.mti.iron.waste.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdMaterialVo;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface WprdMaterialVoService {
  Page<WprdMaterialVo> findWprdMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<WprdMaterialVo> findAllWprdMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);
}
