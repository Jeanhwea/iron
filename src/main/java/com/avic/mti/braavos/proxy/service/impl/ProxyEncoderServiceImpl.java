package com.avic.mti.iron.proxy.service.impl;

import com.avic.mti.iron.common.constant.AppNameConst;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.proxy.service.ProxyEncoderService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ProxyEncoderServiceImpl implements ProxyEncoderService {

  public static final Logger logger = LoggerFactory.getLogger(ProxyEncoderServiceImpl.class);

  private final Eunuch eunuch;

  @Autowired
  public ProxyEncoderServiceImpl(Eunuch eunuch) {
    this.eunuch = eunuch;
  }

  @Override
  public Object proxyGetForEncoder(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 skree/encoder: {}", endpoint);
    return eunuch.getForObject(AppNameConst.SKREE_NAME, endpoint, headers, params);
  }

  @Override
  public Object proxyPostForEncoder(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 skree/encoder: {}", endpoint);
    return eunuch.postForMap(AppNameConst.SKREE_NAME, endpoint, headers, params);
  }

  @Override
  public Object proxyPutForEncoder(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 skree/encoder: {}", endpoint);
    return eunuch.putForMap(AppNameConst.SKREE_NAME, endpoint, headers, params);
  }

  @Override
  public Map<String, Object> generate(String ruleCode, long total) {
    Map<String, Object> params =
        ParamBuilder.init().put("ruleCode", ruleCode).put("total", total).params();
    String endpoint = "/codes/rules/generate";
    return eunuch.postForMap(AppNameConst.SKREE_NAME, endpoint, null, params);
  }

  @Override
  public String nextval(String ruleCode) {
    Map<String, Object> params = ParamBuilder.init().put("ruleCode", ruleCode).params();
    String endpoint = "/codes/rules/nextval";
    Map<String, Object> result = eunuch.postForMap(AppNameConst.SKREE_NAME, endpoint, null, params);
    String nextValue =
        ParamReader.init(result)
            .stringFromKey("nextval")
            .orElseThrow(() -> new BadRequestException("返回值缺少 nextval 字段"));
    return nextValue;
  }
}
