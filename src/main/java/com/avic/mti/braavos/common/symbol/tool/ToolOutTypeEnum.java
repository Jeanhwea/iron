package com.avic.mti.iron.common.symbol.tool;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ToolOutTypeEnum {
  Enum1_ZDCK("1-制单出库"),
  Enum2_ZJCK("2-直接出库"),
  Enum3_BFCK("3-报废出库"),
  Enum4_YKCK("4-移库出库"),
  Unknown("未知");

  private final String symbol;

  ToolOutTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ToolOutTypeEnum of(String symbol) {
    return Arrays.stream(ToolOutTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ToolOutTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ToolOutTypeEnum.values())
        .filter(e -> e != ToolOutTypeEnum.Unknown)
        .map(ToolOutTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
