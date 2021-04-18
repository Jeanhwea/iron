package com.avic.mti.iron.main.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.main.domain.entity.RetestDetail;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface RetestDetailService {

  Page<RetestDetail> findRetestDetails(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<RetestDetail> findAllRetestDetails(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  RetestDetail findById(long retestId);

  RetestDetail createRetestDetail(Map<String, Object> params);

  RetestDetail replaceRetestDetail(long retestId, Map<String, Object> params);

  void deleteRetestDetail(long retestId);

  List<RetestDetail> createRetestDetailInBulk(List<Map<String, Object>> paramList);

  void removeRetestDetailInBulk(Map<String, Object> params);
}
