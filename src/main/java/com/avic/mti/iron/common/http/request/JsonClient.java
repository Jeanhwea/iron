package com.avic.mti.iron.common.http.request;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.InternalServerErrorException;
import com.avic.mti.iron.common.exception.JsonParseException;
import com.avic.mti.iron.common.exception.JsonReqeustTimeoutException;
import com.avic.mti.iron.common.helper.JsonHelper;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.client.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 请对 Json 数据格式的客户端类
 *
 * @author Jinghui Hu
 * @since 2019-12-25, JDK1.8
 */
public class JsonClient {

  private static final int TIMEOUT_IN_MILLISECONDS = 5000000; // 默认的超时毫秒数

  public static final Logger logger = LoggerFactory.getLogger(JsonClient.class);

  private static HttpComponentsClientHttpRequestFactory factory;

  /**
   * 发送 GET 请求，并获取 Json 对象
   *
   * @return Map 类型的对象
   * @author Jinghui Hu
   * @since 2019-12-25, JDK1.8
   */
  public static Map<String, Object> getObject(
      @NonNull String uriTemplate, HttpHeaders headers, Map<String, Object> params)
      throws JsonParseException {
    String respText =
        getBodyString(uriTemplate, headers, params)
            .orElseThrow(
                () -> new InternalServerErrorException("请求 {0} 获取到的回复中没有消息体", uriTemplate));

    Map<String, Object> result =
        JsonHelper.parseObject(respText).orElseThrow(() -> new JsonParseException(uriTemplate));
    logger.trace("请求 Json 获取的解析后结果为: {}", result);

    return result;
  }

  /**
   * 发送 GET 请求，并获取 Json 对象
   *
   * @return List 数组，其中每个数组元素都是一个 Map 对象
   * @author Jinghui Hu
   * @since 2019-12-25, JDK1.8
   */
  public static List<Map<String, Object>> getList(
      @NonNull String uriTemplate, HttpHeaders headers, Map<String, Object> params)
      throws JsonParseException {
    String respText =
        getBodyString(uriTemplate, headers, params, null)
            .orElseThrow(
                () -> new InternalServerErrorException("请求 {0} 获取到的回复中没有消息体", uriTemplate));

    List<Map<String, Object>> result =
        JsonHelper.parseList(respText).orElseThrow(() -> new JsonParseException(uriTemplate));
    logger.trace("请求 Json 获取的解析后结果为: {}", result);

    return result;
  }

  /**
   * 发送 POST 请求，并获取 Json 对象
   *
   * @return Map 类型的对象
   * @author Jinghui Hu
   * @since 2019-12-25, JDK1.8
   */
  public static Map<String, Object> postObject(
      @NonNull String uriTemplate, HttpHeaders headers, Map<String, Object> params)
      throws JsonParseException {
    String respText =
        postBodyString(uriTemplate, headers, params)
            .orElseThrow(
                () -> new InternalServerErrorException("请求 {0} 获取到的回复中没有消息体", uriTemplate));

    Map<String, Object> result =
        JsonHelper.parseObject(respText).orElseThrow(() -> new JsonParseException(uriTemplate));
    logger.trace("请求 Json 获取的解析后结果为: {}", result);

    return result;
  }

  /**
   * 发送 POST 请求，并获取 Json 对象
   *
   * @return List 数组，其中每个数组元素都是一个 Map 对象
   * @author Jinghui Hu
   * @since 2019-12-25, JDK1.8
   */
  public static List<Map<String, Object>> postList(
      @NonNull String uriTemplate, HttpHeaders headers, Map<String, Object> params)
      throws JsonParseException {
    String respText =
        postBodyString(uriTemplate, headers, params)
            .orElseThrow(
                () -> new InternalServerErrorException("请求 {0} 获取到的回复中没有消息体", uriTemplate));

    List<Map<String, Object>> result =
        JsonHelper.parseList(respText).orElseThrow(() -> new JsonParseException(uriTemplate));
    logger.trace("请求 Json 获取的解析后结果为: {}", result);

    return result;
  }

  /**
   * 发送 PUT 请求，并获取 Json 对象
   *
   * @return Map 类型的对象
   * @author Jinghui Hu
   * @since 2020-05-14, JDK1.8
   */
  public static Map<String, Object> putObject(
      @NonNull String uriTemplate, HttpHeaders headers, Map<String, Object> params)
      throws JsonParseException {
    String respText =
        putBodyString(uriTemplate, headers, params)
            .orElseThrow(
                () -> new InternalServerErrorException("请求 {0} 获取到的回复中没有消息体", uriTemplate));

    Map<String, Object> result =
        JsonHelper.parseObject(respText).orElseThrow(() -> new JsonParseException(uriTemplate));
    logger.trace("请求 Json 获取的解析后结果为: {}", result);

    return result;
  }

