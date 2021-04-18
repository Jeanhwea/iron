package com.avic.mti.iron.common.symbol.measure;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 出库类型类
 *
 * @author Jinghui Hu
 * @since 2020-11-27, JDK1.8
 */
public enum MeasOutCateEnum {
  Enum1_JY("1-借用"),
  Enum2_LY("2-领用"),
  Enum3_LXJY("3-零星借用"),
  Enum4_LXLY("4-零星领用"),
  Enum5_BF("5-报废"),
  Enum6_SJ("6-送检"),
  Unknown("未知");

  private final String symbol;

  MeasOutCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MeasOutCateEnum of(String symbol) {
    return Arrays.stream(MeasOutCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MeasOutCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MeasOutCateEnum.values())
        .filter(e -> e != MeasOutCateEnum.Unknown)
        .map(MeasOutCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
