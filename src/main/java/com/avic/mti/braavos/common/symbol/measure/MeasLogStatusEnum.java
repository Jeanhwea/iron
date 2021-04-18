package com.avic.mti.iron.common.symbol.measure;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MeasLogStatusEnum {
  Enum0_WSX("0-未生效"),
  Enum1_YSX("1-已生效"),
  Enum2_YWC("2-已完成"),
  Unknown("未知");

  private final String symbol;

  MeasLogStatusEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MeasLogStatusEnum of(String symbol) {
    return Arrays.stream(MeasLogStatusEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MeasLogStatusEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MeasLogStatusEnum.values())
        .filter(e -> e != MeasLogStatusEnum.Unknown)
        .map(MeasLogStatusEnum::symbol)
        .collect(Collectors.toList());
  }
}
