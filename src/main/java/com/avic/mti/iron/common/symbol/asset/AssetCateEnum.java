package com.avic.mti.iron.common.symbol.asset;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AssetCateEnum {
  Enum1_A("A类"),
  Enum2_B("B类"),
  Enum3_C("C类"),
  Unknown("未知");

  private final String symbol;

  AssetCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static AssetCateEnum of(String symbol) {
    return Arrays.stream(AssetCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(AssetCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(AssetCateEnum.values())
        .filter(e -> e != AssetCateEnum.Unknown)
        .map(AssetCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
