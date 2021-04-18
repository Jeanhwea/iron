package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 出库类型类
 *
 * @author Jinghui Hu
 * @since 2020-11-27, JDK1.8
 */
public enum MainOutCateEnum {
  Enum1_ZDLY("1-制单领用"),
  Enum2_LXLY("2-零星领用"),
  Enum3_BF("3-报废"),
  Enum4_YK("4-移库"),
  Unknown("未知");

  private final String symbol;

  MainOutCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MainOutCateEnum of(String symbol) {
    return Arrays.stream(MainOutCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MainOutCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MainOutCateEnum.values())
        .filter(e -> e != MainOutCateEnum.Unknown)
        .map(MainOutCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
