package com.avic.mti.iron.proxy.service;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;

public interface ProxyReportService {

  Object proxyGetForReport(String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object proxyPostForReport(String endpoint, HttpHeaders headers, Map<String, Object> params);

  List<Map<String, Object>> obtainReportDetailList(
      String prdtCode, String batchCode, List<String> smplCodes);
}
