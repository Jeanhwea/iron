package com.avic.mti.iron.tool.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.domain.entity.ToolLog;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface ToolLogService {
  Page<ToolLog> findToolLogs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<ToolLog> findAllToolLogs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  ToolLog createToolLogForIn(ToolIn toolIn);

  ToolLog createToolLogForOut(ToolOut toolOut);
}
