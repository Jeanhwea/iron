package com.avic.mti.iron.proxy.service.impl;

import com.avic.mti.iron.common.constant.AppNameConst;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.proxy.service.ProxyAuditLogService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ProxyAuditLogServiceImpl implements ProxyAuditLogService {

  public static final Logger logger = LoggerFactory.getLogger(ProxyAuditLogServiceImpl.class);

  private final Eunuch eunuch;

  @Autowired
  public ProxyAuditLogServiceImpl(Eunuch eunuch) {
    this.eunuch = eunuch;
  }

  @Override
  public Object proxyGetForAuditLog(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 astapor/auditLog: {}", endpoint);
    return eunuch.getForObject(AppNameConst.ASTAPOR_NAME, endpoint, headers, params);
  }

  @Override
  public Object proxyPostForAuditLog(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 astapor/auditLog: {}", endpoint);
    return eunuch.postForMap(AppNameConst.ASTAPOR_NAME, endpoint, headers, params);
  }

  @Override
  public Object proxyPutForAuditLog(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 astapor/auditLog: {}", endpoint);
    return eunuch.putForMap(AppNameConst.ASTAPOR_NAME, endpoint, headers, params);
  }
}
