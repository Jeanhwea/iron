package com.avic.mti.iron.proxy.service.impl;

import com.avic.mti.iron.common.constant.AppNameConst;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import com.avic.mti.iron.proxy.service.ProxyBargainService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ProxyBargainServiceImpl implements ProxyBargainService {

  public static final Logger logger = LoggerFactory.getLogger(ProxyBargainServiceImpl.class);

  private final Eunuch eunuch;

  @Autowired
  public ProxyBargainServiceImpl(Eunuch eunuch) {
    this.eunuch = eunuch;
  }

  @Override
  public Object proxyGetForBargain(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 deeplake/bargains: {}", endpoint);
    return eunuch.getForObject(AppNameConst.DEEPLAKE_NAME, endpoint, headers, params);
  }

  @Override
  public Object proxyPutForBargain(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 deeplake/bargains: {}", endpoint);
    return eunuch.putForObject(AppNameConst.DEEPLAKE_NAME, endpoint, headers, params);
  }

  @Override
  public List<Map<String, Object>> obtainBargainDetails(Long brgnId) {
    Map<String, Object> params = ParamBuilder.init().put("brgnId", brgnId).params();
    return eunuch.getForList(AppNameConst.DEEPLAKE_NAME, "/bargains/details", params);
  }

  @Override
  public void updateBargainDetailNumbers(Long brgnDetId, Map<String, Object> params) {
    String endpoint = String.format("/bargains/details/%d/numbers", brgnDetId);
    eunuch.putForMap(AppNameConst.DEEPLAKE_NAME, endpoint, params);
  }

  @Override
  public void updateBargainDetailNumbersInBulk(List<Map<String, Object>> toUpdate) {
    String endpoint = "/bargains/details/numbers";
    Map<String, Object> params = ParamBuilder.init().put("toUpdate", toUpdate).params();
    eunuch.putForList(AppNameConst.DEEPLAKE_NAME, endpoint, params);
  }

  @Override
  public void updateBargainExpectNumbers(Long brgnDetId, Map<String, Object> params) {
    String endpoint = String.format("/bargains/details/%d/expect-numbers", brgnDetId);
    eunuch.putForMap(AppNameConst.DEEPLAKE_NAME, endpoint, params);
  }

  @Override
  public void updateBargainExpectNumbersInBulk(List<Map<String, Object>> toUpdate) {
    String endpoint = "/bargains/details/expect-numbers";
    Map<String, Object> params = ParamBuilder.init().put("toUpdate", toUpdate).params();
    eunuch.putForList(AppNameConst.DEEPLAKE_NAME, endpoint, params);
  }
}
