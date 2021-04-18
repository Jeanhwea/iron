package com.avic.mti.iron.waste.controller;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import com.avic.mti.iron.waste.service.WprdOutService;
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
@RequestMapping("/waste/products/out")
public class WprdOutController {

  public static final Logger logger = LoggerFactory.getLogger(WprdOutController.class);

  private final WprdOutService wprdOutService;

  @Autowired
  public WprdOutController(WprdOutService wprdOutService) {
    this.wprdOutService = wprdOutService;
  }

  @GetMapping("")
  public Page<WprdOut> getWprdOutsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("outStatus", FieldTypeEnum.Enumeration)
            .put("outCate", FieldTypeEnum.Enumeration)
            .put("outType", FieldTypeEnum.Enumeration)
            .put("outFlag", FieldTypeEnum.Enumeration)
            .put("stubId", FieldTypeEnum.Long)
            .put("stubDetId", FieldTypeEnum.Long)
            .put("planEnum", FieldTypeEnum.Long)
            .put("planFnum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("inId", FieldTypeEnum.Long)
            .put("wprdId", FieldTypeEnum.Long)
            .put("wprdCate", FieldTypeEnum.String)
            .put("wprdType", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("produceTime", FieldTypeEnum.Timestamp)
            .put("expireTime", FieldTypeEnum.Timestamp)
            .put("projCode", FieldTypeEnum.String)
            .put("taskCode", FieldTypeEnum.String)
            .put("brgnCode", FieldTypeEnum.String)
            .put("outReason", FieldTypeEnum.String)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("finishOperNC", FieldTypeEnum.String)
            .put("finishDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            .put("shelfText", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.wprdOutService.findWprdOuts(params, fields);
  }

  @GetMapping("/list")
  public List<WprdOut> getWprdOutsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("outStatus", FieldTypeEnum.Enumeration)
            .put("outCate", FieldTypeEnum.Enumeration)
            .put("outType", FieldTypeEnum.Enumeration)
            .put("outFlag", FieldTypeEnum.Enumeration)
            .put("stubId", FieldTypeEnum.Long)
            .put("stubDetId", FieldTypeEnum.Long)
            .put("planEnum", FieldTypeEnum.Long)
            .put("planFnum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
            .put("inId", FieldTypeEnum.Long)
            .put("wprdId", FieldTypeEnum.Long)
            .put("wprdCate", FieldTypeEnum.String)
            .put("wprdType", FieldTypeEnum.String)
            .put("serviceLife", FieldTypeEnum.Long)
            .put("serviceLife2", FieldTypeEnum.Long)
            .put("produceTime", FieldTypeEnum.Timestamp)
            .put("expireTime", FieldTypeEnum.Timestamp)
            .put("projCode", FieldTypeEnum.String)
            .put("taskCode", FieldTypeEnum.String)
            .put("brgnCode", FieldTypeEnum.String)
            .put("outReason", FieldTypeEnum.String)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("finishOperNC", FieldTypeEnum.String)
            .put("finishDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            .put("shelfText", FieldTypeEnum.String)
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.wprdOutService.findAllWprdOuts(params, fields);
  }

  @GetMapping("/{wprdOutId}")
  public WprdOut getWprdOut(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdOutId,
      @RequestParam Map<String, Object> params) {
    return this.wprdOutService.findById(wprdOutId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/bulk/create")
  public List<WprdOut> postWprdOutsInBulk(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        // .require("stubId", ParamTypeEnum.Long)
        // .require("stubDetId", ParamTypeEnum.Long)
        .require("wprdOutList", ParamTypeEnum.List)
        .ensure();
    ParamReader globalReader = ParamReader.init(params);
    List<Map<String, Object>> wprdOutList =
        globalReader
            .listObjFromKey("wprdOutList")
            .orElseThrow(() -> new BadRequestException("缺少 wprdOutList 参数"));
    // Long stubId =
    //     globalReader
    //         .longFromKey("stubId")
    //         .orElseThrow(() -> new BadRequestException("缺少 stubId 参数"));
    // Long stubDetId =
    //     globalReader
    //         .longFromKey("stubDetId")
    //         .orElseThrow(() -> new BadRequestException("缺少 stubDetId 参数"));
    String updateUser =
        globalReader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少 updateUser 参数"));

    List<Map<String, Object>> paramList = new LinkedList<>();
    for (Map<String, Object> p : wprdOutList) {
      Map<String, Object> wprdOutParam =
          ParamBuilder.init(p)
              .put("updateUser", updateUser)
              // .put("stubId", stubId)
              // .put("stubDetId", stubDetId)
              .params();

      ParamChecker.init(wprdOutParam)
          .require("outStatus", ParamTypeEnum.String)
          .require("outCate", ParamTypeEnum.String)
          .require("outType", ParamTypeEnum.String)
          .require("outFlag", ParamTypeEnum.String)
          // .require("stubId", ParamTypeEnum.Long)
          // .require("stubDetId", ParamTypeEnum.Long)
          .require("planEnum", ParamTypeEnum.Long)
          // .require("planFnum", ParamTypeEnum.Long)
          .require("measure", ParamTypeEnum.String)
          // .require("mtlBatch", ParamTypeEnum.String)
          .require("inId", ParamTypeEnum.Long)
          // .require("wprdId", ParamTypeEnum.Long)
          // .require("wprdCate", ParamTypeEnum.String )
          // .require("wprdType", ParamTypeEnum.String )
          // .require("serviceLife", ParamTypeEnum.Long)
          // .require("SERVICE_LIFE2", ParamTypeEnum.Long)
          // .require("produceTime", ParamTypeEnum.Timestamp)
          // .require("expireTime", ParamTypeEnum.Timestamp)
          // .require("projCode", ParamTypeEnum.String)
          // .require("taskCode", ParamTypeEnum.String)
          // .require("brgnCode", ParamTypeEnum.String)
          // .require("outReason", ParamTypeEnum.String)
          .require("creatorNC", ParamTypeEnum.String)
          .require("createDate", ParamTypeEnum.Timestamp)
          .require("finishOperNC", ParamTypeEnum.String)
          .require("finishDate", ParamTypeEnum.Timestamp)
          // .require("note", ParamTypeEnum.String)
          // .require("shelfJson", ParamTypeEnum.String)
          .ensure();

      paramList.add(wprdOutParam);
    }

    return this.wprdOutService.createWprdOutInBulk(paramList);
  }
}
