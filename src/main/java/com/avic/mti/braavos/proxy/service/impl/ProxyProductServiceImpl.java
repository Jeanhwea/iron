package com.avic.mti.iron.proxy.service.impl;

import com.avic.mti.iron.common.constant.AppNameConst;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import com.avic.mti.iron.proxy.service.ProxyProductService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ProxyProductServiceImpl implements ProxyProductService {

  public static final Logger logger = LoggerFactory.getLogger(ProxyProductServiceImpl.class);

  private final Eunuch eunuch;

  @Autowired
  public ProxyProductServiceImpl(Eunuch eunuch) {
    this.eunuch = eunuch;
  }

  @Override
  public Object proxyGetForProduct(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 ciqikou/products: {}", endpoint);
    return eunuch.getForObject(AppNameConst.CIQIKOU_NAME, endpoint, headers, params);
  }

  @Override
  public List<Map<String, Object>> getProductBOMList(List<String> prdtCodeList) {
    Map<String, Object> params = ParamBuilder.init().put("prdtCodes", prdtCodeList).params();
    return eunuch.postForList(AppNameConst.CIQIKOU_NAME, "/products/bom/bulk/read", null, params);
  }
}
