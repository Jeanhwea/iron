package com.avic.mti.iron.main.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.main.domain.entity.Retest;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface RetestService {

  Page<Retest> findRetests(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<Retest> findAllRetests(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  Retest findById(long retestId);

  Retest createRetest(Map<String, Object> params);

  Retest replaceRetest(long retestId, Map<String, Object> params);

  void deleteRetest(long retestId);

  byte[] getRetestFile(long retestId);

  void setRetestFile(String updateUser, long retestId, MultipartFile multipartFile);
}
