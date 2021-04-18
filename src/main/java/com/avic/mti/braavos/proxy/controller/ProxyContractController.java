package com.avic.mti.iron.proxy.controller;

import com.avic.mti.iron.proxy.service.ProxyContractService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contracts")
public class ProxyContractController {

  public static final Logger logger = LoggerFactory.getLogger(ProxyContractController.class);

  private final ProxyContractService proxyContractService;

  @Autowired
  public ProxyContractController(ProxyContractService proxyContractService) {
    this.proxyContractService = proxyContractService;
  }

  @GetMapping("/**")
  public Object getContractsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestParam Map<String, Object> params) {
    return this.proxyContractService.proxyGetForContract(request.getRequestURI(), headers, params);
  }

  @PutMapping("/**")
  public Object putContractsByProxy(
      @RequestHeader HttpHeaders headers,
      HttpServletRequest request,
      @RequestBody Map<String, Object> params) {
    return this.proxyContractService.proxyPutForContract(request.getRequestURI(), headers, params);
  }
}
