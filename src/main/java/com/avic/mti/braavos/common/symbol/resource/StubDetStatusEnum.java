package com.avic.mti.iron.common.symbol.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StubDetStatusEnum {
  Enum0_WSX("0-未生效"),
  Enum1_YSX("1-已生效"),
  Enum2_YCK("2-已出库"),
  Unknown("未知");

  private final String symbol;

  StubDetStatusEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static StubDetStatusEnum of(String symbol) {
    return Arrays.stream(StubDetStatusEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(StubDetStatusEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(StubDetStatusEnum.values())
        .filter(e -> e != StubDetStatusEnum.Unknown)
        .map(StubDetStatusEnum::symbol)
        .collect(Collectors.toList());
  }
}
