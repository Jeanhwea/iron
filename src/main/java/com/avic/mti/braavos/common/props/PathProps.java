package com.avic.mti.iron.common.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统的静态路径配置类
 *
 * @author Jinghui Hu
 * @since 2020-03-06, JDK1.8
 */
@Component
@ConfigurationProperties(prefix = "app.path")
public class PathProps {

  private String staticRoot = "static";

  public String getStaticRoot() {
    return this.staticRoot;
  }

  public void setStaticRoot(String staticRoot) {
    this.staticRoot = staticRoot;
  }
}
