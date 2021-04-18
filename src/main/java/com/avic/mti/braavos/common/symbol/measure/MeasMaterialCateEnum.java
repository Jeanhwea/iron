package com.avic.mti.iron.common.symbol.measure;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MeasMaterialCateEnum {
  Enum1_JK("1-进口"),
  Enum2_GC("2-国产"),
  Unknown("未知");

  private final String symbol;

  MeasMaterialCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MeasMaterialCateEnum of(String symbol) {
    return Arrays.stream(MeasMaterialCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MeasMaterialCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MeasMaterialCateEnum.values())
        .filter(e -> e != MeasMaterialCateEnum.Unknown)
        .map(MeasMaterialCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
