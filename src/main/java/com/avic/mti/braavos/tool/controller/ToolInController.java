package com.avic.mti.iron.tool.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.service.ToolInService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tools/in")
public class ToolInController {

  public static final Logger logger = LoggerFactory.getLogger(ToolInController.class);

  private final ToolInService toolInService;

  @Autowired
  public ToolInController(ToolInService toolInService) {
    this.toolInService = toolInService;
  }

  @GetMapping("")
  public Page<ToolIn> getToolInsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("inCode", FieldTypeEnum.String)
            .put("inCate", FieldTypeEnum.Enumeration)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("toolId", FieldTypeEnum.Long)
            .put("toolCode", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("toolName", FieldTypeEnum.String)
            .put("toolSpec", FieldTypeEnum.String)
            .put("toolType", FieldTypeEnum.Enumeration)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("bplnCode", FieldTypeEnum.String)
            .put("purchCode", FieldTypeEnum.String)
            .put("toolPrice", FieldTypeEnum.Double)
            .put("toolStkNum", FieldTypeEnum.Long)
            .put("toolAvlNum", FieldTypeEnum.Long)
            .put("toolPlnNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLeftSecond", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("produceTime", FieldTypeEnum.Timestamp)
            .put("expireTime", FieldTypeEnum.Timestamp)
            .put("toolMaker", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("prevInId", FieldTypeEnum.Long)
            .put("retestStatus", FieldTypeEnum.Enumeration)
            .put("retestId", FieldTypeEnum.Long)
            .put("shelfText", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.toolInService.findToolIns(params, fields);
  }

  @GetMapping("/list")
  public List<ToolIn> getToolInsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("inCode", FieldTypeEnum.String)
            .put("inCate", FieldTypeEnum.Enumeration)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("toolId", FieldTypeEnum.Long)
            .put("toolCode", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("toolName", FieldTypeEnum.String)
            .put("toolSpec", FieldTypeEnum.String)
            .put("toolType", FieldTypeEnum.Enumeration)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("bplnCode", FieldTypeEnum.String)
            .put("purchCode", FieldTypeEnum.String)
            .put("toolPrice", FieldTypeEnum.Double)
            .put("toolStkNum", FieldTypeEnum.Long)
            .put("toolAvlNum", FieldTypeEnum.Long)
            .put("toolPlnNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLeftSecond", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("produceTime", FieldTypeEnum.Timestamp)
            .put("expireTime", FieldTypeEnum.Timestamp)
            .put("toolMaker", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("prevInId", FieldTypeEnum.Long)
            .put("retestStatus", FieldTypeEnum.Enumeration)
            .put("retestId", FieldTypeEnum.Long)
            .put("shelfText", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.toolInService.findAllToolIns(params, fields);
  }

  @PostMapping("/bulk/read")
  public List<ToolIn> postToolInsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("inCodes", ParamTypeEnum.List).ensure();
    return this.toolInService.findAllToolIns(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public ToolIn postToolIns(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        // .require("inCode", ParamTypeEnum.String)
        .require("inCate", ParamTypeEnum.String)
        .require("inType", ParamTypeEnum.String)
        .require("toolId", ParamTypeEnum.Long)
        // .require("mtlBatch", ParamTypeEnum.String)
        .require("toolName", ParamTypeEnum.String)
        // .require("toolSpec", ParamTypeEnum.String)
        // .require("toolType", ParamTypeEnum.String)
        // .require("mtlMark", ParamTypeEnum.String)
        // .require("mtlStandard", ParamTypeEnum.String)
        // .require("bplnCode", ParamTypeEnum.String)
        // .require("purchCode", ParamTypeEnum.String)
        // .require("toolPrice", ParamTypeEnum.Double)
        .require("toolStkNum", ParamTypeEnum.Long)
        .require("toolAvlNum", ParamTypeEnum.Long)
        // .require("toolPlnNum", ParamTypeEnum.Long)
        .require("measure", ParamTypeEnum.String)
        // .require("serviceLife", ParamTypeEnum.Long)
        // .require("serviceLife2", ParamTypeEnum.Long)
        // .require("produceTime", ParamTypeEnum.Timestamp)
        // .require("expireTime", ParamTypeEnum.Timestamp)
        // .require("toolMaker", ParamTypeEnum.String)
        .require("inNC", ParamTypeEnum.String)
        .require("inDate", ParamTypeEnum.Timestamp)
        // .require("prevInId", ParamTypeEnum.Long)
        // .require("retestStatus", ParamTypeEnum.String)
        // .require("retestId", ParamTypeEnum.Long)
        // .require("shelfJson", ParamTypeEnum.String)
        .ensure();
    return this.toolInService.createToolIn(params);
  }

  @GetMapping("/{toolInId}")
  public ToolIn getToolIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolInId,
      @RequestParam Map<String, Object> params) {
    return this.toolInService.findById(toolInId);
  }

  @PutMapping("/{toolInId}")
  public ToolIn putToolIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolInId,
      @RequestBody Map<String, Object> params) {
    return this.toolInService.replaceToolIn(toolInId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{toolInId}")
  public void deleteToolIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolInId,
      @RequestParam Map<String, Object> params) {
    this.toolInService.deleteToolIn(toolInId);
  }
}
