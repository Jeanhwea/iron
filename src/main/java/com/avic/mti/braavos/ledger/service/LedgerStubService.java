package com.avic.mti.iron.ledger.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.ledger.domain.entity.LedgerStub;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface LedgerStubService {
  Page<LedgerStub> findLedgerStubs(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<LedgerStub> findAllLedgerStubs(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  LedgerStub findById(long resourceId);

  LedgerStub createLedgerStub(Map<String, Object> params);

  LedgerStub replaceLedgerStub(long resourceId, Map<String, Object> params);

  void deleteLedgerStub(long resourceId);

  Map<String, Object> enableLedgerStubs(Map<String, Object> params);

  Map<String, Object> disableLedgerStubs(Map<String, Object> params);
}
