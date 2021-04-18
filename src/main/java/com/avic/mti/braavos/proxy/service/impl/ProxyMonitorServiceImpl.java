package com.avic.mti.iron.proxy.service.impl;

import com.avic.mti.iron.common.constant.AppNameConst;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.proxy.service.ProxyMonitorService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ProxyMonitorServiceImpl implements ProxyMonitorService {

  public static final Logger logger = LoggerFactory.getLogger(ProxyMonitorServiceImpl.class);

  private final Eunuch eunuch;

  @Autowired
  public ProxyMonitorServiceImpl(Eunuch eunuch) {
    this.eunuch = eunuch;
  }

  @Override
  public Object proxyGetForMonitor(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 ciqikou/monitor: {}", endpoint);
    return eunuch.getForObject(AppNameConst.CIQIKOU_NAME, endpoint, headers, params);
  }

  @Override
  public Object proxyPostForMonitor(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 ciqikou/monitor: {}", endpoint);
    return eunuch.postForMap(AppNameConst.CIQIKOU_NAME, endpoint, headers, params);
  }

  @Override
  public Object proxyPutForMonitor(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 ciqikou/monitor: {}", endpoint);
    return eunuch.putForMap(AppNameConst.CIQIKOU_NAME, endpoint, headers, params);
  }
}
