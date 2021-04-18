package com.avic.mti.iron.tool.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.tool.domain.entity.ToolRoom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ToolRoomHelper {

  public static ToolRoom assignToolRoom(ToolRoom toolRoom, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(toolRoom::updateUser);
    reader.stringFromKey("roomCate").ifPresent(toolRoom::roomCate);
    reader.stringFromKey("roomCode").ifPresent(toolRoom::roomCode);
    reader.stringFromKey("roomName").ifPresent(toolRoom::roomName);
    reader.stringFromKey("location").ifPresent(toolRoom::location);
    reader.longFromKey("parentRoomId").ifPresent(toolRoom::parentRoomId);
    reader.longFromKey("areaNum").ifPresent(toolRoom::areaNum);
    reader.longFromKey("shelfNum").ifPresent(toolRoom::shelfNum);
    reader.longFromKey("floorNum").ifPresent(toolRoom::floorNum);
    reader.longFromKey("placeNum").ifPresent(toolRoom::placeNum);
    reader.longFromKey("gridNum").ifPresent(toolRoom::gridNum);
    reader.stringFromKey("creatorNC").ifPresent(toolRoom::creatorNC);
    reader.timeFromKey("createDate").ifPresent(toolRoom::createDate);
    reader.stringFromKey("note").ifPresent(toolRoom::note);
    return toolRoom;
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

  private ToolRoomHelper() {}
}
