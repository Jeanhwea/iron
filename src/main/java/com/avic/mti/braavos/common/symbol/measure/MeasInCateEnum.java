package com.avic.mti.iron.common.symbol.measure;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MeasInCateEnum {
  Enum1_RK("1-入库"),
  Enum2_GH("2-归还"),
  Enum3_YK("3-移库"),
  Enum9_QT("9-其它"),
  Unknown("未知");

  private final String symbol;

  MeasInCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MeasInCateEnum of(String symbol) {
    return Arrays.stream(MeasInCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MeasInCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MeasInCateEnum.values())
        .filter(e -> e != MeasInCateEnum.Unknown)
        .map(MeasInCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
