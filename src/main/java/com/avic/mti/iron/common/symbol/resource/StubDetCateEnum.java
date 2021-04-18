package com.avic.mti.iron.common.symbol.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 出库单明细类型
 *
 * @author Jinghui Hu
 * @since 2021-01-29, JDK1.8
 */
public enum StubDetCateEnum {
  Enum1_ZDLY("1-制单领用"),
  Enum2_LXLY("2-零星领用"),
  Unknown("未知");

  private final String symbol;

  StubDetCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static StubDetCateEnum of(String symbol) {
    return Arrays.stream(StubDetCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(StubDetCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(StubDetCateEnum.values())
        .filter(e -> e != StubDetCateEnum.Unknown)
        .map(StubDetCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
