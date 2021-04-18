package com.avic.mti.iron.common.exception;

import java.text.MessageFormat;
import java.util.List;

public class WrongParameterTypeException extends BadRequestException {

  private static final long serialVersionUID = 13660159929109561L;

  public WrongParameterTypeException(List<String> badFields) {
    super(MessageFormat.format("下列参数的类型错误: {0}", badFields));
  }
}
