package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MainInTypeEnum {
  Enum0_ZD("0-自动"),
  Enum1_SG("1-手工"),
  Unknown("未知");

  private final String symbol;

  MainInTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MainInTypeEnum of(String symbol) {
    return Arrays.stream(MainInTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MainInTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MainInTypeEnum.values())
        .filter(e -> e != MainInTypeEnum.Unknown)
        .map(MainInTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
