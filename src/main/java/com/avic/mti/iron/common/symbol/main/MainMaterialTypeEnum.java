package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MainMaterialTypeEnum {
  Enum1_FWZ("1-芳纶纸"),
  Enum2_JDJ("2-节点胶"),
  Enum3_FQSZ("3-酚醛树脂"),
  Enum9_QT("9-其它"),
  Unknown("未知");

  private final String symbol;

  MainMaterialTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MainMaterialTypeEnum of(String symbol) {
    return Arrays.stream(MainMaterialTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MainMaterialTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MainMaterialTypeEnum.values())
        .filter(e -> e != MainMaterialTypeEnum.Unknown)
        .map(MainMaterialTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
