package com.avic.mti.iron.common.http.request;

import com.avic.mti.appbase.filter.entity.BaseUserTokenEntity;
import com.avic.mti.appbase.utils.ThreadLocalUtils;
import com.avic.mti.iron.common.helper.NameCodeHelper;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.helper.UrlHelper;
import java.util.Enumeration;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * 请求头的包装类
 *
 * @author Jinghui Hu
 * @since 2019-12-28, JDK1.8
 */
public class HeaderHelper {

  public static final Logger logger = LoggerFactory.getLogger(HeaderHelper.class);

  public static final String MTI_REMOTE_ADDR = "Mti-Remote-Addr";
  public static final String MTI_REMOTE_HOST = "Mti-Remote-Host";
  public static final String MTI_SERVER_ADDR = "Mti-Server-Addr";
  public static final String MTI_SERVER_PORT = "Mti-Server-Port";
  public static final String MTI_SERVER_HOST = "Mti-Server-Host";
  public static final String MTI_REQUEST_ID = "Mti-Request-Id";
  public static final String MTI_UPDATE_USER_NAME = "Mti-Update-User-Name";
  public static final String MTI_UPDATE_USER_CODE = "Mti-Update-User-Code";

  public static String getUpdateUser(HttpHeaders headers) {
    logger.trace("开始从网关读取用户信息数据");
    BaseUserTokenEntity entity = ThreadLocalUtils.getCurrentBaseUserTokenEntity();
    String name = Optional.ofNullable(entity).map(BaseUserTokenEntity::getName).orElse(null);
    String code = Optional.ofNullable(entity).map(BaseUserTokenEntity::getCode).orElse(null);

    if (StringHelper.isBlank(name)) {
      logger.trace("开始从请求头读取用户信息数据");
      name =
          Optional.ofNullable(headers)
              .map(h -> h.get(MTI_UPDATE_USER_NAME))
              .map(l -> l.get(0))
              .map(UrlHelper::decode)
              .orElse(null);
      code =
          Optional.ofNullable(headers)
              .map(h -> h.get(MTI_UPDATE_USER_CODE))
              .map(l -> l.get(0))
              .map(UrlHelper::decode)
              .orElse(null);
      logger.trace("直接从网关读取用户失败，改从请求头读: {} {}", name, code);
    } else {
      logger.trace("直接从网关读取用户成功: {} {}", name, code);
    }

    logger.trace("开始获取最后更新的用户名");
    String updateUser;
    if (StringHelper.isNonBlank(name) && StringHelper.isNonBlank(code)) {
      updateUser = NameCodeHelper.consNameCode(name, code);
    } else if (StringHelper.isNonBlank(name)) {
      updateUser = name;
    } else {
      updateUser = "anonymous";
      logger.warn("从请求头 HttpHeaders 中读取用户失败，使用匿名 anonymous 身份操作数据");
    }

    logger.trace("从请求头 HttpHeaders 中读取用户结束，获取到的最后更新用户: updateUser = {}", updateUser);
    return updateUser;
  }

  public static HttpHeaders readHttpRequestHeaders(HttpServletRequest request) {
    HttpHeaders headers = new HttpHeaders();
    Enumeration<String> names = request.getHeaderNames();
    while (names.hasMoreElements()) {
      String name = names.nextElement();
      headers.add(name, request.getHeader(name));
    }

    return headers;
  }

  private HeaderHelper() {}
}
