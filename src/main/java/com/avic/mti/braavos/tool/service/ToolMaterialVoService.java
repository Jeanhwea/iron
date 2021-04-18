package com.avic.mti.iron.tool.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolMaterialVo;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface ToolMaterialVoService {
  Page<ToolMaterialVo> findToolMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<ToolMaterialVo> findAllToolMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);
}
