package com.avic.mti.iron.proxy.controller;

import com.avic.mti.iron.proxy.service.ProxyCustomerService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class ProxyCustomerController {

  public static final Logger logger = LoggerFactory.getLogger(ProxyCustomerController.class);

  private final ProxyCustomerService proxyCustomerService;

  @Autowired
  public ProxyCustomerController(ProxyCustomerService proxyCustomerService) {
    this.proxyCustomerService = proxyCustomerService;
  }

  @GetMapping("/**")
  public Object getCustomersByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestParam Map<String, Object> params) {
    return this.proxyCustomerService.proxyGetForCustomer(request.getRequestURI(), headers, params);
  }
}
