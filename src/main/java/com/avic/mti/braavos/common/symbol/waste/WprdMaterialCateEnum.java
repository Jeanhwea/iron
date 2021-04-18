package com.avic.mti.iron.common.symbol.waste;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WprdMaterialCateEnum {
  Enum1_JK("1-进口"),
  Enum2_GC("2-国产"),
  Unknown("未知");

  private final String symbol;

  WprdMaterialCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static WprdMaterialCateEnum of(String symbol) {
    return Arrays.stream(WprdMaterialCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(WprdMaterialCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(WprdMaterialCateEnum.values())
        .filter(e -> e != WprdMaterialCateEnum.Unknown)
        .map(WprdMaterialCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