  /**
   * 发送 PUT 请求，并获取 Json 对象
   *
   * @return List 数组，其中每个数组元素都是一个 Map 对象
   * @author Jinghui Hu
   * @since 2020-05-14, JDK1.8
   */
  public static List<Map<String, Object>> putList(
      @NonNull String uriTemplate, HttpHeaders headers, Map<String, Object> params)
      throws JsonParseException {
    String respText =
        putBodyString(uriTemplate, headers, params)
            .orElseThrow(
                () -> new InternalServerErrorException("请求 {0} 获取到的回复中没有消息体", uriTemplate));

    List<Map<String, Object>> result =
        JsonHelper.parseList(respText).orElseThrow(() -> new JsonParseException(uriTemplate));
    logger.trace("请求 Json 获取的解析后结果为: {}", result);

    return result;
  }

  public static Optional<String> getBodyString(
      @NonNull String uriTemplate, HttpHeaders headers, Map<String, Object> params) {
    return doGetBodyString(uriTemplate, headers, params, null);
  }

  public static Optional<String> getBodyString(
      @NonNull String uriTemplate,
      HttpHeaders headers,
      Map<String, Object> params,
      ClientHttpRequestFactory clientHttpRequestFactory) {
    return doGetBodyString(uriTemplate, headers, params, clientHttpRequestFactory);
  }

  public static Optional<String> postBodyString(
      @NonNull String uriTemplate, HttpHeaders headers, Map<String, Object> params) {
    return doPostBodyString(uriTemplate, headers, params, null);
  }

  public static Optional<String> postBodyString(
      @NonNull String uriTemplate,
      HttpHeaders headers,
      Map<String, Object> params,
      ClientHttpRequestFactory clientHttpRequestFactory) {
    return doPostBodyString(uriTemplate, headers, params, clientHttpRequestFactory);
  }

  public static Optional<String> putBodyString(
      @NonNull String uriTemplate, HttpHeaders headers, Map<String, Object> params) {
    return doPutBodyString(uriTemplate, headers, params, null);
  }

  public static Optional<String> putBodyString(
      @NonNull String uriTemplate,
      HttpHeaders headers,
      Map<String, Object> params,
      ClientHttpRequestFactory clientHttpRequestFactory) {
    return doPutBodyString(uriTemplate, headers, params, clientHttpRequestFactory);
  }

