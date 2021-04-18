package com.avic.mti.iron.proxy.service;

import java.util.Map;
import org.springframework.http.HttpHeaders;

public interface ProxyCustomerService {

  Object proxyGetForCustomer(String endpoint, HttpHeaders headers, Map<String, Object> params);
}
