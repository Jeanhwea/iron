package com.avic.mti.iron.common.symbol.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StubFlagEnum {
  Enum1_SC("1-生产"),
  Enum2_YF("2-研发"),
  Unknown("未知");

  private final String symbol;

  StubFlagEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static StubFlagEnum of(String symbol) {
    return Arrays.stream(StubFlagEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(StubFlagEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(StubFlagEnum.values())
        .filter(e -> e != StubFlagEnum.Unknown)
        .map(StubFlagEnum::symbol)
        .collect(Collectors.toList());
  }
}
