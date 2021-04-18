package com.avic.mti.iron.measure.controller;

import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.measure.domain.entity.MeasMaterial;
import com.avic.mti.iron.measure.service.MeasMaterialService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/measures")
public class MeasMaterialController {

  public static final Logger logger = LoggerFactory.getLogger(MeasMaterialController.class);

  private final MeasMaterialService measMaterialService;

  @Autowired
  public MeasMaterialController(MeasMaterialService measMaterialService) {
    this.measMaterialService = measMaterialService;
  }

  @GetMapping("")
  public Page<MeasMaterial> getMeasMaterialsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("measCode", FieldTypeEnum.String)
            .put("measName", FieldTypeEnum.String)
            .put("measCate", FieldTypeEnum.Enumeration)
            .put("measSpec", FieldTypeEnum.String)
            .put("measType", FieldTypeEnum.Enumeration)
            .put("measMaker", FieldTypeEnum.String)
            .put("measUsage", FieldTypeEnum.String)
            .put("measDept", FieldTypeEnum.String)
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
    return this.measMaterialService.findMeasMaterials(params, fields);
  }

  @GetMapping("/list")
  public List<MeasMaterial> getMeasMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("measCode", FieldTypeEnum.String)
            .put("measName", FieldTypeEnum.String)
            .put("measCate", FieldTypeEnum.Enumeration)
            .put("measSpec", FieldTypeEnum.String)
            .put("measType", FieldTypeEnum.Enumeration)
            .put("measMaker", FieldTypeEnum.String)
            .put("measUsage", FieldTypeEnum.String)
            .put("measDept", FieldTypeEnum.String)
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
    return this.measMaterialService.findAllMeasMaterials(params, fields);
  }

  @GetMapping("/idle")
  public List<MeasMaterial> getIdleMeasMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.measMaterialService.findIdleMeasMaterials();
  }

  @GetMapping("/idle2")
  public List<MeasMaterial> getRuleIdleMeasMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.measMaterialService.findRuleIdleMeasMaterials();
  }

  @PostMapping("/bulk/read")
  public List<MeasMaterial> postMeasMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("measCodes", ParamTypeEnum.List).ensure();
    return this.measMaterialService.findAllMeasMaterials(params);
  }

  @PostMapping("/bulk/read/idle")
  public List<MeasMaterial> postIdleMeasMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("measCodes", ParamTypeEnum.List).ensure();
    return this.measMaterialService.findIdleMeasMaterials(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public MeasMaterial postMeasMaterials(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("measCode", ParamTypeEnum.String)
        .require("measName", ParamTypeEnum.String)
        .require("measCate", ParamTypeEnum.String)
        // .require("measSpec", ParamTypeEnum.String)
        .require("measType", ParamTypeEnum.String)
        // .require("measMaker", ParamTypeEnum.String)
        // .require("measUsage", ParamTypeEnum.String)
        // .require("measDept", ParamTypeEnum.String)
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
    return this.measMaterialService.createMeasMaterial(params);
  }

  @GetMapping("/{measMaterialId}")
  public MeasMaterial getMeasMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measMaterialId,
      @RequestParam Map<String, Object> params) {
    return this.measMaterialService.findById(measMaterialId);
  }

  @PutMapping("/{measMaterialId}")
  public MeasMaterial putMeasMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measMaterialId,
      @RequestBody Map<String, Object> params) {
    return this.measMaterialService.replaceMeasMaterial(measMaterialId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{measMaterialId}")
  public void deleteMeasMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measMaterialId,
      @RequestParam Map<String, Object> params) {
    this.measMaterialService.deleteMeasMaterial(measMaterialId);
  }
}
