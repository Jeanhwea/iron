package com.avic.mti.iron.common.http.request;

import java.util.Arrays;

public enum FieldTypeEnum {
  String(1),
  Long(2),
  Double(3),
  Timestamp(4),
  Enumeration(5),
  Boolean(6),
  Unknown(0);

  private final int value;

  FieldTypeEnum(int val) {
    this.value = val;
  }

  public int getValue() {
    return this.value;
  }

  public static FieldTypeEnum of(int val) {
    return Arrays.stream(FieldTypeEnum.values())
        .filter(e -> e.getValue() == val)
        .findFirst()
        .orElse(FieldTypeEnum.Unknown);
  }
}
