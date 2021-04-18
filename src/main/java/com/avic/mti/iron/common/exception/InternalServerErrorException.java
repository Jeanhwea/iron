package com.avic.mti.iron.common.exception;

import java.text.MessageFormat;
import org.springframework.lang.NonNull;

public class InternalServerErrorException extends RuntimeException {

  private static final long serialVersionUID = 1048144563024257801L;

  private final String message;

  public InternalServerErrorException(String message) {
    this.message = message;
  }

  public InternalServerErrorException(@NonNull String pattern, Object... args) {
    this.message = MessageFormat.format(pattern, args);
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
