package com.avic.mti.iron.waste.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdRoom;
import java.util.List;
import java.util.Map;

public interface WprdRoomService {

  List<WprdRoom> findAllWprdRooms(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  WprdRoom findById(long wprdRoomId);

  WprdRoom createWprdRoom(Map<String, Object> params);

  WprdRoom replaceWprdRoom(long wprdRoomId, Map<String, Object> params);

  void deleteWprdRoom(long wprdRoomId);

  WprdRoom initWprdRoom(Map<String, Object> params);

  Map<String, Object> getWprdRoomTree(long wprdRoomId, Map<String, Object> params);
}
