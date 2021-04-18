package com.avic.mti.iron.proxy.controller;

import com.avic.mti.iron.proxy.service.ProxyPassverfService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passverf")
public class ProxyPassverfController {

  public static final Logger logger = LoggerFactory.getLogger(ProxyPassverfController.class);

  private final ProxyPassverfService proxyPassverfService;

  @Autowired
  public ProxyPassverfController(ProxyPassverfService proxyPassverfService) {
    this.proxyPassverfService = proxyPassverfService;
  }

  @GetMapping("/**")
  public Object getPassverfsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestParam Map<String, Object> params) {
    return this.proxyPassverfService.proxyGetForPassverf(request.getRequestURI(), headers, params);
  }

  @PostMapping("/**")
  public Object postPassverfsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestBody Map<String, Object> params) {
    return this.proxyPassverfService.proxyPostForPassverf(request.getRequestURI(), headers, params);
  }

  @PutMapping("/**")
  public Object putPassverfsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestBody Map<String, Object> params) {
    return this.proxyPassverfService.proxyPutForPassverf(request.getRequestURI(), headers, params);
  }
}
