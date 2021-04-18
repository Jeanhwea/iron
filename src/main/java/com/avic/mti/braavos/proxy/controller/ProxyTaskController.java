package com.avic.mti.iron.proxy.controller;

import com.avic.mti.iron.proxy.service.ProxyTaskService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class ProxyTaskController {

  public static final Logger logger = LoggerFactory.getLogger(ProxyTaskController.class);

  private final ProxyTaskService proxyTaskService;

  @Autowired
  public ProxyTaskController(ProxyTaskService proxyTaskService) {
    this.proxyTaskService = proxyTaskService;
  }

  @GetMapping("/**")
  public Object getTasksByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestParam Map<String, Object> params) {
    return this.proxyTaskService.proxyGetForTask(request.getRequestURI(), headers, params);
  }
}
