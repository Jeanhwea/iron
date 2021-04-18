package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RetestStatusEnum {
  Enum1_HG("1-合格"),
  Enum2_BHG("2-不合格"),
  Unknown("未知");

  private final String symbol;

  RetestStatusEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static RetestStatusEnum of(String symbol) {
    return Arrays.stream(RetestStatusEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(RetestStatusEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(RetestStatusEnum.values())
        .filter(e -> e != RetestStatusEnum.Unknown)
        .map(RetestStatusEnum::symbol)
        .collect(Collectors.toList());
  }
}
