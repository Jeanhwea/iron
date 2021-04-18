package com.avic.mti.iron.waste.controller;

import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.waste.domain.entity.WprdMaterial;
import com.avic.mti.iron.waste.service.WprdMaterialService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waste/products")
public class WprdMaterialController {

  public static final Logger logger = LoggerFactory.getLogger(WprdMaterialController.class);

  private final WprdMaterialService wprdMaterialService;

  @Autowired
  public WprdMaterialController(WprdMaterialService wprdMaterialService) {
    this.wprdMaterialService = wprdMaterialService;
  }

  @GetMapping("")
  public Page<WprdMaterial> getWprdMaterialsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("wprdCode", FieldTypeEnum.String)
            .put("wprdName", FieldTypeEnum.String)
            .put("wprdCate", FieldTypeEnum.Enumeration)
            .put("wprdSpec", FieldTypeEnum.String)
            .put("wprdType", FieldTypeEnum.Enumeration)
            .put("wprdMaker", FieldTypeEnum.String)
            .put("wprdDept", FieldTypeEnum.String)
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
    return this.wprdMaterialService.findWprdMaterials(params, fields);
  }

  @GetMapping("/list")
  public List<WprdMaterial> getWprdMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("wprdCode", FieldTypeEnum.String)
            .put("wprdName", FieldTypeEnum.String)
            .put("wprdCate", FieldTypeEnum.Enumeration)
            .put("wprdSpec", FieldTypeEnum.String)
            .put("wprdType", FieldTypeEnum.Enumeration)
            .put("wprdMaker", FieldTypeEnum.String)
            .put("wprdDept", FieldTypeEnum.String)
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
    return this.wprdMaterialService.findAllWprdMaterials(params, fields);
  }

  @GetMapping("/idle")
  public List<WprdMaterial> getIdleWprdMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.wprdMaterialService.findIdleWprdMaterials();
  }

  @GetMapping("/idle2")
  public List<WprdMaterial> getRuleIdleWprdMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.wprdMaterialService.findRuleIdleWprdMaterials();
  }

  @PostMapping("/bulk/read")
  public List<WprdMaterial> postWprdMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("wprdCodes", ParamTypeEnum.List).ensure();
    return this.wprdMaterialService.findAllWprdMaterials(params);
  }

  @PostMapping("/bulk/read/idle")
  public List<WprdMaterial> postIdleWprdMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("wprdCodes", ParamTypeEnum.List).ensure();
    return this.wprdMaterialService.findIdleWprdMaterials(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public WprdMaterial postWprdMaterials(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("wprdCode", ParamTypeEnum.String)
        .require("wprdName", ParamTypeEnum.String)
        .require("wprdCate", ParamTypeEnum.String)
        // .require("wprdSpec", ParamTypeEnum.String)
        .require("wprdType", ParamTypeEnum.String)
        // .require("wprdMaker", ParamTypeEnum.String)
        // .require("wprdDept", ParamTypeEnum.String)
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
    return this.wprdMaterialService.createWprdMaterial(params);
  }

  @GetMapping("/{wprdMaterialId}")
  public WprdMaterial getWprdMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdMaterialId,
      @RequestParam Map<String, Object> params) {
    return this.wprdMaterialService.findById(wprdMaterialId);
  }

  @PutMapping("/{wprdMaterialId}")
  public WprdMaterial putWprdMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdMaterialId,
      @RequestBody Map<String, Object> params) {
    return this.wprdMaterialService.replaceWprdMaterial(wprdMaterialId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{wprdMaterialId}")
  public void deleteWprdMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdMaterialId,
      @RequestParam Map<String, Object> params) {
    this.wprdMaterialService.deleteWprdMaterial(wprdMaterialId);
  }
}
