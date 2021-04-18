package com.avic.mti.iron.common.exception;

public class JsonParseException extends InternalServerErrorException {

  private static final long serialVersionUID = 340867195719872608L;

  public JsonParseException(String requestUrl) {
    super("请求 {0} 时解析返回的 Json 字符串出现错误", requestUrl);
  }
}
