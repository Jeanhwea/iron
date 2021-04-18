package com.avic.mti.iron.measure.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
import com.avic.mti.iron.measure.service.MeasInService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/measures/in")
public class MeasInController {

  public static final Logger logger = LoggerFactory.getLogger(MeasInController.class);

  private final MeasInService measInService;

  @Autowired
  public MeasInController(MeasInService measInService) {
    this.measInService = measInService;
  }

  @GetMapping("")
  public Page<MeasIn> getMeasInsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("inCode", FieldTypeEnum.String)
            .put("inCate", FieldTypeEnum.Enumeration)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("measId", FieldTypeEnum.Long)
            .put("measCode", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("measName", FieldTypeEnum.String)
            .put("measSpec", FieldTypeEnum.String)
            .put("measUsage", FieldTypeEnum.String)
            .put("measType", FieldTypeEnum.Enumeration)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("bplnCode", FieldTypeEnum.String)
            .put("purchCode", FieldTypeEnum.String)
            .put("measPrice", FieldTypeEnum.Double)
            .put("measStkNum", FieldTypeEnum.Long)
            .put("measAvlNum", FieldTypeEnum.Long)
            .put("measPlnNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLeftSecond", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("produceTime", FieldTypeEnum.Timestamp)
            .put("expireTime", FieldTypeEnum.Timestamp)
            .put("isExpired", FieldTypeEnum.Boolean)
            .put("measMaker", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("prevInId", FieldTypeEnum.Long)
            .put("retestStatus", FieldTypeEnum.Enumeration)
            .put("retestId", FieldTypeEnum.Long)
            .put("shelfText", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.measInService.findMeasIns(params, fields);
  }

  @GetMapping("/list")
  public List<MeasIn> getMeasInsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("inCode", FieldTypeEnum.String)
            .put("inCate", FieldTypeEnum.Enumeration)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("measId", FieldTypeEnum.Long)
            .put("measCode", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("measName", FieldTypeEnum.String)
            .put("measSpec", FieldTypeEnum.String)
            .put("measUsage", FieldTypeEnum.String)
            .put("measType", FieldTypeEnum.Enumeration)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("bplnCode", FieldTypeEnum.String)
            .put("purchCode", FieldTypeEnum.String)
            .put("measPrice", FieldTypeEnum.Double)
            .put("measStkNum", FieldTypeEnum.Long)
            .put("measAvlNum", FieldTypeEnum.Long)
            .put("measPlnNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLeftSecond", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("produceTime", FieldTypeEnum.Timestamp)
            .put("expireTime", FieldTypeEnum.Timestamp)
            .put("isExpired", FieldTypeEnum.Boolean)
            .put("measMaker", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("prevInId", FieldTypeEnum.Long)
            .put("retestStatus", FieldTypeEnum.Enumeration)
            .put("retestId", FieldTypeEnum.Long)
            .put("shelfText", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.measInService.findAllMeasIns(params, fields);
  }

  @PostMapping("/bulk/read")
  public List<MeasIn> postMeasInsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("inCodes", ParamTypeEnum.List).ensure();
    return this.measInService.findAllMeasIns(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public MeasIn postMeasIns(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        // .require("inCode", ParamTypeEnum.String)
        .require("inCate", ParamTypeEnum.String)
        .require("inType", ParamTypeEnum.String)
        .require("measId", ParamTypeEnum.Long)
        // .require("mtlBatch", ParamTypeEnum.String)
        .require("measName", ParamTypeEnum.String)
        // .require("measSpec", ParamTypeEnum.String)
        // .require("measUsage", ParamTypeEnum.String)
        // .require("measType", ParamTypeEnum.String)
        // .require("mtlMark", ParamTypeEnum.String)
        // .require("mtlStandard", ParamTypeEnum.String)
        // .require("bplnCode", ParamTypeEnum.String)
        // .require("purchCode", ParamTypeEnum.String)
        // .require("measPrice", ParamTypeEnum.Double)
        .require("measStkNum", ParamTypeEnum.Long)
        .require("measAvlNum", ParamTypeEnum.Long)
        // .require("measPlnNum", ParamTypeEnum.Long)
        .require("measure", ParamTypeEnum.String)
        // .require("serviceLife", ParamTypeEnum.Long)
        // .require("serviceLife2", ParamTypeEnum.Long)
        // .require("produceTime", ParamTypeEnum.Timestamp)
        // .require("expireTime", ParamTypeEnum.Timestamp)
        // .require("measMaker", ParamTypeEnum.String)
        .require("inNC", ParamTypeEnum.String)
        .require("inDate", ParamTypeEnum.Timestamp)
        // .require("prevInId", ParamTypeEnum.Long)
        // .require("retestStatus", ParamTypeEnum.String)
        // .require("retestId", ParamTypeEnum.Long)
        // .require("shelfJson", ParamTypeEnum.String)
        .ensure();
    return this.measInService.createMeasIn(params);
  }

  @GetMapping("/{measInId}")
  public MeasIn getMeasIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measInId,
      @RequestParam Map<String, Object> params) {
    return this.measInService.findById(measInId);
  }

  @PutMapping("/{measInId}")
  public MeasIn putMeasIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measInId,
      @RequestBody Map<String, Object> params) {
    return this.measInService.replaceMeasIn(measInId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{measInId}")
  public void deleteMeasIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measInId,
      @RequestParam Map<String, Object> params) {
    this.measInService.deleteMeasIn(measInId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/return")
  public MeasIn returnMeasIns(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        // .require("inCode", ParamTypeEnum.String)
        .require("inCate", ParamTypeEnum.String)
        .require("inType", ParamTypeEnum.String)
        .require("measId", ParamTypeEnum.Long)
        // .require("mtlBatch", ParamTypeEnum.String)
        .require("measName", ParamTypeEnum.String)
        // .require("measSpec", ParamTypeEnum.String)
        // .require("measUsage", ParamTypeEnum.String)
        // .require("measType", ParamTypeEnum.String)
        // .require("mtlMark", ParamTypeEnum.String)
        // .require("mtlStandard", ParamTypeEnum.String)
        // .require("bplnCode", ParamTypeEnum.String)
        // .require("purchCode", ParamTypeEnum.String)
        // .require("measPrice", ParamTypeEnum.Double)
        .require("measStkNum", ParamTypeEnum.Long)
        .require("measAvlNum", ParamTypeEnum.Long)
        // .require("measPlnNum", ParamTypeEnum.Long)
        .require("measure", ParamTypeEnum.String)
        // .require("serviceLife", ParamTypeEnum.Long)
        // .require("serviceLife2", ParamTypeEnum.Long)
        // .require("produceTime", ParamTypeEnum.Timestamp)
        // .require("expireTime", ParamTypeEnum.Timestamp)
        // .require("measMaker", ParamTypeEnum.String)
        .require("inNC", ParamTypeEnum.String)
        .require("inDate", ParamTypeEnum.Timestamp)
        // .require("prevInId", ParamTypeEnum.Long)
        // .require("retestStatus", ParamTypeEnum.String)
        // .require("retestId", ParamTypeEnum.Long)
        // .require("shelfJson", ParamTypeEnum.String)
        .require("outId", ParamTypeEnum.Long)
        .ensure();
    return this.measInService.returnMeasIn(params);
  }
}
