package com.avic.mti.iron.common.exception;

import java.text.MessageFormat;

public class WrongParameterFormatException extends BadRequestException {

  private static final long serialVersionUID = 1621642363541023956L;

  public WrongParameterFormatException(String label, String expected, String value) {
    super(MessageFormat.format("参数 {0} 格式错误, 希望满足 {1} 格式, 却传入了错误值 {2}", label, expected, value));
  }

  public WrongParameterFormatException(String expected, String value) {
    super(MessageFormat.format("错误格式是参数: 希望满足 {0} 格式, 却传入了错误值 {1}", expected, value));
  }
}
