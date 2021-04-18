package com.avic.mti.iron.common.http.proxy.impl;

import com.avic.mti.appbase.utils.ThreadLocalUtils;
import com.avic.mti.appbase.utils.TokenUtils;
import com.avic.mti.iron.common.exception.InternalServerErrorException;
import com.avic.mti.iron.common.helper.JsonHelper;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.common.http.request.JsonClient;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 请求转发的工具类，用于代理微服务之间的调用转发
 *
 * @author Jinghui Hu
 * @since 2019-11-14, JDK1.8
 */
@Component
@Scope("prototype")
public class EunuchImpl implements Eunuch {

  public static final Logger logger = LoggerFactory.getLogger(EunuchImpl.class);

  /**
   * 构造请求头，添加微服务认证和用户认证
   *
   * @return 请求头的 HttpHeaders 对象
   * @author Jinghui Hu
   * @since 2019-12-25, JDK1.8
   */
  private HttpHeaders consHeaders(HttpHeaders headers) {
    logger.debug("处理之前的请求头 prevHeader 为 {}", headers);
    // 处理旧的请求头
    HttpHeaders newHeader = Optional.ofNullable(headers).orElse(new HttpHeaders());

    // 添加微服务认证和用户认证
    Optional.ofNullable(TokenUtils.getAppToken())
        .ifPresent(
            appAuth -> {
              logger.trace("添加 appAuth: {}", appAuth);
              newHeader.add(TokenUtils.TOKEN_APP, appAuth);
            });
    Optional.ofNullable(ThreadLocalUtils.getCurrentOriginEntity())
        .map(ThreadLocalUtils.OriginEntity::getBaseUserTokenEntityStr)
        .ifPresent(
            userAuth -> {
              logger.trace("添加 userAuth: {}", userAuth);
              newHeader.add(TokenUtils.TOKEN_USER_INFO, userAuth);
            });

    logger.debug("处理过后的请求头 newHeader 为 {}", newHeader);
    return newHeader;
  }

  /**
   * 通过 appname 和 endpoint 来构造网关调用地址
   *
   * @return 调用网关的 URL 服务地址
   * @author Jinghui Hu
   * @since 2020-03-09, JDK1.8
   */
  private String consServiceUrl(@NonNull String appname, @NonNull String endpoint) {
    String gatewayPrefix =
        Optional.ofNullable(TokenUtils.getGateWayUrl())
            .orElseThrow(() -> new InternalServerErrorException("缺失网关配置项: 网关前缀 URL "));
    long organizeCode =
        Optional.of(TokenUtils.getOrganizationCode())
            .orElseThrow(() -> new InternalServerErrorException("缺失网关配置项: 微服务所属的组织号"));
    String serviceUrl =
        MessageFormat.format(
            "{0}/{1}{2,number,#}{3}", gatewayPrefix, appname, organizeCode, endpoint);
    logger.debug("获取的服务调用 URL : {}", serviceUrl);
    return serviceUrl;
  }

  /**
   * 读取返回消息体字符串，解析成 List 或 Object
   *
   * @author Jinghui Hu
   * @since 2020-05-17, JDK1.8
   */
  private Object readObject(@NonNull String appname, @NonNull String endpoint, String bodyString) {
    Object res;
    Optional<List<Map<String, Object>>> requestJson = JsonHelper.parseList(bodyString);
    if (requestJson.isPresent()) {
      res = requestJson.get();
    } else {
      res =
          JsonHelper.parseObject(bodyString)
              .orElseThrow(
                  () ->
                      new InternalServerErrorException(
                          "代理请求 {0} {1} 时出现无法解析结果错误", appname, endpoint));
    }

    return res;
  }

