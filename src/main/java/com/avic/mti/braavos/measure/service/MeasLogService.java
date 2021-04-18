package com.avic.mti.iron.measure.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
import com.avic.mti.iron.measure.domain.entity.MeasLog;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MeasLogService {
  Page<MeasLog> findMeasLogs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<MeasLog> findAllMeasLogs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  MeasLog createMeasLogForIn(MeasIn measIn);

  MeasLog createMeasLogForOut(MeasOut measOut);
}
