package com.avic.mti.iron.waste.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.waste.domain.entity.WprdRoom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WprdRoomHelper {

  public static WprdRoom assignWprdRoom(WprdRoom wprdRoom, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(wprdRoom::updateUser);
    reader.stringFromKey("roomCate").ifPresent(wprdRoom::roomCate);
    reader.stringFromKey("roomCode").ifPresent(wprdRoom::roomCode);
    reader.stringFromKey("roomName").ifPresent(wprdRoom::roomName);
    reader.stringFromKey("location").ifPresent(wprdRoom::location);
    reader.longFromKey("parentRoomId").ifPresent(wprdRoom::parentRoomId);
    reader.longFromKey("areaNum").ifPresent(wprdRoom::areaNum);
    reader.longFromKey("shelfNum").ifPresent(wprdRoom::shelfNum);
    reader.longFromKey("floorNum").ifPresent(wprdRoom::floorNum);
    reader.longFromKey("placeNum").ifPresent(wprdRoom::placeNum);
    reader.longFromKey("gridNum").ifPresent(wprdRoom::gridNum);
    reader.stringFromKey("creatorNC").ifPresent(wprdRoom::creatorNC);
    reader.timeFromKey("createDate").ifPresent(wprdRoom::createDate);
    reader.stringFromKey("note").ifPresent(wprdRoom::note);
    return wprdRoom;
  }

  public static String getRoomName(int deepness, int index) {
    String repr = "";
    if (deepness <= 1) {
      repr = "库";
    } else if (deepness <= 2) {
      repr = "区";
    } else if (deepness <= 3) {
      repr = "架";
    } else if (deepness <= 4) {
      repr = "层";
    } else if (deepness <= 5) {
      repr = "位";
    } else if (deepness <= 6) {
      repr = "格";
    }

    return String.format("%02d%s", index + 1, repr);
  }

  public static List<Long> getInitDimension(Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    List<Long> dimension = new ArrayList<>();
    for (int i = 0; i < LEVEL_NAMES.length; i++) {
      long dim = reader.longFromKey(LEVEL_NAMES[i]).orElse(0L);
      if (dim > 0) {
        dimension.add(dim);
      } else {
        break;
      }
    }

    return dimension;
  }

  private static final String[] LEVEL_NAMES =
      new String[] {"areaNum", "shelfNum", "floorNum", "placeNum", "gridNum"};

  private WprdRoomHelper() {}
}
