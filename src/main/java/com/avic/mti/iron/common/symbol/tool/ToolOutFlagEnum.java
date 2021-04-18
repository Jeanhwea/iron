package com.avic.mti.iron.common.symbol.tool;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ToolOutFlagEnum {
  Enum1_YF("1-研发"),
  Enum2_GS("2-公司"),
  Unknown("未知");

  private final String symbol;

  ToolOutFlagEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ToolOutFlagEnum of(String symbol) {
    return Arrays.stream(ToolOutFlagEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ToolOutFlagEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ToolOutFlagEnum.values())
        .filter(e -> e != ToolOutFlagEnum.Unknown)
        .map(ToolOutFlagEnum::symbol)
        .collect(Collectors.toList());
  }
}
