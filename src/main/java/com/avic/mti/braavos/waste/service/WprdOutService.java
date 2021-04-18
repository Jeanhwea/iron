package com.avic.mti.iron.waste.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface WprdOutService {

  Page<WprdOut> findWprdOuts(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<WprdOut> findAllWprdOuts(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  WprdOut findById(long wprdOutId);

  WprdOut createWprdOut(Map<String, Object> params);

  WprdOut replaceWprdOut(long wprdOutId, Map<String, Object> params);

  void deleteWprdOut(long wprdOutId);

  List<WprdOut> createWprdOutInBulk(List<Map<String, Object>> paramList);
}
