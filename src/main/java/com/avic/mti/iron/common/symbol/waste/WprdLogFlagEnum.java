package com.avic.mti.iron.common.symbol.waste;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WprdLogFlagEnum {
  Enum1_YF("1-研发"),
  Enum2_GS("2-公司"),
  Unknown("未知");

  private final String symbol;

  WprdLogFlagEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static WprdLogFlagEnum of(String symbol) {
    return Arrays.stream(WprdLogFlagEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(WprdLogFlagEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(WprdLogFlagEnum.values())
        .filter(e -> e != WprdLogFlagEnum.Unknown)
        .map(WprdLogFlagEnum::symbol)
        .collect(Collectors.toList());
  }
}
