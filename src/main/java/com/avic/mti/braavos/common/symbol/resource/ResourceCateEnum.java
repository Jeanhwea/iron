package com.avic.mti.iron.common.symbol.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ResourceCateEnum {
  Enum1_SB("1-设备"),
  Enum2_MJ("2-模具"),
  Enum3_ZC("3-主材"),
  Enum4_FC("4-辅材"),
  Enum5_GJ("5-工具"),
  Enum6_PT("6-配套"),
  Unknown("未知");

  private final String symbol;

  ResourceCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ResourceCateEnum of(String symbol) {
    return Arrays.stream(ResourceCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ResourceCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ResourceCateEnum.values())
        .filter(e -> e != ResourceCateEnum.Unknown)
        .map(ResourceCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
