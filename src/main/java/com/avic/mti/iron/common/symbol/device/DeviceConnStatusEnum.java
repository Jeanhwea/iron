package com.avic.mti.iron.common.symbol.device;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DeviceConnStatusEnum {
  Enum0_JJR("0-未接入"),
  Enum1_WJR("1-已接入"),
  Unknown("未知");

  private final String symbol;

  DeviceConnStatusEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static DeviceConnStatusEnum of(String symbol) {
    return Arrays.stream(DeviceConnStatusEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(DeviceConnStatusEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(DeviceConnStatusEnum.values())
        .filter(e -> e != DeviceConnStatusEnum.Unknown)
        .map(DeviceConnStatusEnum::symbol)
        .collect(Collectors.toList());
  }
}
