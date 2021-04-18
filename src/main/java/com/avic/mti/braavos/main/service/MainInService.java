package com.avic.mti.iron.main.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.MainOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MainInService {
  Page<MainIn> findMainIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<MainIn> findAllMainIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<MainIn> findAllMainIns(Map<String, Object> params);

  MainIn findById(long mainInId);

  MainIn createMainIn(Map<String, Object> params);

  MainIn replaceMainIn(long mainInId, Map<String, Object> params);

  void deleteMainIn(long mainInId);

  MainIn exitMainIn(MainOut mainOut);

  void updateNumbers(long mainInId, long deltaStkNum, long deltaAvlNum, long deltaPlnNum);

  void fixMainPlnNum(Long prevInId, Long deltaPlnNum);

  List<MainIn> createMainInsInBulk(List<Map<String, Object>> paramList);
}
