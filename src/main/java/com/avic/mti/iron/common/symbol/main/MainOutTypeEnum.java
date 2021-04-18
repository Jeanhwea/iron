package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MainOutTypeEnum {
  Enum1_ZDCK("1-制单出库"),
  Enum2_ZJCK("2-直接出库"),
  Enum3_BFCK("3-报废出库"),
  Enum4_YKCK("4-移库出库"),
  Unknown("未知");

  private final String symbol;

  MainOutTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MainOutTypeEnum of(String symbol) {
    return Arrays.stream(MainOutTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MainOutTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MainOutTypeEnum.values())
        .filter(e -> e != MainOutTypeEnum.Unknown)
        .map(MainOutTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
