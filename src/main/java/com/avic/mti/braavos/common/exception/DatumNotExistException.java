package com.avic.mti.iron.common.exception;

import java.text.MessageFormat;

public class DatumNotExistException extends NotFoundException {

  private static final long serialVersionUID = 231567898828239468L;

  public DatumNotExistException(String name, long id) {
    super(MessageFormat.format("无法使用 id = {0,number,#} 找到 {1} 数据项", id, name));
  }

  public DatumNotExistException(String name, String key, String value) {
    super(MessageFormat.format("无法使用 {0} = {1} 找到 {2} 数据项", key, value, name));
  }
}
