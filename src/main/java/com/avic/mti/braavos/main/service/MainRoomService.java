package com.avic.mti.iron.main.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainRoom;
import java.util.List;
import java.util.Map;

public interface MainRoomService {

  List<MainRoom> findAllMainRooms(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  MainRoom findById(long mainRoomId);

  MainRoom createMainRoom(Map<String, Object> params);

  MainRoom replaceMainRoom(long mainRoomId, Map<String, Object> params);

  void deleteMainRoom(long mainRoomId);

  MainRoom initMainRoom(Map<String, Object> params);

  Map<String, Object> getMainRoomTree(long mainRoomId, Map<String, Object> params);
}
