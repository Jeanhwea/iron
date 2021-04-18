package com.avic.mti.iron.common.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 跨域的一些常量字段
 *
 * @author Jinghui Hu
 * @since 2020-03-31, JDK1.8
 */
@Component
@ConfigurationProperties(prefix = "app.cors")
public class CorsProps {

  private String[] headers =
      new String[] {
        "Access-Control-Request-Headers",
        "Access-Control-Request-Method",
        "Accept",
        "Authorization",
        "Cache-Control",
        "Content-Type",
        "DNT",
        "If-Modified-Since",
        "Keep-Alive",
        "Origin",
        "Referer",
        "User-Agent",
        "X-Mx-ReqToken",
        "X-Requested-With"
      };

  public String[] getHeaders() {
    return this.headers;
  }

  public void setHeaders(String[] headers) {
    this.headers = headers;
  }

  private String[] methods = new String[] {"GET", "OPTIONS", "POST", "PUT", "DELETE"};

  public String[] getMethods() {
    return this.methods;
  }

  public void setMethods(String[] methods) {
    this.methods = methods;
  }
}
