package com.avic.mti.iron.common.exception;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

public class MissingParameterException extends BadRequestException {

  private static final long serialVersionUID = 1959849012405744424L;

  public MissingParameterException(List<String> missingFields) {
    super(MessageFormat.format("参数缺失错误: 缺少下列参数 {0}", Collections.singletonList(missingFields)));
  }
}
