package com.avic.mti.iron.common.symbol.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 出库单类别枚举
 *
 * @author Jinghui Hu
 * @since 2021-01-29, JDK1.8
 */
public enum StubCateEnum {
  Enum1_ZDLY("1-制单领用"),
  Enum2_LXLY("2-零星领用"),
  Unknown("未知");

  private final String symbol;

  StubCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static StubCateEnum of(String symbol) {
    return Arrays.stream(StubCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(StubCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(StubCateEnum.values())
        .filter(e -> e != StubCateEnum.Unknown)
        .map(StubCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
