package com.avic.mti.iron.proxy.service;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;

public interface ProxyContractService {
  Object proxyGetForContract(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPutForContract(String endpoint, HttpHeaders headers, Map<String, Object> params);

  List<Map<String, Object>> obtainContractDetailsByBrgnId(Long brgnId);

  List<Map<String, Object>> obtainContractDetailsByCnttId(Long cnttId);

  void updateContractDetailNumbers(Long cnttDetId, Map<String, Object> params);

  void updateContractDetailNumbersInBulk(List<Map<String, Object>> toUpdate);

  Map<String, Object> updateContractHeader(
      Long cnttId, HttpHeaders headers, Map<String, Object> params);
}
