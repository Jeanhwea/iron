package com.avic.mti.iron.waste.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.domain.entity.WprdLog;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface WprdLogService {
  Page<WprdLog> findWprdLogs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<WprdLog> findAllWprdLogs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  WprdLog createWprdLogForIn(WprdIn wprdIn);

  WprdLog createWprdLogForOut(WprdOut wprdOut);
}
