package com.avic.mti.iron.common.symbol.tool;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ToolMaterialCateEnum {
  Enum1_JK("1-进口"),
  Enum2_GC("2-国产"),
  Unknown("未知");

  private final String symbol;

  ToolMaterialCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ToolMaterialCateEnum of(String symbol) {
    return Arrays.stream(ToolMaterialCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ToolMaterialCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ToolMaterialCateEnum.values())
        .filter(e -> e != ToolMaterialCateEnum.Unknown)
        .map(ToolMaterialCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