  /**
   * 构建 GET 的请求的 URI, 主要是需要拼接 Query String
   *
   * @return 用于发送 GET 请求的 URI
   * @author Jinghui Hu
   * @since 2019-12-25, JDK1.8
   */
  private static URI consUriForGet(@NonNull String uriTemplate, Map<String, Object> params) {
    if (null == params || params.isEmpty()) {
      return UriComponentsBuilder.fromUriString(uriTemplate).build(0);
    }

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uriTemplate);
    params.forEach((k, v) -> builder.queryParam(k, "{" + k + "}"));
    return builder.encode().buildAndExpand(params).toUri();
  }

  /**
   * 添加 Json 的请求头参数，主要是添加 Content-Type 和 Accept 请求头
   *
   * @author Jinghui Hu
   * @since 2019-12-25, JDK1.8
   */
  private static HttpHeaders consHttpHeaders(HttpHeaders headers) {
    HttpHeaders newHeaders = Optional.ofNullable(headers).orElseGet(HttpHeaders::new);

    // 添加 Content-Type: application/json
    newHeaders.setContentType(MediaType.APPLICATION_JSON);

    // 添加 Accept: application/json
    List<MediaType> accepts = new LinkedList<>();
    accepts.add(MediaType.APPLICATION_JSON);
    newHeaders.setAccept(accepts);

    return newHeaders;
  }

  /**
   * 配置 HTTP 请求的超时参数
   *
   * @author Jinghui Hu
   * @since 2019-12-25, JDK1.8
   */
  private static ClientHttpRequestFactory getClientHttpRequestFactory() {
    return Optional.ofNullable(factory)
        .orElseGet(
            () -> {
              factory = new HttpComponentsClientHttpRequestFactory();
              factory.setConnectionRequestTimeout(TIMEOUT_IN_MILLISECONDS);
              factory.setConnectTimeout(TIMEOUT_IN_MILLISECONDS);
              factory.setReadTimeout(TIMEOUT_IN_MILLISECONDS);
              return factory;
            });
  }

  /**
   * 提取请求失败的 message 字段
   *
   * @return 消息字符串
   * @author Jinghui Hu
   * @since 2020-04-22, JDK1.8
   */
  private static String extractMessage(@NonNull String bodyText, @NonNull String uriTemplate) {
    logger.debug(bodyText);
    return JsonHelper.parseObject(bodyText)
        .map(obj -> obj.get("message"))
        .map(ParamMapper::toString)
        .orElse("请求 " + uriTemplate + " 时发生错误，返回体中没有 message 字段");
  }

  /**
   * 发送 GET 请求，获取返回消息体中的字符串
   *
   * @return 返回信息体中的字符串
   * @author Jinghui Hu
   * @since 2020-03-16, JDK1.8
   */
  public static Optional<String> doGetBodyString(
      @NonNull String uriTemplate,
      HttpHeaders headers,
      Map<String, Object> params,
      ClientHttpRequestFactory clientHttpRequestFactory)
      throws JsonReqeustTimeoutException {
    ClientHttpRequestFactory factory =
        Optional.ofNullable(clientHttpRequestFactory).orElse(getClientHttpRequestFactory());

    RequestEntity<Void> requestEntity =
        RequestEntity.get(consUriForGet(uriTemplate, params))
            .headers(consHttpHeaders(headers))
            .build();

    RestTemplate restTemplate = new RestTemplate(factory);
    try {
      ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
      return Optional.ofNullable(response.getBody());
    } catch (ResourceAccessException e) {
      throw new JsonReqeustTimeoutException(uriTemplate);
    } catch (HttpClientErrorException e) {
      HttpStatus statusCode = e.getStatusCode();
      String responseMessage = extractMessage(e.getResponseBodyAsString(), uriTemplate);
      if (statusCode.is4xxClientError()) {
        throw new BadRequestException(responseMessage);
      } else {
        throw new InternalServerErrorException(responseMessage);
      }
    } catch (RestClientException e) {
      return Optional.empty();
    }
  }

  /**
   * 发送 POST 请求，并获取 Json 返回的消息体字符串
   *
   * @return 消息体字符串
   * @author Jinghui Hu
   * @since 2020-03-16, JDK1.8
   */
  private static Optional<String> doPostBodyString(
      @NonNull String uriTemplate,
      HttpHeaders headers,
      Map<String, Object> params,
      ClientHttpRequestFactory clientHttpRequestFactory)
      throws JsonReqeustTimeoutException {
    ClientHttpRequestFactory factory =
        Optional.ofNullable(clientHttpRequestFactory).orElse(getClientHttpRequestFactory());

    HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, consHttpHeaders(headers));

    RestTemplate restTemplate = new RestTemplate(factory);
    try {
      ResponseEntity<String> response =
          restTemplate.exchange(uriTemplate, HttpMethod.POST, httpEntity, String.class);
      return Optional.ofNullable(response.getBody());
    } catch (ResourceAccessException e) {
      throw new JsonReqeustTimeoutException(uriTemplate);
    } catch (HttpClientErrorException e) {
      HttpStatus statusCode = e.getStatusCode();
      String responseMessage = extractMessage(e.getResponseBodyAsString(), uriTemplate);
      if (statusCode.is4xxClientError()) {
        throw new BadRequestException(responseMessage);
      } else {
        throw new InternalServerErrorException(responseMessage);
      }
    } catch (RestClientException e) {
      return Optional.empty();
    }
  }

  /**
   * 发送 PUT 请求，并获取 Json 返回的消息体字符串
   *
   * @return 消息体字符串
   * @author Jinghui Hu
   * @since 2020-05-14, JDK1.8
   */
  private static Optional<String> doPutBodyString(
      @NonNull String uriTemplate,
      HttpHeaders headers,
      Map<String, Object> params,
      ClientHttpRequestFactory clientHttpRequestFactory)
      throws JsonReqeustTimeoutException {
    ClientHttpRequestFactory factory =
        Optional.ofNullable(clientHttpRequestFactory).orElse(getClientHttpRequestFactory());

    HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, consHttpHeaders(headers));

    RestTemplate restTemplate = new RestTemplate(factory);
    try {
      ResponseEntity<String> response =
          restTemplate.exchange(uriTemplate, HttpMethod.PUT, httpEntity, String.class);
      return Optional.ofNullable(response.getBody());
    } catch (ResourceAccessException e) {
      throw new JsonReqeustTimeoutException(uriTemplate);
    } catch (HttpClientErrorException e) {
      HttpStatus statusCode = e.getStatusCode();
      String responseMessage = extractMessage(e.getResponseBodyAsString(), uriTemplate);
      if (statusCode.is4xxClientError()) {
        throw new BadRequestException(responseMessage);
      } else {
        throw new InternalServerErrorException(responseMessage);
      }
    } catch (RestClientException e) {
      return Optional.empty();
    }
  }
}
