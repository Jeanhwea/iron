package com.avic.mti.iron.common.http.request;

import java.util.Arrays;

public enum ParamTypeEnum {
  Integer(1),
  String(2),
  Long(3),
  Double(4),
  Timestamp(5),
  Datetime(6),
  Boolean(7),
  List(8),
  Object(9),
  Unknown(0);

  private final int value;

  ParamTypeEnum(int val) {
    this.value = val;
  }

  private int getValue() {
    return this.value;
  }

  public static ParamTypeEnum of(int val) {
    return Arrays.stream(ParamTypeEnum.values())
        .filter(e -> e.getValue() == val)
        .findFirst()
        .orElse(ParamTypeEnum.Unknown);
  }
}
