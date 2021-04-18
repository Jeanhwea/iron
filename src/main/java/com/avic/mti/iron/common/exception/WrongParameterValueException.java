package com.avic.mti.iron.common.exception;

import java.text.MessageFormat;

public class WrongParameterValueException extends BadRequestException {

  private static final long serialVersionUID = 102902907093683313L;

  public WrongParameterValueException(String label, String value) {
    super(MessageFormat.format("参数 {0} 的值为 {1}, 格式错误", label, value));
  }
}
