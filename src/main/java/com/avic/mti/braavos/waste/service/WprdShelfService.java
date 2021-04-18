package com.avic.mti.iron.waste.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import com.avic.mti.iron.waste.domain.entity.WprdShelf;
import java.util.List;
import java.util.Map;

public interface WprdShelfService {
  List<WprdShelf> findAllWprdShelfs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  WprdShelf findById(long wprdShelfId);

  WprdShelf createWprdShelf(Map<String, Object> params);

  WprdShelf replaceWprdShelf(long wprdShelfId, Map<String, Object> params);

  void deleteWprdShelf(long wprdShelfId);

  List<WprdShelf> enterShelfList(WprdIn wprdIn);

  List<WprdShelf> exitShelfList(WprdOut wprdOut);

  List<WprdIn> findShelfIns(Map<String, Object> params);
}
