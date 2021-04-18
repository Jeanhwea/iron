package com.avic.mti.iron.proxy.service;

import java.util.Map;
import org.springframework.http.HttpHeaders;

public interface ProxyUnitService {
  Object proxyGetForUnit(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPostForUnit(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPutForUnit(String endpoint, HttpHeaders headers, Map<String, Object> params);
}
