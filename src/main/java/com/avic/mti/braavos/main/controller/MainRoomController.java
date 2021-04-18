package com.avic.mti.iron.main.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainRoom;
import com.avic.mti.iron.main.service.MainRoomService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materials/rooms")
public class MainRoomController {

  public static final Logger logger = LoggerFactory.getLogger(MainRoomController.class);

  private final MainRoomService mainRoomService;

  @Autowired
  public MainRoomController(MainRoomService mainRoomService) {
    this.mainRoomService = mainRoomService;
  }

  @GetMapping("/list")
  public List<MainRoom> getMainRoomsList(
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
    return this.mainRoomService.findAllMainRooms(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public MainRoom postMainRooms(
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
    return this.mainRoomService.createMainRoom(params);
  }

  @GetMapping("/{mainRoomId}")
  public MainRoom getMainRoom(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainRoomId,
      @RequestParam Map<String, Object> params) {
    return this.mainRoomService.findById(mainRoomId);
  }

  @PutMapping("/{mainRoomId}")
  public MainRoom putMainRoom(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainRoomId,
      @RequestBody Map<String, Object> params) {
    return this.mainRoomService.replaceMainRoom(mainRoomId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{mainRoomId}")
  public void deleteMainRoom(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainRoomId,
      @RequestParam Map<String, Object> params) {
    this.mainRoomService.deleteMainRoom(mainRoomId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/init")
  public MainRoom initMainRooms(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("roomCode", ParamTypeEnum.String).ensure();
    return this.mainRoomService.initMainRoom(params);
  }

  @GetMapping("/{mainRoomId}/tree")
  public Map<String, Object> getMainRoomTree(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainRoomId,
      @RequestParam Map<String, Object> params) {
    return this.mainRoomService.getMainRoomTree(mainRoomId, params);
  }
}
