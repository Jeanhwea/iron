package com.avic.mti.iron.waste.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface WprdInService {
  Page<WprdIn> findWprdIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<WprdIn> findAllWprdIns(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<WprdIn> findAllWprdIns(Map<String, Object> params);

  WprdIn findById(long wprdInId);

  WprdIn createWprdIn(Map<String, Object> params);

  WprdIn replaceWprdIn(long wprdInId, Map<String, Object> params);

  void deleteWprdIn(long wprdInId);

  WprdIn exitWprdIn(WprdOut wprdOut);

  void updateNumbers(long wprdInId, long deltaStkNum, long deltaAvlNum, long deltaPlnNum);
}
