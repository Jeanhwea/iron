package com.avic.mti.iron.common.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统的个性化配置
 *
 * @author Jinghui Hu
 * @since 2020-03-06, JDK1.8
 */
@Component
@ConfigurationProperties(prefix = "app.custom")
public class CustomProps {

  // 是否开启跨域请求支持
  private boolean enableCors = false;

  public boolean isEnableCors() {
    return this.enableCors;
  }

  public void setEnableCors(boolean enableCors) {
    this.enableCors = enableCors;
  }

  // 是否开启允许匿名请求支持
  private boolean enableAnonymous = false;

  public boolean isEnableAnonymous() {
    return this.enableAnonymous;
  }

  public void setEnableAnonymous(boolean enableAnonymous) {
    this.enableAnonymous = enableAnonymous;
  }

  // 是否开启允许网关的过滤器
  private boolean enableGatewayFilter = false;

  public boolean isEnableGatewayFilter() {
    return enableGatewayFilter;
  }

  public void setEnableGatewayFilter(boolean enableGatewayFilter) {
    this.enableGatewayFilter = enableGatewayFilter;
  }

  // 是否开启追踪请求信息
  private boolean enableTraceRequest = true;

  public boolean isEnableTraceRequest() {
    return this.enableTraceRequest;
  }

  public void setEnableTraceRequest(boolean enableTraceRequest) {
    this.enableTraceRequest = enableTraceRequest;
  }

  // 警告负荷的请求毫秒数
  private long heavyRequestThreshold = 1000L;

  public long getHeavyRequestThreshold() {
    return this.heavyRequestThreshold;
  }

  public void setHeavyRequestThreshold(long heavyRequestThreshold) {
    this.heavyRequestThreshold = heavyRequestThreshold;
  }
}
