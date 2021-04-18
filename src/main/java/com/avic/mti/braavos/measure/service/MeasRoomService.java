package com.avic.mti.iron.measure.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasRoom;
import java.util.List;
import java.util.Map;

public interface MeasRoomService {

  List<MeasRoom> findAllMeasRooms(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  MeasRoom findById(long measRoomId);

  MeasRoom createMeasRoom(Map<String, Object> params);

  MeasRoom replaceMeasRoom(long measRoomId, Map<String, Object> params);

  void deleteMeasRoom(long measRoomId);

  MeasRoom initMeasRoom(Map<String, Object> params);

  Map<String, Object> getMeasRoomTree(long measRoomId, Map<String, Object> params);
}
