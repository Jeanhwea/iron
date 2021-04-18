package com.avic.mti.iron.ledger.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.ledger.domain.entity.Resource;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface ResourceService {
  Page<Resource> findResources(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<Resource> findAllResources(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  Resource findById(long resourceId);

  Resource createResource(Map<String, Object> params);

  Resource replaceResource(long resourceId, Map<String, Object> params);

  void deleteResource(long resourceId);
}
