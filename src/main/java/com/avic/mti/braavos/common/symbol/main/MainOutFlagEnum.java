package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MainOutFlagEnum {
  Enum1_YF("1-研发"),
  Enum2_GS("2-公司"),
  Unknown("未知");

  private final String symbol;

  MainOutFlagEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MainOutFlagEnum of(String symbol) {
    return Arrays.stream(MainOutFlagEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MainOutFlagEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MainOutFlagEnum.values())
        .filter(e -> e != MainOutFlagEnum.Unknown)
        .map(MainOutFlagEnum::symbol)
        .collect(Collectors.toList());
  }
}
