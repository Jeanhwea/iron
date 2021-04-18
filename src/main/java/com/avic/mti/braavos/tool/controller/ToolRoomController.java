package com.avic.mti.iron.tool.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolRoom;
import com.avic.mti.iron.tool.service.ToolRoomService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tools/rooms")
public class ToolRoomController {

  public static final Logger logger = LoggerFactory.getLogger(ToolRoomController.class);

  private final ToolRoomService toolRoomService;

  @Autowired
  public ToolRoomController(ToolRoomService toolRoomService) {
    this.toolRoomService = toolRoomService;
  }

  @GetMapping("/list")
  public List<ToolRoom> getToolRoomsList(
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
    return this.toolRoomService.findAllToolRooms(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public ToolRoom postToolRooms(
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
    return this.toolRoomService.createToolRoom(params);
  }

  @GetMapping("/{toolRoomId}")
  public ToolRoom getToolRoom(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolRoomId,
      @RequestParam Map<String, Object> params) {
    return this.toolRoomService.findById(toolRoomId);
  }

  @PutMapping("/{toolRoomId}")
  public ToolRoom putToolRoom(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolRoomId,
      @RequestBody Map<String, Object> params) {
    return this.toolRoomService.replaceToolRoom(toolRoomId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{toolRoomId}")
  public void deleteToolRoom(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolRoomId,
      @RequestParam Map<String, Object> params) {
    this.toolRoomService.deleteToolRoom(toolRoomId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/init")
  public ToolRoom initToolRooms(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("roomCode", ParamTypeEnum.String).ensure();
    return this.toolRoomService.initToolRoom(params);
  }

  @GetMapping("/{toolRoomId}/tree")
  public Map<String, Object> getToolRoomTree(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolRoomId,
      @RequestParam Map<String, Object> params) {
    return this.toolRoomService.getToolRoomTree(toolRoomId, params);
  }
}
