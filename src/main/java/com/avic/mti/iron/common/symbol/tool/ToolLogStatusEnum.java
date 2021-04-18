package com.avic.mti.iron.common.symbol.tool;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ToolLogStatusEnum {
  Enum0_WSX("0-未生效"),
  Enum1_YSX("1-已生效"),
  Enum2_YWC("2-已完成"),
  Unknown("未知");

  private final String symbol;

  ToolLogStatusEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ToolLogStatusEnum of(String symbol) {
    return Arrays.stream(ToolLogStatusEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ToolLogStatusEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ToolLogStatusEnum.values())
        .filter(e -> e != ToolLogStatusEnum.Unknown)
        .map(ToolLogStatusEnum::symbol)
        .collect(Collectors.toList());
  }
}
