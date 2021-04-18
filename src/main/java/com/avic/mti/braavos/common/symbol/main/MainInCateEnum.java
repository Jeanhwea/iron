package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MainInCateEnum {
  Enum1_RK("1-入库"),
  Enum2_GH("2-归还"),
  Enum3_YK("3-移库"),
  Enum9_QT("9-其它"),
  Unknown("未知");

  private final String symbol;

  MainInCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MainInCateEnum of(String symbol) {
    return Arrays.stream(MainInCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MainInCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MainInCateEnum.values())
        .filter(e -> e != MainInCateEnum.Unknown)
        .map(MainInCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
