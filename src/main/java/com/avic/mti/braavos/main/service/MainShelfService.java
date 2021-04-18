package com.avic.mti.iron.main.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.MainOut;
import com.avic.mti.iron.main.domain.entity.MainShelf;
import java.util.List;
import java.util.Map;

public interface MainShelfService {
  List<MainShelf> findAllMainShelfs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  MainShelf findById(long mainShelfId);

  MainShelf createMainShelf(Map<String, Object> params);

  MainShelf replaceMainShelf(long mainShelfId, Map<String, Object> params);

  void deleteMainShelf(long mainShelfId);

  List<MainShelf> enterShelfList(MainIn mainIn);

  List<MainShelf> exitShelfList(MainOut mainOut);

  List<MainIn> findShelfIns(Map<String, Object> params);
}
