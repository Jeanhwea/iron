package com.avic.mti.iron.common.exception;

public class MissingConfigKeyException extends InternalServerErrorException {

  private static final long serialVersionUID = 1372081979512394194L;

  public MissingConfigKeyException(String configKey) {
    super("配置文件中缺少: {0}", configKey);
  }
}
