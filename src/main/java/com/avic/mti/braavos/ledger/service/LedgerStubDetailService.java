package com.avic.mti.iron.ledger.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.ledger.domain.entity.LedgerStubDetail;
import com.avic.mti.iron.main.domain.entity.MainOut;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface LedgerStubDetailService {
  Page<LedgerStubDetail> findLedgerStubDetails(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<LedgerStubDetail> findAllLedgerStubDetails(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  LedgerStubDetail findById(long resourceId);

  LedgerStubDetail createLedgerStubDetail(Map<String, Object> params);

  List<LedgerStubDetail> createLedgerStubDetailInBulk(List<Map<String, Object>> paramList);

  LedgerStubDetail replaceLedgerStubDetail(long resourceId, Map<String, Object> params);

  void deleteLedgerStubDetail(long resourceId);

  void deleteLedgerStubDetailInBulk(Map<String, Object> params);

  LedgerStubDetail exitStubDetail(MainOut mainOut);
}
