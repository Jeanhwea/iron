package com.avic.mti.iron.proxy.controller;

import com.avic.mti.iron.proxy.service.ProxyTestService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tests")
public class ProxyTestController {

  public static final Logger logger = LoggerFactory.getLogger(ProxyTestController.class);

  private final ProxyTestService proxyTestService;

  @Autowired
  public ProxyTestController(ProxyTestService proxyTestService) {
    this.proxyTestService = proxyTestService;
  }

  @GetMapping("/**")
  public Object getTestsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestParam Map<String, Object> params) {
    return this.proxyTestService.proxyForTest(request.getRequestURI(), headers, params);
  }
}
