package com.avic.mti.iron.common.exception;

public class DirtyDataException extends InternalServerErrorException {

  private static final long serialVersionUID = 1795159656535743283L;

  public DirtyDataException(String message) {
    super(message);
  }

  public DirtyDataException(String pattern, Object... args) {
    super(pattern, args);
  }
}
