package com.avic.mti.iron.proxy.service;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;

public interface ProxyBargainService {
  Object proxyGetForBargain(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPutForBargain(String endpoint, HttpHeaders headers, Map<String, Object> params);

  List<Map<String, Object>> obtainBargainDetails(Long brgnId);

  void updateBargainDetailNumbers(Long brgnDetId, Map<String, Object> params);

  void updateBargainDetailNumbersInBulk(List<Map<String, Object>> toUpdate);

  void updateBargainExpectNumbers(Long brgnDetId, Map<String, Object> params);

  void updateBargainExpectNumbersInBulk(List<Map<String, Object>> toUpdate);
}
