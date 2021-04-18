package com.avic.mti.iron.mould.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.mould.domain.entity.Mould;
import com.avic.mti.iron.mould.service.MouldService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moulds")
public class MouldController {

  public static final Logger logger = LoggerFactory.getLogger(MouldController.class);

  private final MouldService mouldService;

  @Autowired
  public MouldController(MouldService mouldService) {
    this.mouldService = mouldService;
  }

  @GetMapping("")
  public Page<Mould> getMouldsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("moldCode", FieldTypeEnum.String)
            .put("moldName", FieldTypeEnum.String)
            .put("moldCate", FieldTypeEnum.Enumeration)
            .put("moldSpec", FieldTypeEnum.String)
            .put("moldDept", FieldTypeEnum.String)
            .put("moldAdmin", FieldTypeEnum.String)
            .put("moldMaker", FieldTypeEnum.String)
            .put("moldStatus", FieldTypeEnum.String)
            .put("blueprintNo", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Timestamp)
            .put("moldSize", FieldTypeEnum.String)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("inStub", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String )
            .fields();
    return this.mouldService.findMoulds(params, fields);
  }

  @GetMapping("/list")
  public List<Mould> getMouldsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("moldCode", FieldTypeEnum.String)
            .put("moldName", FieldTypeEnum.String)
            .put("moldCate", FieldTypeEnum.Enumeration)
            .put("moldSpec", FieldTypeEnum.String)
            .put("moldDept", FieldTypeEnum.String)
            .put("moldAdmin", FieldTypeEnum.String)
            .put("moldMaker", FieldTypeEnum.String)
            .put("moldStatus", FieldTypeEnum.String)
            .put("blueprintNo", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Timestamp)
            .put("moldSize", FieldTypeEnum.String)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("inStub", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String )
            .fields();
    return this.mouldService.findAllMoulds(params, fields);
  }

  @GetMapping("/idle")
  public List<Mould> getIdleMouldsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.mouldService.findIdleMoulds();
  }

  @PostMapping("/bulk/read")
  public List<Mould> postMouldsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("moldCodes", ParamTypeEnum.List).ensure();
    return this.mouldService.findAllMoulds(params);
  }

  @PostMapping("/bulk/read/idle")
  public List<Mould> postIdleMouldsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("moldCodes", ParamTypeEnum.List).ensure();
    return this.mouldService.findIdleMoulds(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public Mould postMoulds(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("moldCode", ParamTypeEnum.String)
        .require("moldName", ParamTypeEnum.String)
        // .require("moldCate", ParamTypeEnum.String)
        // .require("moldSpec", ParamTypeEnum.String)
        // .require("moldDept", ParamTypeEnum.String)
        // .require("moldAdmin", ParamTypeEnum.String)
        // .require("moldMaker", ParamTypeEnum.String)
        // .require("moldStatus", ParamTypeEnum.String)
        // .require("blueprintNo", ParamTypeEnum.String)
        // .require("serviceLife", ParamTypeEnum.Timestamp)
        // .require("moldSize", ParamTypeEnum.String)
        // .require("inType", ParamTypeEnum.String)
        // .require("inStub", ParamTypeEnum.String)
        // .require("inNC", ParamTypeEnum.String)
        // .require("inDate", ParamTypeEnum.Timestamp)
        // .require("note", ParamTypeEnum.String)
        // .require("shelfJson", ParamTypeEnum.String)
        .ensure();
    return this.mouldService.createMould(params);
  }

  @GetMapping("/{mouldId}")
  public Mould getMould(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mouldId,
      @RequestParam Map<String, Object> params) {
    return this.mouldService.findById(mouldId);
  }

  @PutMapping("/{mouldId}")
  public Mould putMould(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mouldId,
      @RequestBody Map<String, Object> params) {
    return this.mouldService.replaceMould(mouldId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{mouldId}")
  public void deleteMould(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mouldId,
      @RequestParam Map<String, Object> params) {
    this.mouldService.deleteMould(mouldId);
  }
}
