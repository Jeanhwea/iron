package com.avic.mti.iron.main.controller;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.main.domain.entity.RetestDetail;
import com.avic.mti.iron.main.service.RetestDetailService;
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
@RequestMapping("/materials/retests/details")
public class RetestDetailController {

  public static final Logger logger = LoggerFactory.getLogger(RetestDetailController.class);

  private final RetestDetailService retestDetailService;

  @Autowired
  public RetestDetailController(RetestDetailService retestDetailService) {
    this.retestDetailService = retestDetailService;
  }

  @GetMapping("")
  public Page<RetestDetail> getRetestDetailsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("retestId", FieldTypeEnum.Long)
            .put("inId", FieldTypeEnum.Long)
            .put("inCode", FieldTypeEnum.String)
            .put("mainId", FieldTypeEnum.Long)
            .put("mainCode", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("mainType", FieldTypeEnum.Enumeration)
            .put("mainSpec", FieldTypeEnum.String)
            .put("mtlMark", FieldTypeEnum.String)
            .put("smplCode", FieldTypeEnum.String)
            .put("smplName", FieldTypeEnum.String)
            .put("retestStatus", FieldTypeEnum.Enumeration)
            .put("retestNum01", FieldTypeEnum.Long)
            .put("retestUom01", FieldTypeEnum.String)
            .put("retestNum02", FieldTypeEnum.Long)
            .put("retestUom02", FieldTypeEnum.String)
            .put("retestVar01Num", FieldTypeEnum.Long)
            .put("retestVar02Num", FieldTypeEnum.Long)
            .put("retestVar03Num", FieldTypeEnum.Long)
            .put("retestVar04Num", FieldTypeEnum.Long)
            .put("retestVar05Num", FieldTypeEnum.Long)
            .put("retestVar01Str", FieldTypeEnum.String)
            .put("retestVar02Str", FieldTypeEnum.String)
            .put("retestVar03Str", FieldTypeEnum.String)
            .put("retestVar04Str", FieldTypeEnum.String)
            .put("retestVar05Str", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .fields();
    return this.retestDetailService.findRetestDetails(params, fields);
  }

  @GetMapping("/list")
  public List<RetestDetail> getRetestDetailsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("retestId", FieldTypeEnum.Long)
            .put("inId", FieldTypeEnum.Long)
            .put("inCode", FieldTypeEnum.String)
            .put("mainId", FieldTypeEnum.Long)
            .put("mainCode", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("mainType", FieldTypeEnum.Enumeration)
            .put("mainSpec", FieldTypeEnum.String)
            .put("mtlMark", FieldTypeEnum.String)
            .put("smplCode", FieldTypeEnum.String)
            .put("smplName", FieldTypeEnum.String)
            .put("retestStatus", FieldTypeEnum.Enumeration)
            .put("retestNum01", FieldTypeEnum.Long)
            .put("retestUom01", FieldTypeEnum.String)
            .put("retestNum02", FieldTypeEnum.Long)
            .put("retestUom02", FieldTypeEnum.String)
            .put("retestVar01Num", FieldTypeEnum.Long)
            .put("retestVar02Num", FieldTypeEnum.Long)
            .put("retestVar03Num", FieldTypeEnum.Long)
            .put("retestVar04Num", FieldTypeEnum.Long)
            .put("retestVar05Num", FieldTypeEnum.Long)
            .put("retestVar01Str", FieldTypeEnum.String)
            .put("retestVar02Str", FieldTypeEnum.String)
            .put("retestVar03Str", FieldTypeEnum.String)
            .put("retestVar04Str", FieldTypeEnum.String)
            .put("retestVar05Str", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .fields();
    return this.retestDetailService.findAllRetestDetails(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public RetestDetail postRetestDetails(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("retestId", ParamTypeEnum.Long)
        .require("inId", ParamTypeEnum.Long)
        .require("inCode", ParamTypeEnum.String)
        .require("mainId", ParamTypeEnum.Long)
        .require("mainCode", ParamTypeEnum.String)
        // .require("mtlBatch", ParamTypeEnum.String)
        // .require("mainType", ParamTypeEnum.String)
        // .require("mainSpec", ParamTypeEnum.String)
        // .require("mtlMark", ParamTypeEnum.String)
        .require("smplCode", ParamTypeEnum.String)
        .require("smplName", ParamTypeEnum.String)
        .require("retestStatus", ParamTypeEnum.String)
        // .require("retestNum01", ParamTypeEnum.Long)
        // .require("retestUom01", ParamTypeEnum.String)
        // .require("retestNum02", ParamTypeEnum.Long)
        // .require("retestUom02", ParamTypeEnum.String)
        // .require("retestVar01Num", ParamTypeEnum.Long)
        // .require("retestVar02Num", ParamTypeEnum.Long)
        // .require("retestVar03Num", ParamTypeEnum.Long)
        // .require("retestVar04Num", ParamTypeEnum.Long)
        // .require("retestVar05Num", ParamTypeEnum.Long)
        // .require("retestVar01Str", ParamTypeEnum.String)
        // .require("retestVar02Str", ParamTypeEnum.String)
        // .require("retestVar03Str", ParamTypeEnum.String)
        // .require("retestVar04Str", ParamTypeEnum.String)
        // .require("retestVar05Str", ParamTypeEnum.String)
        // .require("note", ParamTypeEnum.String)
        .require("creatorNC", ParamTypeEnum.String)
        .require("createDate", ParamTypeEnum.Timestamp)
        .ensure();
    return this.retestDetailService.createRetestDetail(params);
  }

  @GetMapping("/{retestDetailId}")
  public RetestDetail getRetestDetail(
      @RequestHeader HttpHeaders headers,
      @PathVariable long retestDetailId,
      @RequestParam Map<String, Object> params) {
    return this.retestDetailService.findById(retestDetailId);
  }

  @PutMapping("/{retestDetailId}")
  public RetestDetail putRetestDetail(
      @RequestHeader HttpHeaders headers,
      @PathVariable long retestDetailId,
      @RequestBody Map<String, Object> params) {
    return this.retestDetailService.replaceRetestDetail(retestDetailId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{retestDetailId}")
  public void deleteRetestDetail(
      @RequestHeader HttpHeaders headers,
      @PathVariable long retestDetailId,
      @RequestParam Map<String, Object> params) {
    this.retestDetailService.deleteRetestDetail(retestDetailId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/bulk/create")
  public List<RetestDetail> postRetestDetailsInBulk(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("retestId", ParamTypeEnum.Long)
        .require("detailList", ParamTypeEnum.List)
        .ensure();
    ParamReader globalReader = ParamReader.init(params);
    List<Map<String, Object>> detailList =
        globalReader
            .listObjFromKey("detailList")
            .orElseThrow(() -> new BadRequestException("缺少 detailList 参数"));
    Long retestId =
        globalReader
            .longFromKey("retestId")
            .orElseThrow(() -> new BadRequestException("缺少 retestId 参数"));
    String updateUser =
        globalReader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少 updateUser 参数"));

    List<Map<String, Object>> paramList = new LinkedList<>();
    for (Map<String, Object> p : detailList) {
      Map<String, Object> detailParam =
          ParamBuilder.init(p).put("updateUser", updateUser).put("retestId", retestId).params();

      ParamChecker.init(detailParam)
          // .require("retestId", ParamTypeEnum.Long)
          .require("inId", ParamTypeEnum.Long)
          .require("inCode", ParamTypeEnum.String)
          .require("mainId", ParamTypeEnum.Long)
          .require("mainCode", ParamTypeEnum.String)
          // .require("mtlBatch", ParamTypeEnum.String)
          // .require("mainType", ParamTypeEnum.String)
          // .require("mainSpec", ParamTypeEnum.String)
          // .require("mtlMark", ParamTypeEnum.String)
          .require("smplCode", ParamTypeEnum.String)
          .require("smplName", ParamTypeEnum.String)
          .require("retestStatus", ParamTypeEnum.String)
          // .require("retestNum01", ParamTypeEnum.Long)
          // .require("retestUom01", ParamTypeEnum.String)
          // .require("retestNum02", ParamTypeEnum.Long)
          // .require("retestUom02", ParamTypeEnum.String)
          // .require("retestVar01Num", ParamTypeEnum.Long)
          // .require("retestVar02Num", ParamTypeEnum.Long)
          // .require("retestVar03Num", ParamTypeEnum.Long)
          // .require("retestVar04Num", ParamTypeEnum.Long)
          // .require("retestVar05Num", ParamTypeEnum.Long)
          // .require("retestVar01Str", ParamTypeEnum.String)
          // .require("retestVar02Str", ParamTypeEnum.String)
          // .require("retestVar03Str", ParamTypeEnum.String)
          // .require("retestVar04Str", ParamTypeEnum.String)
          // .require("retestVar05Str", ParamTypeEnum.String)
          // .require("note", ParamTypeEnum.String)
          .require("creatorNC", ParamTypeEnum.String)
          .require("createDate", ParamTypeEnum.Timestamp)
          .ensure();

      paramList.add(detailParam);
    }

    return this.retestDetailService.createRetestDetailInBulk(paramList);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PostMapping("/bulk/remove")
  public void removeRetestDetailsInBulk(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("retestId", ParamTypeEnum.Long).ensure();
    this.retestDetailService.removeRetestDetailInBulk(params);
  }
}
