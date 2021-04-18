package com.avic.mti.iron.main.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MainOutService {

  Page<MainOut> findMainOuts(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<MainOut> findAllMainOuts(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  MainOut findById(long mainOutId);

  MainOut createMainOut(Map<String, Object> params);

  MainOut createMainOut2(Map<String, Object> params);

  MainOut replaceMainOut(long mainOutId, Map<String, Object> params);

  void deleteMainOut(long mainOutId);

  List<MainOut> createMainOutInBulk(List<Map<String, Object>> paramList);

  List<MainOut> createMainOutInBulk2(List<Map<String, Object>> paramList);

  List<MainOut> transferMainOutInBulk(
      List<Map<String, Object>> paramOutList, Map<String, Object> mainInParam);
}
