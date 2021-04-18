package com.avic.mti.iron.common.symbol.tool;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ToolLogFlagEnum {
  Enum1_YF("1-研发"),
  Enum2_GS("2-公司"),
  Unknown("未知");

  private final String symbol;

  ToolLogFlagEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ToolLogFlagEnum of(String symbol) {
    return Arrays.stream(ToolLogFlagEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ToolLogFlagEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ToolLogFlagEnum.values())
        .filter(e -> e != ToolLogFlagEnum.Unknown)
        .map(ToolLogFlagEnum::symbol)
        .collect(Collectors.toList());
  }
}
