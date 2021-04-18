package com.avic.mti.iron.common.symbol.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StubDetTypeEnum {
  Enum1_FLZ("1-芳纶纸"),
  Enum2_JDJ("2-节点胶"),
  Enum3_FQSZ("3-酚醛树脂"),
  Enum9_QT("9-其它"),
  Unknown("未知");

  private final String symbol;

  StubDetTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static StubDetTypeEnum of(String symbol) {
    return Arrays.stream(StubDetTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(StubDetTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(StubDetTypeEnum.values())
        .filter(e -> e != StubDetTypeEnum.Unknown)
        .map(StubDetTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
