package com.avic.mti.iron.common.symbol.measure;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MeasInTypeEnum {
  Enum0_ZD("0-自动"),
  Enum1_SG("1-手工"),
  Unknown("未知");

  private final String symbol;

  MeasInTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MeasInTypeEnum of(String symbol) {
    return Arrays.stream(MeasInTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MeasInTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MeasInTypeEnum.values())
        .filter(e -> e != MeasInTypeEnum.Unknown)
        .map(MeasInTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
