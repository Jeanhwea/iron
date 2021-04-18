package com.avic.mti.iron.auxiliary.controller;

import com.avic.mti.iron.auxiliary.domain.entity.AuxiMaterial;
import com.avic.mti.iron.auxiliary.service.AuxiMaterialService;
import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auxiliaries")
public class AuxiMaterialController {

  public static final Logger logger = LoggerFactory.getLogger(AuxiMaterialController.class);

  private final AuxiMaterialService auxiMaterialService;

  @Autowired
  public AuxiMaterialController(AuxiMaterialService auxiMaterialService) {
    this.auxiMaterialService = auxiMaterialService;
  }

  @GetMapping("")
  public Page<AuxiMaterial> getAuxiMaterialsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("auxiCode", FieldTypeEnum.String)
            .put("auxiName", FieldTypeEnum.String)
            .put("auxiCate", FieldTypeEnum.Enumeration)
            .put("auxiSpec", FieldTypeEnum.String)
            .put("auxiType", FieldTypeEnum.Enumeration)
            .put("auxiMaker", FieldTypeEnum.String)
            .put("auxiDept", FieldTypeEnum.String)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("measure", FieldTypeEnum.String)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("inStub", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.auxiMaterialService.findAuxiMaterials(params, fields);
  }

  @GetMapping("/list")
  public List<AuxiMaterial> getAuxiMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("auxiCode", FieldTypeEnum.String)
            .put("auxiName", FieldTypeEnum.String)
            .put("auxiCate", FieldTypeEnum.Enumeration)
            .put("auxiSpec", FieldTypeEnum.String)
            .put("auxiType", FieldTypeEnum.Enumeration)
            .put("auxiMaker", FieldTypeEnum.String)
            .put("auxiDept", FieldTypeEnum.String)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("measure", FieldTypeEnum.String)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("inStub", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.auxiMaterialService.findAllAuxiMaterials(params, fields);
  }

  @GetMapping("/idle")
  public List<AuxiMaterial> getIdleAuxiMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.auxiMaterialService.findIdleAuxiMaterials();
  }

  @PostMapping("/bulk/read")
  public List<AuxiMaterial> postAuxiMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("auxiCodes", ParamTypeEnum.List).ensure();
    return this.auxiMaterialService.findAllAuxiMaterials(params);
  }

  @PostMapping("/bulk/read/idle")
  public List<AuxiMaterial> postIdleAuxiMaterialsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("auxiCodes", ParamTypeEnum.List).ensure();
    return this.auxiMaterialService.findIdleAuxiMaterials(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public AuxiMaterial postAuxiMaterials(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("auxiCode", ParamTypeEnum.String)
        .require("auxiName", ParamTypeEnum.String)
        // .require("auxiCate", ParamTypeEnum.String)
        // .require("auxiSpec", ParamTypeEnum.String)
        // .require("auxiType", ParamTypeEnum.String)
        // .require("auxiMaker", ParamTypeEnum.String)
        // .require("auxiDept", ParamTypeEnum.String)
        // .require("mtlMark", ParamTypeEnum.String)
        // .require("mtlStandard", ParamTypeEnum.String)
        // .require("measure", ParamTypeEnum.String)
        // .require("inType", ParamTypeEnum.String)
        // .require("inStub", ParamTypeEnum.String)
        // .require("inNC", ParamTypeEnum.String)
        // .require("inDate", ParamTypeEnum.Timestamp)
        // .require("note", ParamTypeEnum.String)
        // .require("shelfJson", ParamTypeEnum.String)
        .ensure();
    return this.auxiMaterialService.createAuxiMaterial(params);
  }

  @GetMapping("/{auxiMaterialId}")
  public AuxiMaterial getAuxiMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long auxiMaterialId,
      @RequestParam Map<String, Object> params) {
    return this.auxiMaterialService.findById(auxiMaterialId);
  }

  @PutMapping("/{auxiMaterialId}")
  public AuxiMaterial putAuxiMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long auxiMaterialId,
      @RequestBody Map<String, Object> params) {
    return this.auxiMaterialService.replaceAuxiMaterial(auxiMaterialId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{auxiMaterialId}")
  public void deleteAuxiMaterial(
      @RequestHeader HttpHeaders headers,
      @PathVariable long auxiMaterialId,
      @RequestParam Map<String, Object> params) {
    this.auxiMaterialService.deleteAuxiMaterial(auxiMaterialId);
  }
}