  /**
   * GET 方法传递调用请求 Object 对象
   *
   * @return Object 对象
   * @author Jinghui Hu
   * @since 2019-12-25, JDK1.8
   */
  private Map<String, Object> doGetForMap(
      @NonNull String appname,
      @NonNull String endpoint,
      HttpHeaders headers,
      Map<String, Object> params) {
    logger.trace("代理请求[{}] GET {}: params = {}, headers = {}", appname, endpoint, params, headers);
    Map<String, Object> res =
        JsonClient.getObject(consServiceUrl(appname, endpoint), consHeaders(headers), params);
    logger.trace("代理请求[{}] GET {} 请求结果: {}", appname, endpoint, res);
    return res;
  }

  /**
   * GET 方法传递调用请求 List 对象
   *
   * @return List 对象
   * @author Jinghui Hu
   * @since 2020-03-09, JDK1.8
   */
  private List<Map<String, Object>> doGetForList(
      @NonNull String appname,
      @NonNull String endpoint,
      HttpHeaders headers,
      Map<String, Object> params) {
    logger.trace("代理请求[{}] GET {}: params = {}, headers = {}", appname, endpoint, params, headers);
    List<Map<String, Object>> res =
        JsonClient.getList(consServiceUrl(appname, endpoint), consHeaders(headers), params);
    logger.trace("代理请求[{}] GET {} 请求结果: {}", appname, endpoint, res);
    return res;
  }

  /**
   * GET 方法获取 List 或 Object
   *
   * @return 获取的对象
   * @author Jinghui Hu
   * @since 2020-03-16, JDK1.8
   */
  private Object doGetForObject(
      @NonNull String appname,
      @NonNull String endpoint,
      HttpHeaders headers,
      Map<String, Object> params) {
    logger.trace("代理请求[{}] GET {}: params = {}, headers = {}", appname, endpoint, params, headers);
    String bodyString =
        JsonClient.getBodyString(consServiceUrl(appname, endpoint), consHeaders(headers), params)
            .orElseThrow(() -> new InternalServerErrorException("请求到的 JSON 没有消息体"));
    logger.trace("获取 bodyString 成功");

    Object res = readObject(appname, endpoint, bodyString);
    logger.trace("GET 获取的结果成功: {}", res);
    return res;
  }

  /**
   * POST 方法传递调用请求 Object 对象
   *
   * @return Object 对象
   * @author Jinghui Hu
   * @since 2020-03-10, JDK1.8
   */
  private Map<String, Object> doPostForMap(
      @NonNull String appname,
      @NonNull String endpoint,
      HttpHeaders headers,
      Map<String, Object> params) {
    logger.trace("代理请求[{}] POST {}: params = {}, headers = {}", appname, endpoint, params, headers);
    Map<String, Object> res =
        JsonClient.postObject(consServiceUrl(appname, endpoint), consHeaders(headers), params);
    logger.trace("代理请求[{}] POST {} 请求结果: {}", appname, endpoint, res);
    return res;
  }

  /**
   * POST 方法传递调用请求 Object 对象
   *
   * @return Object 对象
   * @author Jinghui Hu
   * @since 2020-03-10, JDK1.8
   */
  private List<Map<String, Object>> doPostForList(
      @NonNull String appname,
      @NonNull String endpoint,
      HttpHeaders headers,
      Map<String, Object> params) {
    logger.trace("代理请求[{}] POST {}: params = {}, headers = {}", appname, endpoint, params, headers);
    List<Map<String, Object>> res =
        JsonClient.postList(consServiceUrl(appname, endpoint), consHeaders(headers), params);
    logger.trace("代理请求[{}] POST {} 请求结果: {}", appname, endpoint, res);
    return res;
  }

  /**
   * POST 方法获取 List 或 Object
   *
   * @return 获取的对象
   * @author Jinghui Hu
   * @since 2020-03-16, JDK1.8
   */
  private Object doPostForObject(
      @NonNull String appname,
      @NonNull String endpoint,
      HttpHeaders headers,
      Map<String, Object> params) {
    logger.trace("代理请求[{}] POST {}: params = {}, headers = {}", appname, endpoint, params, headers);
    String bodyString =
        JsonClient.postBodyString(consServiceUrl(appname, endpoint), consHeaders(headers), params)
            .orElseThrow(() -> new InternalServerErrorException("请求到的 JSON 没有消息体"));
    logger.trace("POST 获取 bodyString 成功");

    Object res = readObject(appname, endpoint, bodyString);
    logger.trace("POST 获取的结果成功: {}", res);
    return res;
  }

