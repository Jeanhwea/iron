package com.avic.mti.iron.common.exception;

import java.text.MessageFormat;

public class DatumUniquenessException extends BadRequestException {

  private static final long serialVersionUID = 1281413763963962545L;

  public DatumUniquenessException(String key, Object value) {
    super(MessageFormat.format("数据唯一性错误: {0} 为 {1} 已经存在", key, value));
  }
}
