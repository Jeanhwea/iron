package com.avic.mti.iron.measure.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import com.avic.mti.iron.measure.domain.entity.MeasShelf;
import java.util.List;
import java.util.Map;

public interface MeasShelfService {
  List<MeasShelf> findAllMeasShelfs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  MeasShelf findById(long measShelfId);

  MeasShelf createMeasShelf(Map<String, Object> params);

  MeasShelf replaceMeasShelf(long measShelfId, Map<String, Object> params);

  void deleteMeasShelf(long measShelfId);

  List<MeasShelf> enterShelfList(MeasIn measIn);

  List<MeasShelf> exitShelfList(MeasOut measOut);

  List<MeasIn> findShelfIns(Map<String, Object> params);
}
