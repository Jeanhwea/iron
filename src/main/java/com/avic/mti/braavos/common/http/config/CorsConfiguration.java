package com.avic.mti.iron.common.http.config;

import com.avic.mti.iron.common.props.CorsProps;
import com.avic.mti.iron.common.props.CustomProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 允许跨域的配置类
 *
 * @author Jinghui Hu
 * @since 2019-07-28, JDK1.8
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

  public static final Logger logger = LoggerFactory.getLogger(CorsConfiguration.class);

  private final CustomProps customProps;
  private final CorsProps corsProps;

  @Autowired
  public CorsConfiguration(CustomProps customProps, CorsProps corsProps) {
    this.customProps = customProps;
    this.corsProps = corsProps;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    logger.info("允许跨域请求: {}", this.customProps.isEnableCors());
    if (this.customProps.isEnableCors()) {
      registry
          .addMapping("/**")
          .allowedOrigins("*")
          .allowedMethods(corsProps.getMethods())
          .allowedHeaders(corsProps.getHeaders())
          .exposedHeaders(corsProps.getHeaders())
          .allowCredentials(true)
          .maxAge(3600);
    }
  }
}
