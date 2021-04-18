package com.avic.mti.iron.tool.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface ToolOutService {

  Page<ToolOut> findToolOuts(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<ToolOut> findAllToolOuts(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  ToolOut findById(long toolOutId);

  ToolOut createToolOut(Map<String, Object> params);

  ToolOut replaceToolOut(long toolOutId, Map<String, Object> params);

  void deleteToolOut(long toolOutId);

  List<ToolOut> createToolOutInBulk(List<Map<String, Object>> paramList);
}
