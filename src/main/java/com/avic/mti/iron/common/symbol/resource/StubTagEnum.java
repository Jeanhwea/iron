package com.avic.mti.iron.common.symbol.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StubTagEnum {
  Enum1_SG("1-手工"),
  Enum2_XT("2-系统"),
  Unknown("未知");

  private final String symbol;

  StubTagEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static StubTagEnum of(String symbol) {
    return Arrays.stream(StubTagEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(StubTagEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(StubTagEnum.values())
        .filter(e -> e != StubTagEnum.Unknown)
        .map(StubTagEnum::symbol)
        .collect(Collectors.toList());
  }
}
