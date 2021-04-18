package com.avic.mti.iron.common.symbol.waste;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WprdInTypeEnum {
  Enum0_ZD("0-自动"),
  Enum1_SG("1-手工"),
  Unknown("未知");

  private final String symbol;

  WprdInTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static WprdInTypeEnum of(String symbol) {
    return Arrays.stream(WprdInTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(WprdInTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(WprdInTypeEnum.values())
        .filter(e -> e != WprdInTypeEnum.Unknown)
        .map(WprdInTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
