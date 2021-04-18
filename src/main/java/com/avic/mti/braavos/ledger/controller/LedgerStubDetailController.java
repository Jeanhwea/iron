package com.avic.mti.iron.ledger.controller;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.ledger.domain.entity.LedgerStubDetail;
import com.avic.mti.iron.ledger.service.LedgerStubDetailService;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ledgers/stubs/details")
public class LedgerStubDetailController {

  public static final Logger logger = LoggerFactory.getLogger(LedgerStubDetailController.class);

  private final LedgerStubDetailService ledgerStubDetailService;

  @Autowired
  public LedgerStubDetailController(LedgerStubDetailService ledgerStubDetailService) {
    this.ledgerStubDetailService = ledgerStubDetailService;
  }

  @GetMapping("")
  public Page<LedgerStubDetail> getLedgerStubDetailsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("stubId", FieldTypeEnum.Long)
            .put("stubCode", FieldTypeEnum.String)
            .put("stubDetStatus", FieldTypeEnum.Enumeration)
            .put("stubDetCate", FieldTypeEnum.Enumeration)
            .put("stubDetType", FieldTypeEnum.Enumeration)
            .put("mtlCode", FieldTypeEnum.String)
            .put("mtlName", FieldTypeEnum.String)
            .put("mtlType", FieldTypeEnum.String)
            .put("mtlSpec", FieldTypeEnum.String)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("inId", FieldTypeEnum.Long)
            .put("qdNum", FieldTypeEnum.Long)
            .put("outNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("finishOperNC", FieldTypeEnum.String)
            .put("finishDate", FieldTypeEnum.Timestamp)
            .put("isOverflow", FieldTypeEnum.Boolean)
            .put("taskCode", FieldTypeEnum.String)
            .put("brgnCode", FieldTypeEnum.String)
            .put("projCode", FieldTypeEnum.String)
            .put("outReason", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            .fields();
    return this.ledgerStubDetailService.findLedgerStubDetails(params, fields);
  }

  @GetMapping("/list")
  public List<LedgerStubDetail> getLedgerStubDetailsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("stubId", FieldTypeEnum.Long)
            .put("stubCode", FieldTypeEnum.String)
            .put("stubDetStatus", FieldTypeEnum.Enumeration)
            .put("stubDetCate", FieldTypeEnum.Enumeration)
            .put("stubDetType", FieldTypeEnum.Enumeration)
            .put("mtlCode", FieldTypeEnum.String)
            .put("mtlName", FieldTypeEnum.String)
            .put("mtlType", FieldTypeEnum.String)
            .put("mtlSpec", FieldTypeEnum.String)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("inId", FieldTypeEnum.Long)
            .put("qdNum", FieldTypeEnum.Long)
            .put("outNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("finishOperNC", FieldTypeEnum.String)
            .put("finishDate", FieldTypeEnum.Timestamp)
            .put("isOverflow", FieldTypeEnum.Boolean)
            .put("taskCode", FieldTypeEnum.String)
            .put("brgnCode", FieldTypeEnum.String)
            .put("projCode", FieldTypeEnum.String)
            .put("outReason", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            .fields();
    return this.ledgerStubDetailService.findAllLedgerStubDetails(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public LedgerStubDetail postLedgerStubDetails(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("stubId", ParamTypeEnum.Long)
        .require("stubCode", ParamTypeEnum.String)
        .require("stubDetStatus", ParamTypeEnum.String)
        .require("stubDetCate", ParamTypeEnum.String)
        .require("stubDetType", ParamTypeEnum.String)
        // .require("mtlCode", ParamTypeEnum.String)
        // .require("mtlName", ParamTypeEnum.String)
        // .require("mtlType", ParamTypeEnum.String)
        // .require("mtlSpec", ParamTypeEnum.String)
        // .require("mtlMark", ParamTypeEnum.String)
        // .require("mtlBatch", ParamTypeEnum.String)
        // .require("inId", ParamTypeEnum.Long)
        .require("qdNum", ParamTypeEnum.Long)
        .require("outNum", ParamTypeEnum.Long)
        // .require("measure", ParamTypeEnum.String)
        .require("creatorNC", ParamTypeEnum.String)
        .require("createDate", ParamTypeEnum.Timestamp)
        // .require("finishOperNC", ParamTypeEnum.String)
        // .require("finishDate", ParamTypeEnum.Timestamp)
        // .require("isOverflow", ParamTypeEnum.Boolean)
        // .require("taskCode", ParamTypeEnum.String)
        // .require("brgnCode", ParamTypeEnum.String)
        // .require("projCode", ParamTypeEnum.String)
        // .require("outReason", ParamTypeEnum.String)
        // .require("note", ParamTypeEnum.String)
        .ensure();
    return this.ledgerStubDetailService.createLedgerStubDetail(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/bulk/create")
  public List<LedgerStubDetail> postLedgerStubDetailsInBulk(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("stubId", ParamTypeEnum.Long)
        .require("stubCode", ParamTypeEnum.String)
        .require("detailList", ParamTypeEnum.List)
        .ensure();
    ParamReader globalReader = ParamReader.init(params);
    List<Map<String, Object>> detailList =
        globalReader
            .listObjFromKey("detailList")
            .orElseThrow(() -> new BadRequestException("缺少 detailList 参数"));
    Long stubId =
        globalReader
            .longFromKey("stubId")
            .orElseThrow(() -> new BadRequestException("缺少 stubId 参数"));
    String stubCode =
        globalReader
            .stringFromKey("stubCode")
            .orElseThrow(() -> new BadRequestException("缺少 stubCode 参数"));
    String updateUser =
        globalReader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少 updateUser 参数"));

    List<Map<String, Object>> paramList = new LinkedList<>();
    for (Map<String, Object> p : detailList) {
      Map<String, Object> stubDetailParam =
          ParamBuilder.init(p)
              .put("updateUser", updateUser)
              .put("stubId", stubId)
              .put("stubCode", stubCode)
              .params();

      ParamChecker.init(stubDetailParam)
          // .require("stubId", ParamTypeEnum.Long)
          // .require("stubCode", ParamTypeEnum.String)
          .require("stubDetStatus", ParamTypeEnum.String)
          .require("stubDetCate", ParamTypeEnum.String)
          .require("stubDetType", ParamTypeEnum.String)
          // .require("mtlCode", ParamTypeEnum.String)
          // .require("mtlName", ParamTypeEnum.String)
          // .require("mtlType", ParamTypeEnum.String)
          // .require("mtlSpec", ParamTypeEnum.String)
          // .require("mtlMark", ParamTypeEnum.String)
          // .require("mtlBatch", ParamTypeEnum.String)
          // .require("inId", ParamTypeEnum.Long)
          .require("qdNum", ParamTypeEnum.Long)
          .require("outNum", ParamTypeEnum.Long)
          // .require("measure", ParamTypeEnum.String)
          .require("creatorNC", ParamTypeEnum.String)
          .require("createDate", ParamTypeEnum.Timestamp)
          // .require("finishOperNC", ParamTypeEnum.String)
          // .require("finishDate", ParamTypeEnum.Timestamp)
          // .require("isOverflow", ParamTypeEnum.Boolean)
          // .require("taskCode", ParamTypeEnum.String)
          // .require("brgnCode", ParamTypeEnum.String)
          // .require("projCode", ParamTypeEnum.String)
          // .require("outReason", ParamTypeEnum.String)
          // .require("note", ParamTypeEnum.String)
          .ensure();

      paramList.add(stubDetailParam);
    }

    return this.ledgerStubDetailService.createLedgerStubDetailInBulk(paramList);
  }

  @GetMapping("/{ledgerStubId}")
  public LedgerStubDetail getLedgerStubDetail(
      @RequestHeader HttpHeaders headers,
      @PathVariable long ledgerStubId,
      @RequestParam Map<String, Object> params) {
    return this.ledgerStubDetailService.findById(ledgerStubId);
  }

  @PutMapping("/{ledgerStubId}")
  public LedgerStubDetail putLedgerStubDetail(
      @RequestHeader HttpHeaders headers,
      @PathVariable long ledgerStubId,
      @RequestBody Map<String, Object> params) {
    return this.ledgerStubDetailService.replaceLedgerStubDetail(ledgerStubId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{ledgerStubId}")
  public void deleteLedgerStubDetail(
      @RequestHeader HttpHeaders headers,
      @PathVariable long ledgerStubId,
      @RequestParam Map<String, Object> params) {
    this.ledgerStubDetailService.deleteLedgerStubDetail(ledgerStubId);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PostMapping("/bulk/remove")
  public void deleteLedgerStubDetailsInBulk(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("stubDetIds", ParamTypeEnum.List).ensure();
    this.ledgerStubDetailService.deleteLedgerStubDetailInBulk(params);
  }
}
