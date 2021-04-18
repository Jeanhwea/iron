package com.avic.mti.iron.proxy.controller;

import com.avic.mti.iron.proxy.service.ProxyProductService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProxyProductController {

  public static final Logger logger = LoggerFactory.getLogger(ProxyProductController.class);

  private final ProxyProductService proxyProductService;

  @Autowired
  public ProxyProductController(ProxyProductService proxyProductService) {
    this.proxyProductService = proxyProductService;
  }

  @GetMapping("/**")
  public Object getProductsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestParam Map<String, Object> params) {
    return this.proxyProductService.proxyGetForProduct(request.getRequestURI(), headers, params);
  }
}
