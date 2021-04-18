package com.avic.mti.iron.tool.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.domain.entity.ToolShelf;
import com.avic.mti.iron.tool.service.ToolShelfService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tools/shelfs")
public class ToolShelfController {

  public static final Logger logger = LoggerFactory.getLogger(ToolShelfController.class);

  private final ToolShelfService toolShelfService;

  @Autowired
  public ToolShelfController(ToolShelfService toolShelfService) {
    this.toolShelfService = toolShelfService;
  }

  @GetMapping("/list")
  public List<ToolShelf> getToolShelfsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("roomCode", FieldTypeEnum.String)
            .put("shelfCode", FieldTypeEnum.String)
            .put("toolNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            .put("detailJson", FieldTypeEnum.String)
            .fields();
    return this.toolShelfService.findAllToolShelfs(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public ToolShelf postToolShelfs(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("roomCode", ParamTypeEnum.String)
        .require("shelfCode", ParamTypeEnum.String)
        .require("creatorNC", ParamTypeEnum.String)
        .require("createDate", ParamTypeEnum.Timestamp)
        .ensure();
    return this.toolShelfService.createToolShelf(params);
  }

  @GetMapping("/{toolShelfId}")
  public ToolShelf getToolShelf(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolShelfId,
      @RequestParam Map<String, Object> params) {
    return this.toolShelfService.findById(toolShelfId);
  }

  @PutMapping("/{toolShelfId}")
  public ToolShelf putToolShelf(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolShelfId,
      @RequestBody Map<String, Object> params) {
    return this.toolShelfService.replaceToolShelf(toolShelfId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{toolShelfId}")
  public void deleteToolShelf(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolShelfId,
      @RequestParam Map<String, Object> params) {
    this.toolShelfService.deleteToolShelf(toolShelfId);
  }

  @GetMapping("/in")
  public List<ToolIn> getToolShelfIn(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.toolShelfService.findShelfIns(params);
  }
}
