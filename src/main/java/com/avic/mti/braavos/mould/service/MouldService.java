package com.avic.mti.iron.mould.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.mould.domain.entity.Mould;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface MouldService {
  Page<Mould> findMoulds(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<Mould> findAllMoulds(Map<String, Object> params);

  List<Mould> findIdleMoulds(Map<String, Object> params);

  List<Mould> findAllMoulds(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  Mould findById(long auxiMaterialId);

  Mould createMould(Map<String, Object> params);

  Mould replaceMould(long auxiMaterialId, Map<String, Object> params);

  void deleteMould(long auxiMaterialId);

  List<Mould> findIdleMoulds();
}
