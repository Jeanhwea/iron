package com.avic.mti.iron.common.symbol.waste;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 出库类型类
 *
 * @author Jinghui Hu
 * @since 2020-11-27, JDK1.8
 */
public enum WprdOutCateEnum {
  Enum1_JY("1-借用"),
  Enum2_LY("2-领用"),
  Enum3_LXJY("3-零星借用"),
  Enum4_LXLY("4-零星领用"),
  Enum5_BF("5-报废"),
  Unknown("未知");

  private final String symbol;

  WprdOutCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static WprdOutCateEnum of(String symbol) {
    return Arrays.stream(WprdOutCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(WprdOutCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(WprdOutCateEnum.values())
        .filter(e -> e != WprdOutCateEnum.Unknown)
        .map(WprdOutCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
