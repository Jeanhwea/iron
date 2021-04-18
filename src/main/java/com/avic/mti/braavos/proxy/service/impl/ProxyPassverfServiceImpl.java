package com.avic.mti.iron.proxy.service.impl;

import com.avic.mti.iron.common.constant.AppNameConst;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.proxy.service.ProxyPassverfService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ProxyPassverfServiceImpl implements ProxyPassverfService {

  public static final Logger logger = LoggerFactory.getLogger(ProxyPassverfServiceImpl.class);

  private final Eunuch eunuch;

  @Autowired
  public ProxyPassverfServiceImpl(Eunuch eunuch) {
    this.eunuch = eunuch;
  }

  @Override
  public Object proxyGetForPassverf(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 astapor/passverf: {}", endpoint);
    return eunuch.getForList(AppNameConst.ASTAPOR_NAME, endpoint, headers, params);
  }

  @Override
  public Object proxyPostForPassverf(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 astapor/passverf: {}", endpoint);
    return eunuch.postForObject(AppNameConst.ASTAPOR_NAME, endpoint, headers, params);
  }

  @Override
  public Object proxyPutForPassverf(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 astapor/passverf: {}", endpoint);
    return eunuch.putForObject(AppNameConst.ASTAPOR_NAME, endpoint, headers, params);
  }
}
