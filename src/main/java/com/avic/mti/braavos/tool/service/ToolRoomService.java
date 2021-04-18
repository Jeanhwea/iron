package com.avic.mti.iron.tool.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolRoom;
import java.util.List;
import java.util.Map;

public interface ToolRoomService {

  List<ToolRoom> findAllToolRooms(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  ToolRoom findById(long toolRoomId);

  ToolRoom createToolRoom(Map<String, Object> params);

  ToolRoom replaceToolRoom(long toolRoomId, Map<String, Object> params);

  void deleteToolRoom(long toolRoomId);

  ToolRoom initToolRoom(Map<String, Object> params);

  Map<String, Object> getToolRoomTree(long toolRoomId, Map<String, Object> params);
}
