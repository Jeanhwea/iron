package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MainLogStatusEnum {
  Enum0_WSX("0-未生效"),
  Enum1_YSX("1-已生效"),
  Enum2_YWC("2-已完成"),
  Unknown("未知");

  private final String symbol;

  MainLogStatusEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MainLogStatusEnum of(String symbol) {
    return Arrays.stream(MainLogStatusEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MainLogStatusEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MainLogStatusEnum.values())
        .filter(e -> e != MainLogStatusEnum.Unknown)
        .map(MainLogStatusEnum::symbol)
        .collect(Collectors.toList());
  }
}
