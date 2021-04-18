package com.avic.mti.iron.common.symbol.measure;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MeasOutFlagEnum {
  Enum1_YF("1-研发"),
  Enum2_GS("2-公司"),
  Unknown("未知");

  private final String symbol;

  MeasOutFlagEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MeasOutFlagEnum of(String symbol) {
    return Arrays.stream(MeasOutFlagEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MeasOutFlagEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MeasOutFlagEnum.values())
        .filter(e -> e != MeasOutFlagEnum.Unknown)
        .map(MeasOutFlagEnum::symbol)
        .collect(Collectors.toList());
  }
}
