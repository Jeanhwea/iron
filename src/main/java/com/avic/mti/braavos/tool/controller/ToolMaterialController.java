package com.avic.mti.iron.tool.controller;

import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.tool.domain.entity.ToolMaterial;
import com.avic.mti.iron.tool.service.ToolMaterialService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tools")
public class ToolMaterialController {

  public static final Logger logger = LoggerFactory.getLogger(ToolMaterialController.class);

  private final ToolMaterialService toolMaterialService;

  @Autowired
  public ToolMaterialController(ToolMaterialService toolMaterialService) {
    this.toolMaterialService = toolMaterialService;
  }

  @GetMapping("")
  public Page<ToolMaterial> getToolMaterialsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("toolCode", FieldTypeEnum.String)
            .put("toolName", FieldTypeEnum.String)
            .put("toolCate", FieldTypeEnum.Enumeration)
            .put("toolSpec", FieldTypeEnum.String)
            .put("toolType", FieldTypeEnum.Enumeration)
            .put("toolMaker", FieldTypeEnum.String)
            .put("toolDept", FieldTypeEnum.String)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("measure", FieldTypeEnum.String)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("inStub", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("minStkNum", FieldTypeEnum.Long)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("note", FieldTypeEnum.String)
            .put("shelfText", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.toolMaterialService.findToolMaterials(params, fields);
  }

  @GetMapping("/list")
  public List<ToolMaterial> getToolMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("toolCode", FieldTypeEnum.String)
            .put("toolName", FieldTypeEnum.String)
            .put("toolCate", FieldTypeEnum.Enumeration)
            .put("toolSpec", FieldTypeEnum.String)
            .put("toolType", FieldTypeEnum.Enumeration)
            .put("toolMaker", FieldTypeEnum.String)
            .put("toolDept", FieldTypeEnum.String)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("measure", FieldTypeEnum.String)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("inStub", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("minStkNum", FieldTypeEnum.Long)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("note", FieldTypeEnum.String)
            .put("shelfText", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.toolMaterialService.findAllToolMaterials(params, fields);
  }

  @GetMapping("/idle")
  public List<ToolMaterial> getIdleToolMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.toolMaterialService.findIdleToolMaterials();
  }

  @GetMapping("/idle2")
  public List<ToolMaterial> getRuleIdleToolMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.toolMaterialService.findRuleIdleToolMaterials();
  }

  @PostMapping("/bulk/read")
  public List<ToolMaterial> postToolMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("toolCodes", ParamTypeEnum.List).ensure();
    return this.toolMaterialService.findAllToolMaterials(params);
  }

  @PostMapping("/bulk/read/idle")
  public List<ToolMaterial> postIdleToolMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("toolCodes", ParamTypeEnum.List).ensure();
    return this.toolMaterialService.findIdleToolMaterials(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public ToolMaterial postToolMaterials(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("toolCode", ParamTypeEnum.String)
        .require("toolName", ParamTypeEnum.String)
        .require("toolCate", ParamTypeEnum.String)
        // .require("toolSpec", ParamTypeEnum.String)
        .require("toolType", ParamTypeEnum.String)
        // .require("toolMaker", ParamTypeEnum.String)
        // .require("toolDept", ParamTypeEnum.String)
        // .require("mtlMark", ParamTypeEnum.String)
        // .require("mtlStandard", ParamTypeEnum.String)
        // .require("measure", ParamTypeEnum.String)
        .require("inType", ParamTypeEnum.String)
        // .require("inStub", ParamTypeEnum.String)
        .require("inNC", ParamTypeEnum.String)
        .require("inDate", ParamTypeEnum.Timestamp)
        // .require("minStkNum", ParamTypeEnum.Long)
        // .require("serviceLife)", ParamTypeEnum.Long)
        // .require("serviceLife2)", ParamTypeEnum.Long)
        // .require("note", ParamTypeEnum.String)
        // .require("shelfText", ParamTypeEnum.String)
        // .require("shelfJson", ParamTypeEnum.String)
        .ensure();
    return this.toolMaterialService.createToolMaterial(params);
  }

  @GetMapping("/{toolMaterialId}")
  public ToolMaterial getToolMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolMaterialId,
      @RequestParam Map<String, Object> params) {
    return this.toolMaterialService.findById(toolMaterialId);
  }

  @PutMapping("/{toolMaterialId}")
  public ToolMaterial putToolMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolMaterialId,
      @RequestBody Map<String, Object> params) {
    return this.toolMaterialService.replaceToolMaterial(toolMaterialId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{toolMaterialId}")
  public void deleteToolMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolMaterialId,
      @RequestParam Map<String, Object> params) {
    this.toolMaterialService.deleteToolMaterial(toolMaterialId);
  }
}
