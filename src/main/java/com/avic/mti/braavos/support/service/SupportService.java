package com.avic.mti.iron.support.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.support.domain.entity.Support;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface SupportService {
  Page<Support> findSupports(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<Support> findAllSupports(Map<String, Object> params);

  List<Support> findIdleSupports(Map<String, Object> params);

  List<Support> findAllSupports(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  Support findById(long supportId);

  Support createSupport(Map<String, Object> params);

  Support replaceSupport(long supportId, Map<String, Object> params);

  void deleteSupport(long supportId);

  List<Support> findIdleSupports();
}
