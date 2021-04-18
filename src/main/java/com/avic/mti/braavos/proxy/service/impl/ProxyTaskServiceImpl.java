package com.avic.mti.iron.proxy.service.impl;

import com.avic.mti.iron.common.constant.AppNameConst;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.proxy.service.ProxyTaskService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ProxyTaskServiceImpl implements ProxyTaskService {

  public static final Logger logger = LoggerFactory.getLogger(ProxyTaskServiceImpl.class);

  private final Eunuch eunuch;

  @Autowired
  public ProxyTaskServiceImpl(Eunuch eunuch) {
    this.eunuch = eunuch;
  }

  @Override
  public Object proxyGetForTask(String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 ciqikou/tasks: {}", endpoint);
    return eunuch.getForObject(AppNameConst.CIQIKOU_NAME, endpoint, headers, params);
  }
}
