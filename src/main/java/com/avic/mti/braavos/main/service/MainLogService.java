package com.avic.mti.iron.main.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.MainLog;
import com.avic.mti.iron.main.domain.entity.MainOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MainLogService {
  Page<MainLog> findMainLogs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<MainLog> findAllMainLogs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  MainLog createMainLogForIn(MainIn mainIn);

  MainLog createMainLogForOut(MainOut mainOut);
}
