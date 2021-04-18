package com.avic.mti.iron.tool.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import com.avic.mti.iron.tool.domain.entity.ToolShelf;
import java.util.List;
import java.util.Map;

public interface ToolShelfService {
  List<ToolShelf> findAllToolShelfs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  ToolShelf findById(long toolShelfId);

  ToolShelf createToolShelf(Map<String, Object> params);

  ToolShelf replaceToolShelf(long toolShelfId, Map<String, Object> params);

  void deleteToolShelf(long toolShelfId);

  List<ToolShelf> enterShelfList(ToolIn toolIn);

  List<ToolShelf> exitShelfList(ToolOut toolOut);

  List<ToolIn> findShelfIns(Map<String, Object> params);
}
