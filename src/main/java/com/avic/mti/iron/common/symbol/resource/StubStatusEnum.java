package com.avic.mti.iron.common.symbol.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StubStatusEnum {
  Enum0_WSX("0-未生效"),
  Enum1_YSX("1-已生效"),
  Enum2_CKZ("2-出库中"),
  Enum3_YCK("3-已出库"),
  Unknown("未知");

  private final String symbol;

  StubStatusEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static StubStatusEnum of(String symbol) {
    return Arrays.stream(StubStatusEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(StubStatusEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(StubStatusEnum.values())
        .filter(e -> e != StubStatusEnum.Unknown)
        .map(StubStatusEnum::symbol)
        .collect(Collectors.toList());
  }
}
