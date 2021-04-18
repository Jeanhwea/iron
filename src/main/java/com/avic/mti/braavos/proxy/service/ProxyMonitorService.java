package com.avic.mti.iron.proxy.service;

import java.util.Map;
import org.springframework.http.HttpHeaders;

public interface ProxyMonitorService {
  Object proxyGetForMonitor(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPostForMonitor(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPutForMonitor(String endpoint, HttpHeaders headers, Map<String, Object> params);
}
