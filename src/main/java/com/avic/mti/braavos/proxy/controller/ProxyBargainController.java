package com.avic.mti.iron.proxy.controller;

import com.avic.mti.iron.proxy.service.ProxyBargainService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bargains")
public class ProxyBargainController {

  public static final Logger logger = LoggerFactory.getLogger(ProxyBargainController.class);

  private final ProxyBargainService proxyBargainService;

  @Autowired
  public ProxyBargainController(ProxyBargainService proxyBargainService) {
    this.proxyBargainService = proxyBargainService;
  }

  @GetMapping("/**")
  public Object getBargainsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestParam Map<String, Object> params) {
    return this.proxyBargainService.proxyGetForBargain(request.getRequestURI(), headers, params);
  }

  @PutMapping("/**")
  public Object putBargainsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestBody Map<String, Object> params) {
    return this.proxyBargainService.proxyPutForBargain(request.getRequestURI(), headers, params);
  }
}
