package com.avic.mti.iron.common.symbol.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接入网络状态
 *
 * @author Jinghui Hu
 * @since 2020-08-30, JDK1.8
 */
public enum ResourceConnStatusEnum {
  Enum0_JJR("0-未接入"),
  Enum1_WJR("1-已接入"),
  Unknown("未知");

  private final String symbol;

  ResourceConnStatusEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static ResourceConnStatusEnum of(String symbol) {
    return Arrays.stream(ResourceConnStatusEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(ResourceConnStatusEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(ResourceConnStatusEnum.values())
        .filter(e -> e != ResourceConnStatusEnum.Unknown)
        .map(ResourceConnStatusEnum::symbol)
        .collect(Collectors.toList());
  }
}
