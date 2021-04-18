package com.avic.mti.iron.common.http.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

/**
 * HTTP 错误返回消息的结构实体类
 *
 * @author Jinghui Hu
 * @since 2019-05-29, JDK1.8
 */
public class HttpStatusEntity {
  private final HttpStatus statusCode;
  private final String message;
  private final StackTraceElement[] stackTrace;
  private final String path;

  public HttpStatusEntity(
      HttpStatus statusCode, String path, String message, StackTraceElement[] stackTrace) {
    this.statusCode = statusCode;
    this.message = message;
    this.stackTrace = stackTrace;
    this.path = path;
  }

  @JsonProperty("status")
  public int status() {
    return statusCode.value();
  }

  @JsonProperty("error")
  public String error() {
    return this.statusCode != HttpStatus.OK ? statusCode.getReasonPhrase() : "";
  }

  @JsonProperty("message")
  public String message() {
    return this.message;
  }

  @JsonProperty("trace")
  public List<String> trace() {
    return Arrays.stream(stackTrace).map(StackTraceElement::toString).collect(Collectors.toList());
  }

  @JsonProperty("timestamp")
  public Date timestamp() {
    return new Date();
  }

  @JsonProperty("time")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  public Date time() {
    return new Date();
  }

  @JsonProperty("path")
  public String path() {
    return this.path;
  }

  @Override
  public String toString() {
    return String.format(
        "HttpStatusEntity{statusCode=%s, message=%s, path=%s}", statusCode, message, path);
  }
}
