package com.avic.mti.iron.proxy.service;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;

public interface ProxyProductService {
  Object proxyGetForProduct(String endpoint, HttpHeaders headers, Map<String, Object> params);

  List<Map<String, Object>> getProductBOMList(List<String> prdtCodeList);
}
