package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 主材出库转态枚举类
 *
 * @author Jinghui Hu
 * @since 2020-11-27, JDK1.8
 */
public enum MainOutStatusEnum {
  Enum0_WSX("0-未生效"),
  Enum1_YSX("1-已生效"),
  Enum2_YWC("2-已完成"),
  Unknown("未知");

  private final String symbol;

  MainOutStatusEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MainOutStatusEnum of(String symbol) {
    return Arrays.stream(MainOutStatusEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MainOutStatusEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(MainOutStatusEnum.values())
        .filter(e -> e != MainOutStatusEnum.Unknown)
        .map(MainOutStatusEnum::symbol)
        .collect(Collectors.toList());
  }
}
