package com.avic.mti.iron.common.symbol.tool;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ToolInTypeEnum {
  Enum0_ZD("0-自动"),
  Enum1_SG("1-手工"),
  Unknown("未知");

  private final String symbol;

  ToolInTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ToolInTypeEnum of(String symbol) {
    return Arrays.stream(ToolInTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ToolInTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ToolInTypeEnum.values())
        .filter(e -> e != ToolInTypeEnum.Unknown)
        .map(ToolInTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
