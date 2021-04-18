package com.avic.mti.iron.common.symbol.waste;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WprdOutFlagEnum {
  Enum1_YF("1-研发"),
  Enum2_GS("2-公司"),
  Unknown("未知");

  private final String symbol;

  WprdOutFlagEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static WprdOutFlagEnum of(String symbol) {
    return Arrays.stream(WprdOutFlagEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(WprdOutFlagEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(WprdOutFlagEnum.values())
        .filter(e -> e != WprdOutFlagEnum.Unknown)
        .map(WprdOutFlagEnum::symbol)
        .collect(Collectors.toList());
  }
}
