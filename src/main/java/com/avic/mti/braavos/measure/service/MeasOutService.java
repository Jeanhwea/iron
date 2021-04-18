package com.avic.mti.iron.measure.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MeasOutService {

  Page<MeasOut> findMeasOuts(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<MeasOut> findAllMeasOuts(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  MeasOut findById(long measOutId);

  MeasOut createMeasOut(Map<String, Object> params);

  MeasOut replaceMeasOut(long measOutId, Map<String, Object> params);

  void deleteMeasOut(long measOutId);

  List<MeasOut> createMeasOutInBulk(List<Map<String, Object>> paramList);
}
