package com.avic.mti.iron.common.symbol.waste;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WprdLogStatusEnum {
  Enum0_WSX("0-未生效"),
  Enum1_YSX("1-已生效"),
  Enum2_YWC("2-已完成"),
  Unknown("未知");

  private final String symbol;

  WprdLogStatusEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static WprdLogStatusEnum of(String symbol) {
    return Arrays.stream(WprdLogStatusEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(WprdLogStatusEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(WprdLogStatusEnum.values())
        .filter(e -> e != WprdLogStatusEnum.Unknown)
        .map(WprdLogStatusEnum::symbol)
        .collect(Collectors.toList());
  }
}
