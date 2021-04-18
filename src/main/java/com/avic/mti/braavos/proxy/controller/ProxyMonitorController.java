package com.avic.mti.iron.proxy.controller;

import com.avic.mti.iron.proxy.service.ProxyMonitorService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitor")
public class ProxyMonitorController {

  public static final Logger logger = LoggerFactory.getLogger(ProxyMonitorController.class);

  private final ProxyMonitorService proxyMonitorService;

  @Autowired
  public ProxyMonitorController(ProxyMonitorService proxyMonitorService) {
    this.proxyMonitorService = proxyMonitorService;
  }

  @GetMapping("/**")
  public Object getMonitorsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestParam Map<String, Object> params) {
    return this.proxyMonitorService.proxyGetForMonitor(request.getRequestURI(), headers, params);
  }

  @PostMapping("/**")
  public Object postMonitorsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestBody Map<String, Object> params) {
    return this.proxyMonitorService.proxyPostForMonitor(request.getRequestURI(), headers, params);
  }

  @PutMapping("/**")
  public Object putMonitorsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestBody Map<String, Object> params) {
    return this.proxyMonitorService.proxyPutForMonitor(request.getRequestURI(), headers, params);
  }
}
