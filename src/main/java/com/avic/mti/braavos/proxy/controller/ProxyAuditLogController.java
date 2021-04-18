package com.avic.mti.iron.proxy.controller;

import com.avic.mti.iron.proxy.service.ProxyAuditLogService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audits/logs")
public class ProxyAuditLogController {

  public static final Logger logger = LoggerFactory.getLogger(ProxyAuditLogController.class);

  private final ProxyAuditLogService proxyAuditLogService;

  @Autowired
  public ProxyAuditLogController(ProxyAuditLogService proxyAuditLogService) {
    this.proxyAuditLogService = proxyAuditLogService;
  }

  @GetMapping("/**")
  public Object getAuditLogsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestParam Map<String, Object> params) {
    return this.proxyAuditLogService.proxyGetForAuditLog(request.getRequestURI(), headers, params);
  }

  @PostMapping("/**")
  public Object postAuditLogsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestBody Map<String, Object> params) {
    return this.proxyAuditLogService.proxyPostForAuditLog(request.getRequestURI(), headers, params);
  }

  @PutMapping("/**")
  public Object putAuditLogsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestBody Map<String, Object> params) {
    return this.proxyAuditLogService.proxyPutForAuditLog(request.getRequestURI(), headers, params);
  }
}
