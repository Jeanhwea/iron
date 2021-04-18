package com.avic.mti.iron.waste.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdRoom;
import com.avic.mti.iron.waste.service.WprdRoomService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waste/products/rooms")
public class WprdRoomController {

  public static final Logger logger = LoggerFactory.getLogger(WprdRoomController.class);

  private final WprdRoomService wprdRoomService;

  @Autowired
  public WprdRoomController(WprdRoomService wprdRoomService) {
    this.wprdRoomService = wprdRoomService;
  }

  @GetMapping("/list")
  public List<WprdRoom> getWprdRoomsList(
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
    return this.wprdRoomService.findAllWprdRooms(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public WprdRoom postWprdRooms(
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
    return this.wprdRoomService.createWprdRoom(params);
  }

  @GetMapping("/{wprdRoomId}")
  public WprdRoom getWprdRoom(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdRoomId,
      @RequestParam Map<String, Object> params) {
    return this.wprdRoomService.findById(wprdRoomId);
  }

  @PutMapping("/{wprdRoomId}")
  public WprdRoom putWprdRoom(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdRoomId,
      @RequestBody Map<String, Object> params) {
    return this.wprdRoomService.replaceWprdRoom(wprdRoomId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{wprdRoomId}")
  public void deleteWprdRoom(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdRoomId,
      @RequestParam Map<String, Object> params) {
    this.wprdRoomService.deleteWprdRoom(wprdRoomId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/init")
  public WprdRoom initWprdRooms(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("roomCode", ParamTypeEnum.String).ensure();
    return this.wprdRoomService.initWprdRoom(params);
  }

  @GetMapping("/{wprdRoomId}/tree")
  public Map<String, Object> getWprdRoomTree(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdRoomId,
      @RequestParam Map<String, Object> params) {
    return this.wprdRoomService.getWprdRoomTree(wprdRoomId, params);
  }
}
