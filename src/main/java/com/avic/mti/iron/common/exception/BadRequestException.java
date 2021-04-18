package com.avic.mti.iron.common.exception;

import java.text.MessageFormat;
import org.springframework.lang.NonNull;

public class BadRequestException extends RuntimeException {

  private static final long serialVersionUID = 1900489203861605645L;

  private final String message;

  public BadRequestException(String message) {
    this.message = message;
  }

  public BadRequestException(@NonNull String pattern, Object... args) {
    this.message = MessageFormat.format(pattern, args);
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
