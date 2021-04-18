package com.avic.mti.iron.common.exception;

import java.util.List;
import org.springframework.lang.NonNull;

public class UnknownEnumException extends BadRequestException {
  private static final long serialVersionUID = 2127234954960891399L;

  public UnknownEnumException(
      @NonNull String label, @NonNull String value, @NonNull List<String> expects) {
    super("{0} 枚举值为 {1}, 不在可用的枚举列表中: {2}", label, value, expects);
  }
}
