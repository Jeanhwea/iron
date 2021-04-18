package com.avic.mti.iron.common.symbol.tool;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ToolLogCateEnum {
  Enum01_RK("01-入库"),
  Enum02_GHRK("02-归还入库"),
  Enum03_YK("03-移库"),
  Enum09_YK("09-其它入库"),
  Enum11_JY("11-借用"),
  Enum12_LY("12-领用"),
  Enum13_LXJY("13-零星借用"),
  Enum14_LXLY("14-零星领用"),
  Enum15_BF("15-报废"),
  Enum99_QT("99-其它"),
  Unknown("未知");

  private final String symbol;

  ToolLogCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ToolLogCateEnum of(String symbol) {
    return Arrays.stream(ToolLogCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ToolLogCateEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ToolLogCateEnum.values())
        .filter(e -> e != ToolLogCateEnum.Unknown)
        .map(ToolLogCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
