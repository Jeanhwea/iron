package com.avic.mti.iron.common.exception;

public class JsonReqeustTimeoutException extends InternalServerErrorException {

  private static final long serialVersionUID = 231417022831770100L;

  public JsonReqeustTimeoutException(String requestUrl) {
    super("请求 {0} 时发生超时错误", requestUrl);
  }
}
