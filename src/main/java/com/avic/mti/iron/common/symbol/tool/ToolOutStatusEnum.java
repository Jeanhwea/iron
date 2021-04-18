package com.avic.mti.iron.common.symbol.tool;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 主材出库转态枚举类
 *
 * @author Jinghui Hu
 * @since 2020-11-27, JDK1.8
 */
public enum ToolOutStatusEnum {
  Enum0_WSX("0-未生效"),
  Enum1_YSX("1-已生效"),
  Enum2_YWC("2-已完成"),
  Unknown("未知");

  private final String symbol;

  ToolOutStatusEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ToolOutStatusEnum of(String symbol) {
    return Arrays.stream(ToolOutStatusEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ToolOutStatusEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ToolOutStatusEnum.values())
        .filter(e -> e != ToolOutStatusEnum.Unknown)
        .map(ToolOutStatusEnum::symbol)
        .collect(Collectors.toList());
  }
}
