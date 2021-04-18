package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MainLogFlagEnum {
  Enum1_YF("1-研发"),
  Enum2_GS("2-公司"),
  Unknown("未知");

  private final String symbol;

  MainLogFlagEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MainLogFlagEnum of(String symbol) {
    return Arrays.stream(MainLogFlagEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MainLogFlagEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MainLogFlagEnum.values())
        .filter(e -> e != MainLogFlagEnum.Unknown)
        .map(MainLogFlagEnum::symbol)
        .collect(Collectors.toList());
  }
}
