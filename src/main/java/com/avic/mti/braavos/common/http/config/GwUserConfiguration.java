package com.avic.mti.iron.common.http.config;

import com.avic.mti.appbase.filter.GwUserTokenEntityFilter;
import com.avic.mti.iron.common.constant.FilterOrderConst;
import com.avic.mti.iron.common.props.CustomProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关用户过滤器
 *
 * @author Jinghui Hu
 * @since 2020-03-04, JDK1.8
 */
@Configuration
public class GwUserConfiguration {

  public static final Logger logger = LoggerFactory.getLogger(GwUserConfiguration.class);

  private final CustomProps customProps;

  @Autowired
  public GwUserConfiguration(CustomProps customProps) {
    this.customProps = customProps;
  }

  @Bean
  public FilterRegistrationBean<GwUserTokenEntityFilter> filterRegistrationBean() {
    FilterRegistrationBean<GwUserTokenEntityFilter> bean = new FilterRegistrationBean<>();
    logger.info("挂载网关过滤器: {}", customProps.isEnableGatewayFilter());
    bean.setFilter(GwUserTokenEntityFilter.instance());
    bean.setName("gwUserTokenEntityFilter");
    bean.setOrder(FilterOrderConst.SYSTEM_FILTER_ORDER);
    if (customProps.isEnableGatewayFilter()) {
      bean.addUrlPatterns("/*");
    } else {
      bean.addUrlPatterns("/");
    }

    return bean;
  }
}
