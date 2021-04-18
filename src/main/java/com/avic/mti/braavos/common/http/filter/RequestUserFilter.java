package com.avic.mti.iron.common.http.filter;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.HeaderHelper;
import com.avic.mti.iron.common.props.CustomProps;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户信息的过滤器
 *
 * @author Jinghui Hu
 * @since 2019-12-28, JDK1.8
 */
@Component
public class RequestUserFilter implements Filter {

  public static final Logger logger = LoggerFactory.getLogger(RequestUserFilter.class);

  private final CustomProps customProps;

  @Autowired
  public RequestUserFilter(CustomProps customProps) {
    this.customProps = customProps;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    logger.trace("用户信息过滤器开始执行");
    String updateUser =
        Optional.ofNullable(request)
            .map(r -> (HttpServletRequest) r)
            .map(HeaderHelper::readHttpRequestHeaders)
            .map(HeaderHelper::getUpdateUser)
            .orElse(null);

    if (StringHelper.isBlank(updateUser)) {
      if (this.customProps.isEnableAnonymous() && null != request) {
        logger.trace("用户信息过滤器 匿名请求数据: {} {}", request.getRemoteHost(), request.getRemoteAddr());
      } else {
        throw new BadRequestException(
            "请求头中缺少: {0}, {1}",
            HeaderHelper.MTI_UPDATE_USER_NAME, HeaderHelper.MTI_UPDATE_USER_CODE);
      }

    } else {
      logger.trace("用户信息过滤器 {} 请求数据", updateUser);
    }

    logger.trace("用户信息过滤器结束: updateUser = {}", updateUser);
    chain.doFilter(request, response);
  }
}
