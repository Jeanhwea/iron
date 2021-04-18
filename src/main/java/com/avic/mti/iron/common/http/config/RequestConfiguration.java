package com.avic.mti.iron.common.http.config;

import com.avic.mti.iron.common.constant.FilterOrderConst;
import com.avic.mti.iron.common.http.filter.RequestTraceFilter;
import com.avic.mti.iron.common.http.filter.RequestUserFilter;
import com.avic.mti.iron.common.props.CustomProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户信息过滤器的启动配置类
 *
 * @author Jinghui Hu
 * @since 2019-12-28, JDK1.8
 */
@Configuration
public class RequestConfiguration {

  public static final Logger logger = LoggerFactory.getLogger(RequestConfiguration.class);

  private final CustomProps customProps;
  private final RequestUserFilter requestUserFilter;

  @Autowired
  public RequestConfiguration(CustomProps customProps, RequestUserFilter requestUserFilter) {
    this.customProps = customProps;
    this.requestUserFilter = requestUserFilter;
  }

  @Bean
  public FilterRegistrationBean<RequestUserFilter> loadRequestUserFilter() {
    logger.info("允许匿名请求: {}", this.customProps.isEnableAnonymous());
    FilterRegistrationBean<RequestUserFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(this.requestUserFilter);
    bean.setName("requestUserFilter");
    bean.addUrlPatterns("/*");
    bean.setOrder(FilterOrderConst.APPLICATION_FILTER_ORDER);
    return bean;
  }

  @Bean
  public FilterRegistrationBean<RequestTraceFilter> loadRequestTraceFilter() {
    FilterRegistrationBean<RequestTraceFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(new RequestTraceFilter(this.customProps));
    bean.setName("requestTraceFilter");
    bean.addUrlPatterns("/*");
    bean.setOrder(FilterOrderConst.LOWEST_FILTER_ORDER);
    return bean;
  }
}
