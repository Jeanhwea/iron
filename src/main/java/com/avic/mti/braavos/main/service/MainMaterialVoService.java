package com.avic.mti.iron.main.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainMaterialVo;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MainMaterialVoService {
  Page<MainMaterialVo> findMainMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<MainMaterialVo> findAllMainMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);
}
