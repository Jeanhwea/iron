package com.avic.mti.iron.common.aspect;

import com.avic.mti.iron.common.http.request.HeaderHelper;
import com.avic.mti.iron.common.props.CustomProps;
import java.util.Map;
import java.util.Optional;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GlobalControllerAspect {

  public static final Logger logger = LoggerFactory.getLogger(GlobalControllerAspect.class);

  private final CustomProps customProps;

  @Autowired
  public GlobalControllerAspect(CustomProps customProps) {
    this.customProps = customProps;
  }

  @Pointcut("execution(* com.avic.mti.iron..controller.*Controller.*(..))")
  public void anyControllerMethod() {}

  @SuppressWarnings("unchecked")
  @Before("anyControllerMethod()")
  public void injectUpdateUserToParams(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();

    HttpHeaders headers =
        Optional.ofNullable(args)
            .map(
                a -> {
                  if (a.length > 0) {
                    return a[0];
                  } else {
                    logger.error("发现没有参数的控制器: {}", joinPoint.getSignature());
                  }

                  return null;
                })
            .map(
                arg0 -> {
                  if (arg0 instanceof HttpHeaders) {
                    return (HttpHeaders) arg0;
                  } else {
                    logger.error("发现没有添加 HttpHeaders 参数的控制器: {}", joinPoint.getSignature());
                  }

                  return null;
                })
            .orElse(null);

    Map<String, Object> params =
        (Map<String, Object>)
            Optional.ofNullable(args)
                .map(a -> a.length > 1 ? args[args.length - 1] : null)
                .filter(argn -> argn instanceof Map)
                .orElse(null);

    String updateUser = HeaderHelper.getUpdateUser(headers);
    Optional.ofNullable(params)
        .ifPresent(
            p -> {
              p.put("updateUser", updateUser);
              logger.trace("添加操作者到参数中: {}", p);
            });

    if (customProps.isEnableTraceRequest()) {
      logger.info("记录 [{}] 发送的 HTTP 请求头为: {}", updateUser, headers);
      logger.info("记录 [{}] 发送的 HTTP 请求参数为: {}", updateUser, params);
    }
  }
}
