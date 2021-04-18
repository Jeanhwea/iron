package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MainLogCateEnum {
  Enum01_RK("01-入库"),
  Enum02_GHRK("02-归还入库"),
  Enum03_YK("03-移库"),
  Enum09_YK("09-其它入库"),
  Enum11_ZDLY("11-制单领用"),
  Enum12_LXLY("12-零星领用"),
  Enum13_ZDJY("13-制单借用"),
  Enum14_LXJY("14-零星借用"),
  Enum15_BF("15-报废"),
  Enum99_QT("99-其它"),
  Unknown("未知");

  private final String symbol;

  MainLogCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MainLogCateEnum of(String symbol) {
    return Arrays.stream(MainLogCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MainLogCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MainLogCateEnum.values())
        .filter(e -> e != MainLogCateEnum.Unknown)
        .map(MainLogCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
