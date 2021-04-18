package com.avic.mti.iron.proxy.service;

import java.util.Map;
import org.springframework.http.HttpHeaders;

public interface ProxyPassverfService {
  Object proxyGetForPassverf(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPostForPassverf(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPutForPassverf(String endpoint, HttpHeaders headers, Map<String, Object> params);
}
