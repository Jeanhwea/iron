package com.avic.mti.iron.proxy.service.impl;

import com.avic.mti.iron.common.constant.AppNameConst;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.proxy.service.ProxyTestService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ProxyTestServiceImpl implements ProxyTestService {

  public static final Logger logger = LoggerFactory.getLogger(ProxyTestServiceImpl.class);

  private final Eunuch eunuch;

  @Autowired
  public ProxyTestServiceImpl(Eunuch eunuch) {
    this.eunuch = eunuch;
  }

  @Override
  public Object proxyForTest(String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 edintown/tests: {}", endpoint);
    return eunuch.getForObject(AppNameConst.EDINTOWN_NAME, endpoint, headers, params);
  }
}
