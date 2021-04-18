package com.avic.mti.iron.common.props;

import java.util.HashMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 三方集成配置类
 *
 * @author Jinghui Hu
 * @since 2020-04-30, JDK1.8
 */
@Component
@ConfigurationProperties(prefix = "app.third")
public class ThirdProps {

  private boolean enableYongYou = false;

  public boolean getEnableYongYou() {
    return this.enableYongYou;
  }

  public void setEnableYongYou(boolean enableYongYou) {
    this.enableYongYou = enableYongYou;
  }

  private int timeout = 5000000;

  public int getTimeout() {
    return timeout;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  private HashMap<String, Object> yongyou;

  public HashMap<String, Object> getYongyou() {
    return yongyou;
  }

  public void setYongyou(HashMap<String, Object> yongyou) {
    this.yongyou = yongyou;
  }
}
