package com.avic.mti.iron.common.http.filter;

import com.avic.mti.iron.common.helper.IdHelper;
import com.avic.mti.iron.common.http.request.HeaderHelper;
import com.avic.mti.iron.common.props.CustomProps;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * 请求追溯的过滤器
 *
 * @author Jinghui Hu
 * @since 2020-01-08, JDK1.8
 */
@Component
public final class RequestTraceFilter implements Filter {

  public static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

  private final CustomProps customProps;

  @Autowired
  public RequestTraceFilter(CustomProps customProps) {
    this.customProps = customProps;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    // 添加 MDC 参数
    String requestId = IdHelper.getBaseN(32);
    String remoteAddr = "0.0.0.0";

    HttpHeaders httpHeaders =
        Optional.ofNullable(req).map(HeaderHelper::readHttpRequestHeaders).orElse(null);
    if (null != httpHeaders) {
      requestId =
          Optional.ofNullable(httpHeaders.get(HeaderHelper.MTI_REQUEST_ID))
              .flatMap(sl -> sl.stream().findFirst())
              .orElse(requestId);
      remoteAddr =
          Optional.ofNullable(httpHeaders.get(HeaderHelper.MTI_REMOTE_ADDR))
              .flatMap(sl -> sl.stream().findFirst())
              .orElse(remoteAddr);
    }

    MDC.put("requestId", requestId);
    MDC.put("remoteAddr", remoteAddr);

    // 记录请求头
    // if (customProps.isEnableTraceRequest()) {
    //   HttpHeaders headers =
    //       Optional.ofNullable(req).map(HeaderHelper::readHttpRequestHeaders).orElse(null);
    //   logger.info("记录 Http 请求头为: {}", headers);
    // }

    // 记录请求耗时
    long start = System.currentTimeMillis();
    chain.doFilter(request, response);
    long elapsed = System.currentTimeMillis() - start;
    if (null != req && elapsed > customProps.getHeavyRequestThreshold()) {
      logger.warn("请求 {} 过慢, 耗时 {} 毫秒", req.getRequestURI(), elapsed);
    }
  }
}
