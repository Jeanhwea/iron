package com.avic.mti.iron.main.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.main.domain.entity.MainRoom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainRoomHelper {

  public static MainRoom assignMainRoom(MainRoom mainRoom, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(mainRoom::updateUser);
    reader.stringFromKey("roomCate").ifPresent(mainRoom::roomCate);
    reader.stringFromKey("roomCode").ifPresent(mainRoom::roomCode);
    reader.stringFromKey("roomName").ifPresent(mainRoom::roomName);
    reader.stringFromKey("location").ifPresent(mainRoom::location);
    reader.longFromKey("parentRoomId").ifPresent(mainRoom::parentRoomId);
    reader.longFromKey("areaNum").ifPresent(mainRoom::areaNum);
    reader.longFromKey("shelfNum").ifPresent(mainRoom::shelfNum);
    reader.longFromKey("floorNum").ifPresent(mainRoom::floorNum);
    reader.longFromKey("placeNum").ifPresent(mainRoom::placeNum);
    reader.longFromKey("gridNum").ifPresent(mainRoom::gridNum);
    reader.stringFromKey("creatorNC").ifPresent(mainRoom::creatorNC);
    reader.timeFromKey("createDate").ifPresent(mainRoom::createDate);
    reader.stringFromKey("note").ifPresent(mainRoom::note);
    return mainRoom;
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

  private MainRoomHelper() {}
}
