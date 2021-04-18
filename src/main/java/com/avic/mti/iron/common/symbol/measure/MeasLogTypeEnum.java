package com.avic.mti.iron.common.symbol.measure;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MeasLogTypeEnum {
  Enum1_ZDCK("1-制单出库"),
  Enum2_ZJCK("2-直接出库"),
  Enum3_BFCK("3-报废出库"),
  Enum4_YKCK("4-移库出库"),
  Enum5_SJCK("5-送检出库"),
  Unknown("未知");

  private final String symbol;

  MeasLogTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MeasLogTypeEnum of(String symbol) {
    return Arrays.stream(MeasLogTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MeasLogTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MeasLogTypeEnum.values())
        .filter(e -> e != MeasLogTypeEnum.Unknown)
        .map(MeasLogTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
