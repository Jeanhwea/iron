package com.avic.mti.iron.main.controller;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.service.MainInService;
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
@RequestMapping("/materials/in")
public class MainInController {

  public static final Logger logger = LoggerFactory.getLogger(MainInController.class);

  private final MainInService mainInService;

  @Autowired
  public MainInController(MainInService mainInService) {
    this.mainInService = mainInService;
  }

  @GetMapping("")
  public Page<MainIn> getMainInsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("inCode", FieldTypeEnum.String)
            .put("inCate", FieldTypeEnum.Enumeration)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("mainId", FieldTypeEnum.Long)
            .put("mainCode", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("mainName", FieldTypeEnum.String)
            .put("mainSpec", FieldTypeEnum.String)
            .put("mainType", FieldTypeEnum.Enumeration)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("bplnCode", FieldTypeEnum.String)
            .put("purchCode", FieldTypeEnum.String)
            .put("mainPrice", FieldTypeEnum.Double)
            .put("mainStkNum", FieldTypeEnum.Long)
            .put("mainAvlNum", FieldTypeEnum.Long)
            .put("mainOpcNum", FieldTypeEnum.Long)
            .put("mainPlnNum", FieldTypeEnum.Long)
            .put("mainCsmNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLeftSecond", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("produceTime", FieldTypeEnum.Timestamp)
            .put("expireTime", FieldTypeEnum.Timestamp)
            .put("mainMaker", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("prevInId", FieldTypeEnum.Long)
            .put("retestStatus", FieldTypeEnum.Enumeration)
            .put("retestId", FieldTypeEnum.Long)
            // .put("shelfJson", FieldTypeEnum.String)
            .put("mainVar01Num", FieldTypeEnum.Double)
            .put("mainVar02Num", FieldTypeEnum.Double)
            .put("mainVar03Num", FieldTypeEnum.Long)
            .put("mainVar04Num", FieldTypeEnum.Long)
            .put("mainVar01Str", FieldTypeEnum.String)
            .put("mainVar02Str", FieldTypeEnum.String)
            .put("mainVar03Str", FieldTypeEnum.String)
            .put("mainVar04Str", FieldTypeEnum.String)
            .put("mainVar05Str", FieldTypeEnum.String)
            .put("mainVar06Str", FieldTypeEnum.String)
            .put("mainVar07Str", FieldTypeEnum.String)
            .put("mainVar08Str", FieldTypeEnum.String)
            .fields();
    return this.mainInService.findMainIns(params, fields);
  }

  @GetMapping("/list")
  public List<MainIn> getMainInsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("inCode", FieldTypeEnum.String)
            .put("inCate", FieldTypeEnum.Enumeration)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("mainId", FieldTypeEnum.Long)
            .put("mainCode", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("mainName", FieldTypeEnum.String)
            .put("mainSpec", FieldTypeEnum.String)
            .put("mainType", FieldTypeEnum.Enumeration)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("bplnCode", FieldTypeEnum.String)
            .put("purchCode", FieldTypeEnum.String)
            .put("mainPrice", FieldTypeEnum.Double)
            .put("mainStkNum", FieldTypeEnum.Long)
            .put("mainAvlNum", FieldTypeEnum.Long)
            .put("mainOpcNum", FieldTypeEnum.Long)
            .put("mainPlnNum", FieldTypeEnum.Long)
            .put("mainCsmNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLeftSecond", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("produceTime", FieldTypeEnum.Timestamp)
            .put("expireTime", FieldTypeEnum.Timestamp)
            .put("mainMaker", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("prevInId", FieldTypeEnum.Long)
            .put("retestStatus", FieldTypeEnum.Enumeration)
            .put("retestId", FieldTypeEnum.Long)
            // .put("shelfJson", FieldTypeEnum.String)
            .put("mainVar01Num", FieldTypeEnum.Double)
            .put("mainVar02Num", FieldTypeEnum.Double)
            .put("mainVar03Num", FieldTypeEnum.Long)
            .put("mainVar04Num", FieldTypeEnum.Long)
            .put("mainVar01Str", FieldTypeEnum.String)
            .put("mainVar02Str", FieldTypeEnum.String)
            .put("mainVar03Str", FieldTypeEnum.String)
            .put("mainVar04Str", FieldTypeEnum.String)
            .put("mainVar05Str", FieldTypeEnum.String)
            .put("mainVar06Str", FieldTypeEnum.String)
            .put("mainVar07Str", FieldTypeEnum.String)
            .put("mainVar08Str", FieldTypeEnum.String)
            .fields();
    return this.mainInService.findAllMainIns(params, fields);
  }

  @PostMapping("/bulk/read")
  public List<MainIn> postMainInsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("inCodes", ParamTypeEnum.List).ensure();
    return this.mainInService.findAllMainIns(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public MainIn postMainIns(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        // .require("inCode", ParamTypeEnum.String)
        .require("inCate", ParamTypeEnum.String)
        .require("inType", ParamTypeEnum.String)
        .require("mainId", ParamTypeEnum.Long)
        .require("mtlBatch", ParamTypeEnum.String)
        .require("mainName", ParamTypeEnum.String)
        // .require("mainSpec", ParamTypeEnum.String)
        // .require("mainType", ParamTypeEnum.String)
        // .require("mtlMark", ParamTypeEnum.String)
        // .require("mtlStandard", ParamTypeEnum.String)
        // .require("bplnCode", ParamTypeEnum.String)
        // .require("purchCode", ParamTypeEnum.String)
        .require("mainPrice", ParamTypeEnum.Double)
        .require("mainStkNum", ParamTypeEnum.Long)
        .require("mainAvlNum", ParamTypeEnum.Long)
        // .require("mainPlnNum", ParamTypeEnum.Long)
        .require("measure", ParamTypeEnum.String)
        // .require("serviceLife", ParamTypeEnum.Long)
        // .require("serviceLife2", ParamTypeEnum.Long)
        // .require("produceTime", ParamTypeEnum.Timestamp)
        // .require("expireTime", ParamTypeEnum.Timestamp)
        // .require("mainMaker", ParamTypeEnum.String)
        .require("inNC", ParamTypeEnum.String)
        .require("inDate", ParamTypeEnum.Timestamp)
        // .require("prevInId", ParamTypeEnum.Long)
        .require("retestStatus", ParamTypeEnum.String)
        // .require("retestId", ParamTypeEnum.Long)
        .require("shelfJson", ParamTypeEnum.String)
        .ensure();
    return this.mainInService.createMainIn(params);
  }

  @GetMapping("/{mainInId}")
  public MainIn getMainIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainInId,
      @RequestParam Map<String, Object> params) {
    return this.mainInService.findById(mainInId);
  }

  @PutMapping("/{mainInId}")
  public MainIn putMainIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainInId,
      @RequestBody Map<String, Object> params) {
    return this.mainInService.replaceMainIn(mainInId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{mainInId}")
  public void deleteMainIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainInId,
      @RequestParam Map<String, Object> params) {
    this.mainInService.deleteMainIn(mainInId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/bulk/create")
  public List<MainIn> postMainInsInBulk(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("mainInList", ParamTypeEnum.List).ensure();

    ParamReader globalReader = ParamReader.init(params);
    String updateUser =
        globalReader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少 updateUser 参数"));
    List<Map<String, Object>> mainInList =
        globalReader
            .listObjFromKey("mainInList")
            .orElseThrow(() -> new BadRequestException("缺少 mainInList 参数"));

    List<Map<String, Object>> paramList = new LinkedList<>();
    for (Map<String, Object> p : mainInList) {
      Map<String, Object> detailParam = ParamBuilder.init(p).put("updateUser", updateUser).params();

      ParamChecker.init(detailParam)
          // .require("inCode", ParamTypeEnum.String)
          .require("inCate", ParamTypeEnum.String)
          .require("inType", ParamTypeEnum.String)
          .require("mainId", ParamTypeEnum.Long)
          .require("mtlBatch", ParamTypeEnum.String)
          .require("mainName", ParamTypeEnum.String)
          // .require("mainSpec", ParamTypeEnum.String)
          // .require("mainType", ParamTypeEnum.String)
          // .require("mtlMark", ParamTypeEnum.String)
          // .require("mtlStandard", ParamTypeEnum.String)
          // .require("bplnCode", ParamTypeEnum.String)
          // .require("purchCode", ParamTypeEnum.String)
          .require("mainPrice", ParamTypeEnum.Double)
          .require("mainStkNum", ParamTypeEnum.Long)
          .require("mainAvlNum", ParamTypeEnum.Long)
          // .require("mainPlnNum", ParamTypeEnum.Long)
          .require("measure", ParamTypeEnum.String)
          // .require("serviceLife", ParamTypeEnum.Long)
          // .require("serviceLife2", ParamTypeEnum.Long)
          // .require("produceTime", ParamTypeEnum.Timestamp)
          // .require("expireTime", ParamTypeEnum.Timestamp)
          // .require("mainMaker", ParamTypeEnum.String)
          .require("inNC", ParamTypeEnum.String)
          .require("inDate", ParamTypeEnum.Timestamp)
          // .require("prevInId", ParamTypeEnum.Long)
          .require("retestStatus", ParamTypeEnum.String)
          // .require("retestId", ParamTypeEnum.Long)
          .require("shelfJson", ParamTypeEnum.String)
          .ensure();

      paramList.add(detailParam);
    }

    return this.mainInService.createMainInsInBulk(paramList);
  }
}
