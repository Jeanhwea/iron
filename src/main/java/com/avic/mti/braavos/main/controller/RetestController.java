package com.avic.mti.iron.main.controller;

import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.main.domain.entity.Retest;
import com.avic.mti.iron.main.service.RetestService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/materials/retests")
public class RetestController {

  public static final Logger logger = LoggerFactory.getLogger(RetestController.class);

  private final RetestService retestService;

  @Autowired
  public RetestController(RetestService retestService) {
    this.retestService = retestService;
  }

  @GetMapping("")
  public Page<Retest> getRetestsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("retestCode", FieldTypeEnum.String)
            .put("retestDeptCode", FieldTypeEnum.String)
            .put("retestDeptName", FieldTypeEnum.String)
            .put("retester", FieldTypeEnum.String)
            .put("retestTime", FieldTypeEnum.Timestamp)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("retestFilePage", FieldTypeEnum.Long)
            // .put("retestFilePath", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            .put("sheetCode", FieldTypeEnum.String)
            .put("resultText", FieldTypeEnum.String)
            .put("retestStandard", FieldTypeEnum.String)
            .put("retestNum01", FieldTypeEnum.Long)
            .put("retestUom01", FieldTypeEnum.String)
            .put("retestNum02", FieldTypeEnum.Long)
            .put("retestUom02", FieldTypeEnum.String)
            .put("var01Num", FieldTypeEnum.Long)
            .put("var02Num", FieldTypeEnum.Long)
            .put("var03Num", FieldTypeEnum.Long)
            .put("var04Num", FieldTypeEnum.Long)
            .put("var05Num", FieldTypeEnum.Long)
            .put("var01Str", FieldTypeEnum.String)
            .put("var02Str", FieldTypeEnum.String)
            .put("var03Str", FieldTypeEnum.String)
            .put("var04Str", FieldTypeEnum.String)
            .put("var05Str", FieldTypeEnum.String)
            // .put("retestItemJson", FieldTypeEnum.String)
            .fields();
    return this.retestService.findRetests(params, fields);
  }

  @GetMapping("/list")
  public List<Retest> getRetestsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("retestCode", FieldTypeEnum.String)
            .put("retestDeptCode", FieldTypeEnum.String)
            .put("retestDeptName", FieldTypeEnum.String)
            .put("retester", FieldTypeEnum.String)
            .put("retestTime", FieldTypeEnum.Timestamp)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("retestFilePage", FieldTypeEnum.Long)
            // .put("retestFilePath", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            .put("sheetCode", FieldTypeEnum.String)
            .put("resultText", FieldTypeEnum.String)
            .put("retestStandard", FieldTypeEnum.String)
            .put("retestNum01", FieldTypeEnum.Long)
            .put("retestUom01", FieldTypeEnum.String)
            .put("retestNum02", FieldTypeEnum.Long)
            .put("retestUom02", FieldTypeEnum.String)
            .put("var01Num", FieldTypeEnum.Long)
            .put("var02Num", FieldTypeEnum.Long)
            .put("var03Num", FieldTypeEnum.Long)
            .put("var04Num", FieldTypeEnum.Long)
            .put("var05Num", FieldTypeEnum.Long)
            .put("var01Str", FieldTypeEnum.String)
            .put("var02Str", FieldTypeEnum.String)
            .put("var03Str", FieldTypeEnum.String)
            .put("var04Str", FieldTypeEnum.String)
            .put("var05Str", FieldTypeEnum.String)
            // .put("retestItemJson", FieldTypeEnum.String)
            .fields();
    return this.retestService.findAllRetests(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public Retest postRetests(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("retestCode", ParamTypeEnum.String)
        // .require("retestDeptCode", ParamTypeEnum.String)
        // .require("retestDeptName", ParamTypeEnum.String)
        // .require("retester", ParamTypeEnum.String)
        // .require("retestTime", ParamTypeEnum.Timestamp)
        .require("creatorNC", ParamTypeEnum.String)
        .require("createDate", ParamTypeEnum.Timestamp)
        .require("retestFilePage", ParamTypeEnum.Long)
        // .require("retestFilePath", ParamTypeEnum.String)
        // .require("note", ParamTypeEnum.String)
        // .require("sheetCode", ParamTypeEnum.String)
        // .require("resultText", ParamTypeEnum.String)
        // .require("retestStandard", ParamTypeEnum.String)
        // .require("retestNum01", ParamTypeEnum.Long)
        // .require("retestUom01", ParamTypeEnum.String)
        // .require("retestNum02", ParamTypeEnum.Long)
        // .require("retestUom02", ParamTypeEnum.String)
        // .require("var01Num", ParamTypeEnum.Long)
        // .require("var02Num", ParamTypeEnum.Long)
        // .require("var03Num", ParamTypeEnum.Long)
        // .require("var04Num", ParamTypeEnum.Long)
        // .require("var05Num", ParamTypeEnum.Long)
        // .require("var01Str", ParamTypeEnum.String)
        // .require("var02Str", ParamTypeEnum.String)
        // .require("var03Str", ParamTypeEnum.String)
        // .require("var04Str", ParamTypeEnum.String)
        // .require("var05Str", ParamTypeEnum.String)
        .require("retestItemJson", ParamTypeEnum.String)
        .ensure();
    return this.retestService.createRetest(params);
  }

  @GetMapping("/{retestId}")
  public Retest getRetest(
      @RequestHeader HttpHeaders headers,
      @PathVariable long retestId,
      @RequestParam Map<String, Object> params) {
    return this.retestService.findById(retestId);
  }

  @PutMapping("/{retestId}")
  public Retest putRetest(
      @RequestHeader HttpHeaders headers,
      @PathVariable long retestId,
      @RequestBody Map<String, Object> params) {
    return this.retestService.replaceRetest(retestId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{retestId}")
  public void deleteRetest(
      @RequestHeader HttpHeaders headers,
      @PathVariable long retestId,
      @RequestParam Map<String, Object> params) {
    this.retestService.deleteRetest(retestId);
  }

  @GetMapping(value = "/{retestId}/file", produces = MediaType.APPLICATION_PDF_VALUE)
  public byte[] getRetestFile(
      @RequestHeader HttpHeaders headers,
      @PathVariable long retestId,
      @RequestParam Map<String, Object> params) {
    return this.retestService.getRetestFile(retestId);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping("/{retestId}/file")
  public void postRetestFile(
      @RequestHeader HttpHeaders headers,
      @PathVariable long retestId,
      @RequestParam(value = "file") MultipartFile multipartFile) {
    String updateUser = HeaderHelper.getUpdateUser(headers);
    this.retestService.setRetestFile(updateUser, retestId, multipartFile);
  }
}
