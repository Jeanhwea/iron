package com.avic.mti.iron.proxy.service.impl;

import com.avic.mti.iron.common.constant.AppNameConst;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import com.avic.mti.iron.proxy.service.ProxyFpwhService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ProxyFpwhServiceImpl implements ProxyFpwhService {

  public static final Logger logger = LoggerFactory.getLogger(ProxyFpwhServiceImpl.class);

  private final Eunuch eunuch;

  @Autowired
  public ProxyFpwhServiceImpl(Eunuch eunuch) {
    this.eunuch = eunuch;
  }

  @Override
  public Object proxyForFpwh(String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 iron/fpwh: {}", endpoint);
    return eunuch.getForObject(AppNameConst.FABFORE_NAME, endpoint, headers, params);
  }

  @Override
  public List<Map<String, Object>> obtainStockInList(List<String> taskCodeList) {
    Map<String, Object> params = ParamBuilder.init().put("taskCodeList", taskCodeList).params();
    String endpoint = "/warehouses/fpwh/in/bulk/read";
    return eunuch.postForList(AppNameConst.FABFORE_NAME, endpoint, null, params);
  }
}
