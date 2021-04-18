package com.avic.mti.iron.measure.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasRoom;
import com.avic.mti.iron.measure.service.MeasRoomService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/measures/rooms")
public class MeasRoomController {

  public static final Logger logger = LoggerFactory.getLogger(MeasRoomController.class);

  private final MeasRoomService measRoomService;

  @Autowired
  public MeasRoomController(MeasRoomService measRoomService) {
    this.measRoomService = measRoomService;
  }

  @GetMapping("/list")
  public List<MeasRoom> getMeasRoomsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("roomCate", FieldTypeEnum.Enumeration)
            .put("roomCode", FieldTypeEnum.String)
            .put("roomName", FieldTypeEnum.String)
            .put("location", FieldTypeEnum.String)
            .put("parentRoomId", FieldTypeEnum.Long)
            .put("areaNum", FieldTypeEnum.Long)
            .put("shelfNum", FieldTypeEnum.Long)
            .put("floorNum", FieldTypeEnum.Long)
            .put("placeNum", FieldTypeEnum.Long)
            .put("gridNum", FieldTypeEnum.Long)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            .fields();
    return this.measRoomService.findAllMeasRooms(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public MeasRoom postMeasRooms(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("roomCate", ParamTypeEnum.String)
        .require("roomCode", ParamTypeEnum.String)
        .require("roomName", ParamTypeEnum.String)
        .require("creatorNC", ParamTypeEnum.String)
        // .require("parentRoomId", ParamTypeEnum.Long)
        .require("areaNum", ParamTypeEnum.Long)
        .require("shelfNum", ParamTypeEnum.Long)
        .require("floorNum", ParamTypeEnum.Long)
        .require("placeNum", ParamTypeEnum.Long)
        .require("gridNum", ParamTypeEnum.Long)
        .require("creatorNC", ParamTypeEnum.String)
        .require("createDate", ParamTypeEnum.Timestamp)
        .ensure();
    return this.measRoomService.createMeasRoom(params);
  }

  @GetMapping("/{measRoomId}")
  public MeasRoom getMeasRoom(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measRoomId,
      @RequestParam Map<String, Object> params) {
    return this.measRoomService.findById(measRoomId);
  }

  @PutMapping("/{measRoomId}")
  public MeasRoom putMeasRoom(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measRoomId,
      @RequestBody Map<String, Object> params) {
    return this.measRoomService.replaceMeasRoom(measRoomId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{measRoomId}")
  public void deleteMeasRoom(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measRoomId,
      @RequestParam Map<String, Object> params) {
    this.measRoomService.deleteMeasRoom(measRoomId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/init")
  public MeasRoom initMeasRooms(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("roomCode", ParamTypeEnum.String).ensure();
    return this.measRoomService.initMeasRoom(params);
  }

  @GetMapping("/{measRoomId}/tree")
  public Map<String, Object> getMeasRoomTree(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measRoomId,
      @RequestParam Map<String, Object> params) {
    return this.measRoomService.getMeasRoomTree(measRoomId, params);
  }
}
