package com.avic.mti.iron.common.symbol.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源类型枚举类
 *
 * @author Jinghui Hu
 * @since 2020-08-30, JDK1.8
 */
public enum ResourceTypeEnum {
  Enum1_ZYX("1-占用型"),
  Enum2_XHX("2-消耗型"),
  Unknown("未知");

  private final String symbol;

  ResourceTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ResourceTypeEnum of(String symbol) {
    return Arrays.stream(ResourceTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ResourceTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ResourceTypeEnum.values())
        .filter(e -> e != ResourceTypeEnum.Unknown)
        .map(ResourceTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