  /**
   * PUT 方法传递调用请求 Object 对象
   *
   * @return Object 对象
   * @author Jinghui Hu
   * @since 2020-03-10, JDK1.8
   */
  private Map<String, Object> doPutForMap(
      @NonNull String appname,
      @NonNull String endpoint,
      HttpHeaders headers,
      Map<String, Object> params) {
    logger.trace("代理请求[{}] PUT {}: params = {}, headers = {}", appname, endpoint, params, headers);
    Map<String, Object> res =
        JsonClient.putObject(consServiceUrl(appname, endpoint), consHeaders(headers), params);
    logger.trace("代理请求[{}] PUT {} 请求结果: {}", appname, endpoint, res);
    return res;
  }

  /**
   * PUT 方法传递调用请求 Object 对象
   *
   * @return Object 对象
   * @author Jinghui Hu
   * @since 2020-03-10, JDK1.8
   */
  private List<Map<String, Object>> doPutForList(
      @NonNull String appname,
      @NonNull String endpoint,
      HttpHeaders headers,
      Map<String, Object> params) {
    logger.trace("代理请求[{}] PUT {}: params = {}, headers = {}", appname, endpoint, params, headers);
    List<Map<String, Object>> res =
        JsonClient.putList(consServiceUrl(appname, endpoint), consHeaders(headers), params);
    logger.trace("代理请求[{}] PUT {} 请求结果: {}", appname, endpoint, res);
    return res;
  }

  /**
   * PUT 方法获取 List 或 Object
   *
   * @return 获取的对象
   * @author Jinghui Hu
   * @since 2020-03-16, JDK1.8
   */
  private Object doPutForObject(
      @NonNull String appname,
      @NonNull String endpoint,
      HttpHeaders headers,
      Map<String, Object> params) {
    logger.trace("代理请求[{}] PUT {}: params = {}, headers = {}", appname, endpoint, params, headers);
    String bodyString =
        JsonClient.putBodyString(consServiceUrl(appname, endpoint), consHeaders(headers), params)
            .orElseThrow(() -> new InternalServerErrorException("请求到的 JSON 没有消息体"));
    logger.trace("PUT 获取 bodyString 成功");

    Object res = readObject(appname, endpoint, bodyString);
    logger.trace("PUT 获取的结果成功: {}", res);
    return res;
  }

  @Override
  public Map<String, Object> getForMap(String appname, String endpoint) {
    return doGetForMap(appname, endpoint, null, null);
  }

  @Override
  public Map<String, Object> getForMap(String appname, String endpoint, HttpHeaders headers) {
    return doGetForMap(appname, endpoint, headers, null);
  }

  @Override
  public Map<String, Object> getForMap(
      String appname, String endpoint, Map<String, Object> params) {
    return doGetForMap(appname, endpoint, null, params);
  }

  @Override
  public Map<String, Object> getForMap(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params) {
    return doGetForMap(appname, endpoint, headers, params);
  }

  @Override
  public List<Map<String, Object>> getForList(String appname, String endpoint) {
    return doGetForList(appname, endpoint, null, null);
  }

  @Override
  public List<Map<String, Object>> getForList(
      String appname, String endpoint, HttpHeaders headers) {
    return doGetForList(appname, endpoint, headers, null);
  }

  @Override
  public List<Map<String, Object>> getForList(
      String appname, String endpoint, Map<String, Object> params) {
    return doGetForList(appname, endpoint, null, params);
  }

  @Override
  public List<Map<String, Object>> getForList(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params) {
    return doGetForList(appname, endpoint, headers, params);
  }

  @Override
  public Object getForObject(String appname, String endpoint) {
    return doGetForObject(appname, endpoint, null, null);
  }

