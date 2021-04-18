package com.avic.mti.iron.common.symbol.device;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 设备类型
 *
 * @author Jinghui Hu
 * @since 2021-03-31, JDK1.8
 */
public enum DeviceTypeEnum {
  Enum0_CG("0-常规"),
  Enum1_GJ("1-关键"),
  Unknown("未知");

  private final String symbol;

  DeviceTypeEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static DeviceTypeEnum of(String symbol) {
    return Arrays.stream(DeviceTypeEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(DeviceTypeEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(DeviceTypeEnum.values())
        .filter(e -> e != DeviceTypeEnum.Unknown)
        .map(DeviceTypeEnum::symbol)
        .collect(Collectors.toList());
  }
}
