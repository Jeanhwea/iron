package com.avic.mti.iron.common.symbol.tool;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ToolInCateEnum {
  Enum1_RK("1-入库"),
  Enum2_GH("2-归还"),
  Enum3_YK("3-移库"),
  Enum9_QT("9-其它"),
  Unknown("未知");

  private final String symbol;

  ToolInCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ToolInCateEnum of(String symbol) {
    return Arrays.stream(ToolInCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ToolInCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ToolInCateEnum.values())
        .filter(e -> e != ToolInCateEnum.Unknown)
        .map(ToolInCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
