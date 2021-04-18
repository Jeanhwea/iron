package com.avic.mti.iron.common.exception;

public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = 453100806883256809L;

  private final String message;

  public NotFoundException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
