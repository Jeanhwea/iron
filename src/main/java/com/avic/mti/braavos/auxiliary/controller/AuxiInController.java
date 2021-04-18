package com.avic.mti.iron.auxiliary.controller;

import com.avic.mti.iron.auxiliary.domain.entity.AuxiIn;
import com.avic.mti.iron.auxiliary.service.AuxiInService;
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
@RequestMapping("/auxiliaries/in")
public class AuxiInController {

  public static final Logger logger = LoggerFactory.getLogger(AuxiInController.class);

  private final AuxiInService auxiInService;

  @Autowired
  public AuxiInController(AuxiInService auxiInService) {
    this.auxiInService = auxiInService;
  }

  @GetMapping("")
  public Page<AuxiIn> getAuxiInsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("inCode", FieldTypeEnum.String)
            .put("inCate", FieldTypeEnum.Enumeration)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("auxiId", FieldTypeEnum.Long)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("auxiName", FieldTypeEnum.String)
            .put("auxiSpec", FieldTypeEnum.String)
            .put("auxiType", FieldTypeEnum.Enumeration)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("bplnCode", FieldTypeEnum.String)
            .put("purchCode", FieldTypeEnum.String)
            .put("auxiPrice", FieldTypeEnum.Double)
            .put("auxiStkNum", FieldTypeEnum.Long)
            .put("auxiAvlNum", FieldTypeEnum.Long)
            .put("auxiPlnNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("produceTime", FieldTypeEnum.Timestamp)
            .put("expireTime", FieldTypeEnum.Timestamp)
            .put("auxiMaker", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("prevInId", FieldTypeEnum.Long)
            .put("retestStatus", FieldTypeEnum.Enumeration)
            .put("retestId", FieldTypeEnum.Long)
            .put("shelfText", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.auxiInService.findAuxiIns(params, fields);
  }

  @GetMapping("/list")
  public List<AuxiIn> getAuxiInsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("inCode", FieldTypeEnum.String)
            .put("inCate", FieldTypeEnum.Enumeration)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("auxiId", FieldTypeEnum.Long)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("auxiName", FieldTypeEnum.String)
            .put("auxiSpec", FieldTypeEnum.String)
            .put("auxiType", FieldTypeEnum.Enumeration)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("bplnCode", FieldTypeEnum.String)
            .put("purchCode", FieldTypeEnum.String)
            .put("auxiPrice", FieldTypeEnum.Double)
            .put("auxiStkNum", FieldTypeEnum.Long)
            .put("auxiAvlNum", FieldTypeEnum.Long)
            .put("auxiPlnNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("produceTime", FieldTypeEnum.Timestamp)
            .put("expireTime", FieldTypeEnum.Timestamp)
            .put("auxiMaker", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("prevInId", FieldTypeEnum.Long)
            .put("retestStatus", FieldTypeEnum.Enumeration)
            .put("retestId", FieldTypeEnum.Long)
            .put("shelfText", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.auxiInService.findAllAuxiIns(params, fields);
  }

  @PostMapping("/bulk/read")
  public List<AuxiIn> postAuxiInsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("inCodes", ParamTypeEnum.List).ensure();
    return this.auxiInService.findAllAuxiIns(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public AuxiIn postAuxiIns(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("inCode", ParamTypeEnum.String)
        // .require("inCate", ParamTypeEnum.String)
        // .require("inType", ParamTypeEnum.String)
        .require("auxiId", ParamTypeEnum.Long)
        // .require("mtlBatch", ParamTypeEnum.String)
        // .require("auxiName", ParamTypeEnum.String)
        // .require("auxiSpec", ParamTypeEnum.String)
        // .require("auxiType", ParamTypeEnum.String)
        // .require("mtlMark", ParamTypeEnum.String)
        // .require("mtlStandard", ParamTypeEnum.String)
        // .require("bplnCode", ParamTypeEnum.String)
        // .require("purchCode", ParamTypeEnum.String)
        // .require("auxiPrice", ParamTypeEnum.Double)
        .require("auxiStkNum", ParamTypeEnum.Long)
        .require("auxiAvlNum", ParamTypeEnum.Long)
        .require("auxiPlnNum", ParamTypeEnum.Long)
        // .require("measure", ParamTypeEnum.String)
        // .require("serviceLife", ParamTypeEnum.Long)
        // .require("serviceLife2", ParamTypeEnum.Long)
        // .require("produceTime", ParamTypeEnum.Timestamp)
        // .require("expireTime", ParamTypeEnum.Timestamp)
        // .require("auxiMaker", ParamTypeEnum.String)
        .require("inNC", ParamTypeEnum.String)
        .require("inDate", ParamTypeEnum.Timestamp)
        // .require("prevInId", ParamTypeEnum.Long)
        // .require("retestStatus", ParamTypeEnum.String)
        // .require("retestId", ParamTypeEnum.Long)
        // .require("shelfJson", ParamTypeEnum.String)
        .ensure();
    return this.auxiInService.createAuxiIn(params);
  }

  @GetMapping("/{auxiInId}")
  public AuxiIn getAuxiIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long auxiInId,
      @RequestParam Map<String, Object> params) {
    return this.auxiInService.findById(auxiInId);
  }

  @PutMapping("/{auxiInId}")
  public AuxiIn putAuxiIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long auxiInId,
      @RequestBody Map<String, Object> params) {
    return this.auxiInService.replaceAuxiIn(auxiInId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{auxiInId}")
  public void deleteAuxiIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long auxiInId,
      @RequestParam Map<String, Object> params) {
    this.auxiInService.deleteAuxiIn(auxiInId);
  }
}
