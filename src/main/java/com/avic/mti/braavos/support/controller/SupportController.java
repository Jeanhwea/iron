package com.avic.mti.iron.support.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.support.domain.entity.Support;
import com.avic.mti.iron.support.service.SupportService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supports")
public class SupportController {

  public static final Logger logger = LoggerFactory.getLogger(SupportController.class);

  private final SupportService supportService;

  @Autowired
  public SupportController(SupportService supportService) {
    this.supportService = supportService;
  }

  @GetMapping("")
  public Page<Support> getSupportsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("suptCode", FieldTypeEnum.String)
            .put("suptName", FieldTypeEnum.String)
            .put("suptCate", FieldTypeEnum.Enumeration)
            .put("suptSpec", FieldTypeEnum.String)
            .put("suptType", FieldTypeEnum.Enumeration)
            .put("suptDept", FieldTypeEnum.String)
            .put("suptUsage", FieldTypeEnum.String)
            .put("suptMaker", FieldTypeEnum.String)
            .put("measure", FieldTypeEnum.String)
            .put("mtlMark", FieldTypeEnum.String)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("inStub", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String )
            .fields();
    return this.supportService.findSupports(params, fields);
  }

  @GetMapping("/list")
  public List<Support> getSupportsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("suptCode", FieldTypeEnum.String)
            .put("suptName", FieldTypeEnum.String)
            .put("suptCate", FieldTypeEnum.Enumeration)
            .put("suptSpec", FieldTypeEnum.String)
            .put("suptType", FieldTypeEnum.Enumeration)
            .put("suptDept", FieldTypeEnum.String)
            .put("suptUsage", FieldTypeEnum.String)
            .put("suptMaker", FieldTypeEnum.String)
            .put("measure", FieldTypeEnum.String)
            .put("mtlMark", FieldTypeEnum.String)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("inStub", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String )
            .fields();
    return this.supportService.findAllSupports(params, fields);
  }

  @GetMapping("/idle")
  public List<Support> getIdleSupportsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.supportService.findIdleSupports();
  }

  @PostMapping("/bulk/read")
  public List<Support> postSupportsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("suptCodes", ParamTypeEnum.List).ensure();
    return this.supportService.findAllSupports(params);
  }

  @PostMapping("/bulk/read/idle")
  public List<Support> postIdleSupportsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("suptCodes", ParamTypeEnum.List).ensure();
    return this.supportService.findIdleSupports(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public Support postSupports(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("suptCode", ParamTypeEnum.String)
        .require("suptName", ParamTypeEnum.String)
        // .require("suptCate", ParamTypeEnum.String)
        // .require("suptSpec", ParamTypeEnum.String)
        // .require("suptType", ParamTypeEnum.String)
        // .require("suptDept", ParamTypeEnum.String)
        // .require("suptUsage", ParamTypeEnum.String)
        // .require("suptMaker", ParamTypeEnum.String)
        // .require("measure", ParamTypeEnum.String)
        // .require("mtlMark", ParamTypeEnum.String)
        // .require("inType", ParamTypeEnum.String)
        // .require("inStub", ParamTypeEnum.String)
        // .require("inNC", ParamTypeEnum.String)
        // .require("inDate", ParamTypeEnum.Timestamp)
        // .require("note", ParamTypeEnum.String)
        // .require("shelfJson", ParamTypeEnum.String)
        .ensure();
    return this.supportService.createSupport(params);
  }

  @GetMapping("/{supportId}")
  public Support getSupport(
      @RequestHeader HttpHeaders headers,
      @PathVariable long supportId,
      @RequestParam Map<String, Object> params) {
    return this.supportService.findById(supportId);
  }

  @PutMapping("/{supportId}")
  public Support putSupport(
      @RequestHeader HttpHeaders headers,
      @PathVariable long supportId,
      @RequestBody Map<String, Object> params) {
    return this.supportService.replaceSupport(supportId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{supportId}")
  public void deleteSupport(
      @RequestHeader HttpHeaders headers,
      @PathVariable long supportId,
      @RequestParam Map<String, Object> params) {
    this.supportService.deleteSupport(supportId);
  }
}
