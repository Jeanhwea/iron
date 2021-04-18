package com.avic.mti.iron.proxy.service.impl;

import com.avic.mti.iron.common.constant.AppNameConst;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import com.avic.mti.iron.proxy.service.ProxyContractService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ProxyContractServiceImpl implements ProxyContractService {

  public static final Logger logger = LoggerFactory.getLogger(ProxyContractServiceImpl.class);

  private final Eunuch eunuch;

  @Autowired
  public ProxyContractServiceImpl(Eunuch eunuch) {
    this.eunuch = eunuch;
  }

  @Override
  public Object proxyGetForContract(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 deeplake/contracts: {}", endpoint);
    return eunuch.getForObject(AppNameConst.DEEPLAKE_NAME, endpoint, headers, params);
  }

  @Override
  public Object proxyPutForContract(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 deeplake/contracts: {}", endpoint);
    return eunuch.putForObject(AppNameConst.DEEPLAKE_NAME, endpoint, headers, params);
  }

  @Override
  public List<Map<String, Object>> obtainContractDetailsByBrgnId(Long brgnId) {
    Map<String, Object> params = ParamBuilder.init().put("brgnId", brgnId).params();
    return eunuch.getForList(AppNameConst.DEEPLAKE_NAME, "/contracts/details", params);
  }

  @Override
  public List<Map<String, Object>> obtainContractDetailsByCnttId(Long cnttId) {
    Map<String, Object> params = ParamBuilder.init().put("cnttId", cnttId).params();
    return eunuch.getForList(AppNameConst.DEEPLAKE_NAME, "/contracts/details", params);
  }

  @Override
  public void updateContractDetailNumbers(Long cnttDetId, Map<String, Object> params) {
    String endpoint = String.format("/contracts/details/%d/numbers", cnttDetId);
    eunuch.putForMap(AppNameConst.DEEPLAKE_NAME, endpoint, params);
  }

  @Override
  public void updateContractDetailNumbersInBulk(List<Map<String, Object>> toUpdate) {
    String endpoint = "/contracts/details/numbers";
    Map<String, Object> params = ParamBuilder.init().put("toUpdate", toUpdate).params();
    eunuch.putForList(AppNameConst.DEEPLAKE_NAME, endpoint, params);
  }

  @Override
  public Map<String, Object> updateContractHeader(
      Long cnttId, HttpHeaders headers, Map<String, Object> params) {
    String endpoint = String.format("/contracts/%d", cnttId);
    return eunuch.putForMap(AppNameConst.DEEPLAKE_NAME, endpoint, headers, params);
  }
}
