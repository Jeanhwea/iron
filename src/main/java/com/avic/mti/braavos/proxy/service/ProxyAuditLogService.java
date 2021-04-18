package com.avic.mti.iron.proxy.service;

import java.util.Map;
import org.springframework.http.HttpHeaders;

public interface ProxyAuditLogService {
  Object proxyGetForAuditLog(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPostForAuditLog(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPutForAuditLog(String endpoint, HttpHeaders headers, Map<String, Object> params);
}
