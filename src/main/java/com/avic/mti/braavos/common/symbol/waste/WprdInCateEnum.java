package com.avic.mti.iron.common.symbol.waste;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WprdInCateEnum {
  Enum1_RK("1-入库"),
  Enum2_GH("2-归还"),
  Enum3_YK("3-移库"),
  Enum9_QT("9-其它"),
  Unknown("未知");

  private final String symbol;

  WprdInCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static WprdInCateEnum of(String symbol) {
    return Arrays.stream(WprdInCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(WprdInCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(WprdInCateEnum.values())
        .filter(e -> e != WprdInCateEnum.Unknown)
        .map(WprdInCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
