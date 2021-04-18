package com.avic.mti.iron.measure.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MeasInService {
  Page<MeasIn> findMeasIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<MeasIn> findAllMeasIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<MeasIn> findAllMeasIns(Map<String, Object> params);

  MeasIn findById(long measInId);

  MeasIn createMeasIn(Map<String, Object> params);

  MeasIn replaceMeasIn(long measInId, Map<String, Object> params);

  void deleteMeasIn(long measInId);

  MeasIn exitMeasIn(MeasOut measOut);

  void updateNumbers(long measInId, long deltaStkNum, long deltaAvlNum, long deltaPlnNum);

  MeasIn returnMeasIn(Map<String, Object> params);
}
