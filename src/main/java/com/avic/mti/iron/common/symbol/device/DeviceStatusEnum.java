package com.avic.mti.iron.common.symbol.device;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DeviceStatusEnum {
  Enum1_ZCYZ("1-正常运转"),
  Enum2_GZTJ("2-故障停机"),
  Enum3_TJWX("3-停机维修"),
  Enum4_TJBY("4-停机保养"),
  Enum5_BF("5-报废"),
  Unknown("未知");

  private final String symbol;

  DeviceStatusEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static DeviceStatusEnum of(String symbol) {
    return Arrays.stream(DeviceStatusEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(DeviceStatusEnum.Unknown);
  }

  public static List<String> expect() {
    return Arrays.stream(DeviceStatusEnum.values())
        .filter(e -> e != DeviceStatusEnum.Unknown)
        .map(DeviceStatusEnum::symbol)
        .collect(Collectors.toList());
  }
}
