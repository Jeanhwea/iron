package com.avic.mti.iron.proxy.controller;

import com.avic.mti.iron.proxy.service.ProxyUnitService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/units")
public class ProxyUnitController {

  public static final Logger logger = LoggerFactory.getLogger(ProxyUnitController.class);

  private final ProxyUnitService proxyUnitService;

  @Autowired
  public ProxyUnitController(ProxyUnitService proxyUnitService) {
    this.proxyUnitService = proxyUnitService;
  }

  @GetMapping("/**")
  public Object getUnitsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestParam Map<String, Object> params) {
    return this.proxyUnitService.proxyGetForUnit(request.getRequestURI(), headers, params);
  }

  @PostMapping("/**")
  public Object postUnitsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestBody Map<String, Object> params) {
    return this.proxyUnitService.proxyPostForUnit(request.getRequestURI(), headers, params);
  }

  @PutMapping("/**")
  public Object putUnitsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestBody Map<String, Object> params) {
    return this.proxyUnitService.proxyPutForUnit(request.getRequestURI(), headers, params);
  }
}
