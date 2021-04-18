package com.avic.mti.iron.ledger.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.ledger.domain.entity.LedgerStub;
import com.avic.mti.iron.ledger.service.LedgerStubService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ledgers/stubs")
public class LedgerStubController {

  public static final Logger logger = LoggerFactory.getLogger(LedgerStubController.class);

  private final LedgerStubService ledgerStubService;

  @Autowired
  public LedgerStubController(LedgerStubService ledgerStubService) {
    this.ledgerStubService = ledgerStubService;
  }

  @GetMapping("")
  public Page<LedgerStub> getLedgerStubsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("stubCode", FieldTypeEnum.String)
            .put("stubName", FieldTypeEnum.String)
            .put("stubStatus", FieldTypeEnum.Enumeration)
            .put("stubCate", FieldTypeEnum.Enumeration)
            .put("stubType", FieldTypeEnum.Enumeration)
            .put("stubFlag", FieldTypeEnum.Enumeration)
            .put("stubTag", FieldTypeEnum.Enumeration)
            .put("isOverflow", FieldTypeEnum.Boolean)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("finishOperNC", FieldTypeEnum.String)
            .put("finishDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            .fields();
    return this.ledgerStubService.findLedgerStubs(params, fields);
  }

  @GetMapping("/list")
  public List<LedgerStub> getLedgerStubsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("stubCode", FieldTypeEnum.String)
            .put("stubName", FieldTypeEnum.String)
            .put("stubStatus", FieldTypeEnum.Enumeration)
            .put("stubCate", FieldTypeEnum.Enumeration)
            .put("stubType", FieldTypeEnum.Enumeration)
            .put("stubFlag", FieldTypeEnum.Enumeration)
            .put("stubTag", FieldTypeEnum.Enumeration)
            .put("isOverflow", FieldTypeEnum.Boolean)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("finishOperNC", FieldTypeEnum.String)
            .put("finishDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            .fields();
    return this.ledgerStubService.findAllLedgerStubs(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public LedgerStub postLedgerStubs(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("stubCode", ParamTypeEnum.String)
        .require("stubName", ParamTypeEnum.String)
        .require("stubStatus", ParamTypeEnum.String)
        .require("stubCate", ParamTypeEnum.String)
        .require("stubType", ParamTypeEnum.String)
        .require("stubFlag", ParamTypeEnum.String)
        .require("stubTag", ParamTypeEnum.String)
        // .require("isOverflow", ParamTypeEnum.Boolean)
        .require("creatorNC", ParamTypeEnum.String)
        .require("createDate", ParamTypeEnum.Timestamp)
        // .require("finishOperNC", ParamTypeEnum.String)
        // .require("finishDate", ParamTypeEnum.Timestamp)
        // .require("note", ParamTypeEnum.String)
        .ensure();
    return this.ledgerStubService.createLedgerStub(params);
  }

  @GetMapping("/{ledgerStubId}")
  public LedgerStub getLedgerStub(
      @RequestHeader HttpHeaders headers,
      @PathVariable long ledgerStubId,
      @RequestParam Map<String, Object> params) {
    return this.ledgerStubService.findById(ledgerStubId);
  }

  @PutMapping("/{ledgerStubId}")
  public LedgerStub putLedgerStub(
      @RequestHeader HttpHeaders headers,
      @PathVariable long ledgerStubId,
      @RequestBody Map<String, Object> params) {
    return this.ledgerStubService.replaceLedgerStub(ledgerStubId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{ledgerStubId}")
  public void deleteLedgerStub(
      @RequestHeader HttpHeaders headers,
      @PathVariable long ledgerStubId,
      @RequestParam Map<String, Object> params) {
    this.ledgerStubService.deleteLedgerStub(ledgerStubId);
  }

  @PostMapping("/enable")
  public Map<String, Object> enableLedgerStubs(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("stubIds", ParamTypeEnum.List).ensure();
    return this.ledgerStubService.enableLedgerStubs(params);
  }

  @PostMapping("/disable")
  public Map<String, Object> disableLedgerStubs(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("stubIds", ParamTypeEnum.List).ensure();
    return this.ledgerStubService.disableLedgerStubs(params);
  }
}
