package com.avic.mti.iron.main.controller;

import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.main.domain.entity.MainMaterial;
import com.avic.mti.iron.main.service.MainMaterialService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materials")
public class MainMaterialController {

  public static final Logger logger = LoggerFactory.getLogger(MainMaterialController.class);

  private final MainMaterialService mainMaterialService;

  @Autowired
  public MainMaterialController(MainMaterialService mainMaterialService) {
    this.mainMaterialService = mainMaterialService;
  }

  @GetMapping("")
  public Page<MainMaterial> getMainMaterialsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("mainCode", FieldTypeEnum.String)
            .put("mainName", FieldTypeEnum.String)
            .put("mainCate", FieldTypeEnum.Enumeration)
            .put("mainSpec", FieldTypeEnum.String)
            .put("mainType", FieldTypeEnum.Enumeration)
            .put("mainMaker", FieldTypeEnum.String)
            .put("mainDept", FieldTypeEnum.String)
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
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.mainMaterialService.findMainMaterials(params, fields);
  }

  @GetMapping("/list")
  public List<MainMaterial> getMainMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("mainCode", FieldTypeEnum.String)
            .put("mainName", FieldTypeEnum.String)
            .put("mainCate", FieldTypeEnum.Enumeration)
            .put("mainSpec", FieldTypeEnum.String)
            .put("mainType", FieldTypeEnum.Enumeration)
            .put("mainMaker", FieldTypeEnum.String)
            .put("mainDept", FieldTypeEnum.String)
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
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.mainMaterialService.findAllMainMaterials(params, fields);
  }

  @GetMapping("/idle")
  public List<MainMaterial> getIdleMainMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.mainMaterialService.findIdleMainMaterials();
  }

  @GetMapping("/idle2")
  public List<MainMaterial> getRuleIdleMainMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.mainMaterialService.findRuleIdleMainMaterials();
  }

  @PostMapping("/bulk/read")
  public List<MainMaterial> postMainMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("mainCodes", ParamTypeEnum.List).ensure();
    return this.mainMaterialService.findAllMainMaterials(params);
  }

  @PostMapping("/bulk/read/idle")
  public List<MainMaterial> postIdleMainMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("mainCodes", ParamTypeEnum.List).ensure();
    return this.mainMaterialService.findIdleMainMaterials(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public MainMaterial postMainMaterials(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("mainCode", ParamTypeEnum.String)
        .require("mainName", ParamTypeEnum.String)
        .require("mainCate", ParamTypeEnum.String)
        // .require("mainSpec", ParamTypeEnum.String)
        .require("mainType", ParamTypeEnum.String)
        // .require("mainMaker", ParamTypeEnum.String)
        // .require("mainDept", ParamTypeEnum.String)
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
        // .require("shelfJson", ParamTypeEnum.String)
        .ensure();
    return this.mainMaterialService.createMainMaterial(params);
  }

  @GetMapping("/{mainMaterialId}")
  public MainMaterial getMainMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainMaterialId,
      @RequestParam Map<String, Object> params) {
    return this.mainMaterialService.findById(mainMaterialId);
  }

  @PutMapping("/{mainMaterialId}")
  public MainMaterial putMainMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainMaterialId,
      @RequestBody Map<String, Object> params) {
    return this.mainMaterialService.replaceMainMaterial(mainMaterialId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{mainMaterialId}")
  public void deleteMainMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainMaterialId,
      @RequestParam Map<String, Object> params) {
    this.mainMaterialService.deleteMainMaterial(mainMaterialId);
  }
}
