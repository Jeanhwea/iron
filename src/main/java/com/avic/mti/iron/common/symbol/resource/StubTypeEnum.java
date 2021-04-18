package com.avic.mti.iron.common.symbol.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 出库单类型枚举
 *
 * @author Jinghui Hu
 * @since 2021-01-29, JDK1.8
 */
public enum StubTypeEnum {
  Enum1_FLZ("1-芳纶纸"),
  Enum2_JDJ("2-节点胶"),
  Enum3_FQSZ("3-酚醛树脂"),
  Enum9_QT("9-其它"),
  Unknown("未知");

  private final String symbol;

  StubTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static StubTypeEnum of(String symbol) {
    return Arrays.stream(StubTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(StubTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(StubTypeEnum.values())
        .filter(e -> e != StubTypeEnum.Unknown)
        .map(StubTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
