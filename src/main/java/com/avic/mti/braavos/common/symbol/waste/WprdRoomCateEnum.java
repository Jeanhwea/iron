package com.avic.mti.iron.common.symbol.waste;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 库位类别定义
 *
 * @author Jinghui Hu
 * @since 2020-11-02, JDK1.8
 */
public enum WprdRoomCateEnum {
  Enum0_ROOT("0-根"),
  Enum1_ROOM("1-库"),
  Enum2_AREA("2-区"),
  Enum3_SHELF("3-架"),
  Enum4_FLOOR("4-层"),
  Enum5_PLACE("5-位"),
  Enum6_GRID("6-格"),
  Unknown("未知");

  private final String symbol;

  WprdRoomCateEnum(String symbol) {
    this.symbol = symbol;
  }

  public String symbol() {
    return this.symbol;
  }

  public static WprdRoomCateEnum of(String symbol) {
    return Arrays.stream(WprdRoomCateEnum.values())
        .filter(element -> element.symbol().equals(symbol))
        .findFirst()
        .orElse(WprdRoomCateEnum.Unknown);
  }

  public static WprdRoomCateEnum of(int deepness) {
    if (deepness <= 0) {
      return WprdRoomCateEnum.Enum0_ROOT;
    } else if (deepness <= 1) {
      return WprdRoomCateEnum.Enum1_ROOM;
    } else if (deepness <= 2) {
      return WprdRoomCateEnum.Enum2_AREA;
    } else if (deepness <= 3) {
      return WprdRoomCateEnum.Enum3_SHELF;
    } else if (deepness <= 4) {
      return WprdRoomCateEnum.Enum4_FLOOR;
    } else if (deepness <= 5) {
      return WprdRoomCateEnum.Enum5_PLACE;
    } else if (deepness <= 6) {
      return WprdRoomCateEnum.Enum6_GRID;
    } else {
      return WprdRoomCateEnum.Unknown;
    }
  }

  public static List<String> expect() {
    return Arrays.stream(WprdRoomCateEnum.values())
        .filter(e -> e != WprdRoomCateEnum.Unknown)
        .map(WprdRoomCateEnum::symbol)
        .collect(Collectors.toList());
  }
}
