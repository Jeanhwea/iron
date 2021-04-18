package com.avic.mti.iron.proxy.controller;

import com.avic.mti.iron.proxy.service.ProxyReportService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ProxyReportController {

  public static final Logger logger = LoggerFactory.getLogger(ProxyReportController.class);

  private final ProxyReportService proxyReportService;

  @Autowired
  public ProxyReportController(ProxyReportService proxyReportService) {
    this.proxyReportService = proxyReportService;
  }

  @GetMapping("/**")
  public Object getReportsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestParam Map<String, Object> params) {
    return this.proxyReportService.proxyGetForReport(request.getRequestURI(), headers, params);
  }

  @PostMapping("/**")
  public Object postReportsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestBody Map<String, Object> params) {
    return this.proxyReportService.proxyPostForReport(request.getRequestURI(), headers, params);
  }
}
