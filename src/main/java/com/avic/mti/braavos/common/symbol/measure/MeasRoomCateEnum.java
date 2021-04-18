package com.avic.mti.iron.common.symbol.measure;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 库位类别定义
 *
 * @author Jinghui Hu
 * @since 2020-11-02, JDK1.8
 */
public enum MeasRoomCateEnum {
  Enum0_ROOT("0-根"),
  Enum1_ROOM("1-库"),
  Enum2_AREA("2-区"),
  Enum3_SHELF("3-架"),
  Enum4_FLOOR("4-层"),
  Enum5_PLACE("5-位"),
  Enum6_GRID("6-格"),
  Unknown("未知");

  private final String symbol;

  MeasRoomCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MeasRoomCateEnum of(String symbol) {
    return Arrays.stream(MeasRoomCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MeasRoomCateEnum.Unknown);
  }

  public static MeasRoomCateEnum of(int deepness) {
    if (deepness <= 0) {
      return MeasRoomCateEnum.Enum0_ROOT;
    } else if (deepness <= 1) {
      return MeasRoomCateEnum.Enum1_ROOM;
    } else if (deepness <= 2) {
      return MeasRoomCateEnum.Enum2_AREA;
    } else if (deepness <= 3) {
      return MeasRoomCateEnum.Enum3_SHELF;
    } else if (deepness <= 4) {
      return MeasRoomCateEnum.Enum4_FLOOR;
    } else if (deepness <= 5) {
      return MeasRoomCateEnum.Enum5_PLACE;
    } else if (deepness <= 6) {
      return MeasRoomCateEnum.Enum6_GRID;
    } else {
      return MeasRoomCateEnum.Unknown;
    }
  }

  public static List<String> expect() {
    return Arrays.stream(MeasRoomCateEnum.values())
        .filter(e -> e != MeasRoomCateEnum.Unknown)
        .map(MeasRoomCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
