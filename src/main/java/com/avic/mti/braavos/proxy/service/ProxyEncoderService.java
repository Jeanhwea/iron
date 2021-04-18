package com.avic.mti.iron.proxy.service;

import java.util.Map;
import org.springframework.http.HttpHeaders;

public interface ProxyEncoderService {
  Object proxyGetForEncoder(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPostForEncoder(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPutForEncoder(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Map<String, Object> generate(String ruleCode, long total);

  String nextval(String ruleCode);
}
