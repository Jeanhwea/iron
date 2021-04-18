package com.avic.mti.iron.common.symbol.asset;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AssetInTypeEnum {
  Enum0_ZD("0-自动"),
  Enum1_SG("1-手工"),
  Unknown("未知");

  private final String symbol;

  AssetInTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static AssetInTypeEnum of(String symbol) {
    return Arrays.stream(AssetInTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(AssetInTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(AssetInTypeEnum.values())
        .filter(e -> e != AssetInTypeEnum.Unknown)
        .map(AssetInTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
