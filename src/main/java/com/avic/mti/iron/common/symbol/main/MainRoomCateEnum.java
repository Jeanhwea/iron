package com.avic.mti.iron.common.symbol.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 库位类别定义
 *
 * @author Jinghui Hu
 * @since 2020-11-02, JDK1.8
 */
public enum MainRoomCateEnum {
  Enum0_ROOT("0-根"),
  Enum1_ROOM("1-库"),
  Enum2_AREA("2-区"),
  Enum3_SHELF("3-架"),
  Enum4_FLOOR("4-层"),
  Enum5_PLACE("5-位"),
  Enum6_GRID("6-格"),
  Unknown("未知");

  private final String symbol;

  MainRoomCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static MainRoomCateEnum of(String symbol) {
    return Arrays.stream(MainRoomCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(MainRoomCateEnum.Unknown);
  }

  public static MainRoomCateEnum of(int deepness) {
    if (deepness <= 0) {
      return MainRoomCateEnum.Enum0_ROOT;
    } else if (deepness <= 1) {
      return MainRoomCateEnum.Enum1_ROOM;
    } else if (deepness <= 2) {
      return MainRoomCateEnum.Enum2_AREA;
    } else if (deepness <= 3) {
      return MainRoomCateEnum.Enum3_SHELF;
    } else if (deepness <= 4) {
      return MainRoomCateEnum.Enum4_FLOOR;
    } else if (deepness <= 5) {
      return MainRoomCateEnum.Enum5_PLACE;
    } else if (deepness <= 6) {
      return MainRoomCateEnum.Enum6_GRID;
    } else {
      return MainRoomCateEnum.Unknown;
    }
  }

  public static List<String> expect() {
    return Arrays.stream(MainRoomCateEnum.values())
        .filter(e -> e != MainRoomCateEnum.Unknown)
        .map(MainRoomCateEnum::symbol)
        .collect(Collectors.toList());
  }
}