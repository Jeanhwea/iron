package com.avic.mti.iron.measure.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.measure.domain.entity.MeasRoom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MeasRoomHelper {

  public static MeasRoom assignMeasRoom(MeasRoom measRoom, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(measRoom::updateUser);
    reader.stringFromKey("roomCate").ifPresent(measRoom::roomCate);
    reader.stringFromKey("roomCode").ifPresent(measRoom::roomCode);
    reader.stringFromKey("roomName").ifPresent(measRoom::roomName);
    reader.stringFromKey("location").ifPresent(measRoom::location);
    reader.longFromKey("parentRoomId").ifPresent(measRoom::parentRoomId);
    reader.longFromKey("areaNum").ifPresent(measRoom::areaNum);
    reader.longFromKey("shelfNum").ifPresent(measRoom::shelfNum);
    reader.longFromKey("floorNum").ifPresent(measRoom::floorNum);
    reader.longFromKey("placeNum").ifPresent(measRoom::placeNum);
    reader.longFromKey("gridNum").ifPresent(measRoom::gridNum);
    reader.stringFromKey("creatorNC").ifPresent(measRoom::creatorNC);
    reader.timeFromKey("createDate").ifPresent(measRoom::createDate);
    reader.stringFromKey("note").ifPresent(measRoom::note);
    return measRoom;
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

  private MeasRoomHelper() {}
}
