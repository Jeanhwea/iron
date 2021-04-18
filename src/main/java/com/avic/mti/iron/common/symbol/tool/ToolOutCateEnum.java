package com.avic.mti.iron.common.symbol.tool;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 出库类型类
 *
 * @author Jinghui Hu
 * @since 2020-11-27, JDK1.8
 */
public enum ToolOutCateEnum {
  Enum1_JY("1-借用"),
  Enum2_LY("2-领用"),
  Enum3_LXJY("3-零星借用"),
  Enum4_LXLY("4-零星领用"),
  Enum5_BF("5-报废"),
  Unknown("未知");

  private final String symbol;

  ToolOutCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ToolOutCateEnum of(String symbol) {
    return Arrays.stream(ToolOutCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ToolOutCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ToolOutCateEnum.values())
        .filter(e -> e != ToolOutCateEnum.Unknown)
        .map(ToolOutCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
