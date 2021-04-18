package com.avic.mti.iron.common.symbol.measure;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MeasLogFlagEnum {
  Enum1_YF("1-研发"),
  Enum2_GS("2-公司"),
  Unknown("未知");

  private final String symbol;

  MeasLogFlagEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MeasLogFlagEnum of(String symbol) {
    return Arrays.stream(MeasLogFlagEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MeasLogFlagEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MeasLogFlagEnum.values())
        .filter(e -> e != MeasLogFlagEnum.Unknown)
        .map(MeasLogFlagEnum::symbol)
        .collect(Collectors.toList());
  }
}
