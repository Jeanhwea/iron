package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MainMaterialCateEnum {
  Enum1_JK("1-进口"),
  Enum2_GC("2-国产"),
  Unknown("未知");

  private final String symbol;

  MainMaterialCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MainMaterialCateEnum of(String symbol) {
    return Arrays.stream(MainMaterialCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MainMaterialCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MainMaterialCateEnum.values())
        .filter(e -> e != MainMaterialCateEnum.Unknown)
        .map(MainMaterialCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
