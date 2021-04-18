package com.avic.mti.iron.proxy.controller;

import com.avic.mti.iron.proxy.service.ProxyFpwhService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warehouses/fpwh")
public class ProxyFpwhController {

  public static final Logger logger = LoggerFactory.getLogger(ProxyFpwhController.class);

  private final ProxyFpwhService proxyFpwhService;

  @Autowired
  public ProxyFpwhController(ProxyFpwhService proxyFpwhService) {
    this.proxyFpwhService = proxyFpwhService;
  }

  @GetMapping("/**")
  public Object getFpwhsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestParam Map<String, Object> params) {
    return this.proxyFpwhService.proxyForFpwh(request.getRequestURI(), headers, params);
  }
}