  @Override
  public Object getForObject(String appname, String endpoint, HttpHeaders headers) {
    return doGetForObject(appname, endpoint, headers, null);
  }

  @Override
  public Object getForObject(String appname, String endpoint, Map<String, Object> params) {
    return doGetForObject(appname, endpoint, null, params);
  }

  @Override
  public Object getForObject(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params) {
    return doGetForObject(appname, endpoint, headers, params);
  }

  @Override
  public Map<String, Object> postForMap(String appname, String endpoint) {
    return doPostForMap(appname, endpoint, null, null);
  }

  @Override
  public Map<String, Object> postForMap(String appname, String endpoint, HttpHeaders headers) {
    return doPostForMap(appname, endpoint, headers, null);
  }

  @Override
  public Map<String, Object> postForMap(
      String appname, String endpoint, Map<String, Object> params) {
    return doPostForMap(appname, endpoint, null, params);
  }

  @Override
  public Map<String, Object> postForMap(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params) {
    return doPostForMap(appname, endpoint, headers, params);
  }

  @Override
  public List<Map<String, Object>> postForList(String appname, String endpoint) {
    return doPostForList(appname, endpoint, null, null);
  }

  @Override
  public List<Map<String, Object>> postForList(
      String appname, String endpoint, HttpHeaders headers) {
    return doPostForList(appname, endpoint, headers, null);
  }

  @Override
  public List<Map<String, Object>> postForList(
      String appname, String endpoint, Map<String, Object> params) {
    return doPostForList(appname, endpoint, null, params);
  }

  @Override
  public List<Map<String, Object>> postForList(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params) {
    return doPostForList(appname, endpoint, headers, params);
  }

  @Override
  public Object postForObject(String appname, String endpoint) {
    return doPostForObject(appname, endpoint, null, null);
  }

  @Override
  public Object postForObject(String appname, String endpoint, HttpHeaders headers) {
    return doPostForObject(appname, endpoint, headers, null);
  }

  @Override
  public Object postForObject(String appname, String endpoint, Map<String, Object> params) {
    return doPostForObject(appname, endpoint, null, params);
  }

  @Override
  public Object postForObject(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params) {
    return doPostForObject(appname, endpoint, headers, params);
  }

  @Override
  public Map<String, Object> putForMap(String appname, String endpoint) {
    return doPutForMap(appname, endpoint, null, null);
  }

  @Override
  public Map<String, Object> putForMap(String appname, String endpoint, HttpHeaders headers) {
    return doPutForMap(appname, endpoint, headers, null);
  }

  @Override
  public Map<String, Object> putForMap(
      String appname, String endpoint, Map<String, Object> params) {
    return doPutForMap(appname, endpoint, null, params);
  }

  @Override
  public Map<String, Object> putForMap(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params) {
    return doPutForMap(appname, endpoint, headers, params);
  }

  @Override
  public List<Map<String, Object>> putForList(String appname, String endpoint) {
    return doPutForList(appname, endpoint, null, null);
  }

  @Override
  public List<Map<String, Object>> putForList(
      String appname, String endpoint, HttpHeaders headers) {
    return doPutForList(appname, endpoint, headers, null);
  }

  @Override
  public List<Map<String, Object>> putForList(
      String appname, String endpoint, Map<String, Object> params) {
    return doPutForList(appname, endpoint, null, params);
  }

  @Override
  public List<Map<String, Object>> putForList(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params) {
    return doPutForList(appname, endpoint, headers, params);
  }

  @Override
  public Object putForObject(String appname, String endpoint) {
    return doPutForObject(appname, endpoint, null, null);
  }

  @Override
  public Object putForObject(String appname, String endpoint, HttpHeaders headers) {
    return doPutForObject(appname, endpoint, headers, null);
  }

  @Override
  public Object putForObject(String appname, String endpoint, Map<String, Object> params) {
    return doPutForObject(appname, endpoint, null, params);
  }

  @Override
  public Object putForObject(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params) {
    return doPutForObject(appname, endpoint, headers, params);
  }
}
