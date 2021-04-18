package com.avic.mti.iron.tool.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface ToolInService {
  Page<ToolIn> findToolIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<ToolIn> findAllToolIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<ToolIn> findAllToolIns(Map<String, Object> params);

  ToolIn findById(long toolInId);

  ToolIn createToolIn(Map<String, Object> params);

  ToolIn replaceToolIn(long toolInId, Map<String, Object> params);

  void deleteToolIn(long toolInId);

  ToolIn exitToolIn(ToolOut toolOut);

  void updateNumbers(long toolInId, long deltaStkNum, long deltaAvlNum, long deltaPlnNum);
}
