package com.avic.mti.iron.proxy.service.impl;

import com.avic.mti.iron.common.constant.AppNameConst;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import com.avic.mti.iron.proxy.service.ProxyReportService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ProxyReportServiceImpl implements ProxyReportService {

  public static final Logger logger = LoggerFactory.getLogger(ProxyReportServiceImpl.class);

  private final Eunuch eunuch;

  @Autowired
  public ProxyReportServiceImpl(Eunuch eunuch) {
    this.eunuch = eunuch;
  }

  @Override
  public Object proxyGetForReport(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 edintown/report: {}", endpoint);
    return eunuch.getForList(AppNameConst.EDINTOWN_NAME, endpoint, headers, params);
  }

  @Override
  public Object proxyPostForReport(
      String endpoint, HttpHeaders headers, Map<String, Object> params) {
    logger.info("通过代理请求 edintown/report: {}", endpoint);
    return eunuch.postForObject(AppNameConst.EDINTOWN_NAME, endpoint, headers, params);
  }

  @Override
  public List<Map<String, Object>> obtainReportDetailList(
      String prdtCode, String batchCode, List<String> smplCodes) {
    String endpoint = "/reports/details/bulk/read";
    Map<String, Object> params =
        ParamBuilder.init()
            .put("prdtCode", prdtCode)
            .put("batchCode", batchCode)
            .put("smplCodes", smplCodes)
            .params();
    return eunuch.postForList(AppNameConst.EDINTOWN_NAME, endpoint, params);
  }
}
