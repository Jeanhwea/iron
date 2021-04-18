package com.avic.mti.iron.waste.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.service.WprdInService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waste/products/in")
public class WprdInController {

  public static final Logger logger = LoggerFactory.getLogger(WprdInController.class);

  private final WprdInService wprdInService;

  @Autowired
  public WprdInController(WprdInService wprdInService) {
    this.wprdInService = wprdInService;
  }

  @GetMapping("")
  public Page<WprdIn> getWprdInsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("inCode", FieldTypeEnum.String)
            .put("inCate", FieldTypeEnum.Enumeration)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("wprdId", FieldTypeEnum.Long)
            .put("wprdCode", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("wprdName", FieldTypeEnum.String)
            .put("wprdSpec", FieldTypeEnum.String)
            .put("wprdCate", FieldTypeEnum.Enumeration)
            .put("wprdType", FieldTypeEnum.Enumeration)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("bplnCode", FieldTypeEnum.String)
            .put("purchCode", FieldTypeEnum.String)
            .put("wprdPrice", FieldTypeEnum.Double)
            .put("wprdStkNum", FieldTypeEnum.Long)
            .put("wprdAvlNum", FieldTypeEnum.Long)
            .put("wprdPlnNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLeftSecond", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("produceTime", FieldTypeEnum.Timestamp)
            .put("expireTime", FieldTypeEnum.Timestamp)
            .put("wprdMaker", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("prevInId", FieldTypeEnum.Long)
            .put("note", FieldTypeEnum.Enumeration)
            .put("retestId", FieldTypeEnum.Long)
            .put("shelfText", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.wprdInService.findWprdIns(params, fields);
  }

  @GetMapping("/list")
  public List<WprdIn> getWprdInsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("inCode", FieldTypeEnum.String)
            .put("inCate", FieldTypeEnum.Enumeration)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("wprdId", FieldTypeEnum.Long)
            .put("wprdCode", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("wprdName", FieldTypeEnum.String)
            .put("wprdSpec", FieldTypeEnum.String)
            .put("wprdCate", FieldTypeEnum.Enumeration)
            .put("wprdType", FieldTypeEnum.Enumeration)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("bplnCode", FieldTypeEnum.String)
            .put("purchCode", FieldTypeEnum.String)
            .put("wprdPrice", FieldTypeEnum.Double)
            .put("wprdStkNum", FieldTypeEnum.Long)
            .put("wprdAvlNum", FieldTypeEnum.Long)
            .put("wprdPlnNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLeftSecond", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("produceTime", FieldTypeEnum.Timestamp)
            .put("expireTime", FieldTypeEnum.Timestamp)
            .put("wprdMaker", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("prevInId", FieldTypeEnum.Long)
            .put("note", FieldTypeEnum.Enumeration)
            .put("retestId", FieldTypeEnum.Long)
            .put("shelfText", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.wprdInService.findAllWprdIns(params, fields);
  }

  @PostMapping("/bulk/read")
  public List<WprdIn> postWprdInsList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("inCodes", ParamTypeEnum.List).ensure();
    return this.wprdInService.findAllWprdIns(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public WprdIn postWprdIns(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        // .require("inCode", ParamTypeEnum.String)
        .require("inCate", ParamTypeEnum.String)
        .require("inType", ParamTypeEnum.String)
        // .require("wprdId", ParamTypeEnum.Long)
        // .require("mtlBatch", ParamTypeEnum.String)
        .require("wprdName", ParamTypeEnum.String)
        // .require("wprdSpec", ParamTypeEnum.String)
        // .require("wprdCate", ParamTypeEnum.String)
        // .require("wprdType", ParamTypeEnum.String)
        // .require("mtlMark", ParamTypeEnum.String)
        // .require("mtlStandard", ParamTypeEnum.String)
        // .require("bplnCode", ParamTypeEnum.String)
        // .require("purchCode", ParamTypeEnum.String)
        // .require("wprdPrice", ParamTypeEnum.Double)
        .require("wprdStkNum", ParamTypeEnum.Long)
        .require("wprdAvlNum", ParamTypeEnum.Long)
        // .require("wprdPlnNum", ParamTypeEnum.Long)
        .require("measure", ParamTypeEnum.String)
        // .require("serviceLife", ParamTypeEnum.Long)
        // .require("serviceLife2", ParamTypeEnum.Long)
        // .require("produceTime", ParamTypeEnum.Timestamp)
        // .require("expireTime", ParamTypeEnum.Timestamp)
        // .require("wprdMaker", ParamTypeEnum.String)
        .require("inNC", ParamTypeEnum.String)
        .require("inDate", ParamTypeEnum.Timestamp)
        // .require("prevInId", ParamTypeEnum.Long)
        // .require("note", ParamTypeEnum.String)
        // .require("retestId", ParamTypeEnum.Long)
        // .require("shelfJson", ParamTypeEnum.String)
        .ensure();
    return this.wprdInService.createWprdIn(params);
  }

  @GetMapping("/{wprdInId}")
  public WprdIn getWprdIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdInId,
      @RequestParam Map<String, Object> params) {
    return this.wprdInService.findById(wprdInId);
  }

  @PutMapping("/{wprdInId}")
  public WprdIn putWprdIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdInId,
      @RequestBody Map<String, Object> params) {
    return this.wprdInService.replaceWprdIn(wprdInId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{wprdInId}")
  public void deleteWprdIn(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdInId,
      @RequestParam Map<String, Object> params) {
    this.wprdInService.deleteWprdIn(wprdInId);
  }
}
