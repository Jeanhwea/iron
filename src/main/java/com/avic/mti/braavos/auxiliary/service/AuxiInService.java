package com.avic.mti.iron.auxiliary.service;

import com.avic.mti.iron.auxiliary.domain.entity.AuxiIn;
import com.avic.mti.iron.auxiliary.domain.entity.AuxiOut;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface AuxiInService {
  Page<AuxiIn> findAuxiIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<AuxiIn> findAllAuxiIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<AuxiIn> findAllAuxiIns(Map<String, Object> params);

  AuxiIn findById(long auxiInId);

  AuxiIn createAuxiIn(Map<String, Object> params);

  AuxiIn replaceAuxiIn(long auxiInId, Map<String, Object> params);

  void deleteAuxiIn(long auxiInId);

  AuxiIn exitAuxiIn(AuxiOut auxiOut);

  void updateNumbers(long auxiInId, long deltaStkNum, long deltaAvlNum, long deltaPlnNum);
}
