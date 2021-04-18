package com.avic.mti.iron.measure.controller;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import com.avic.mti.iron.measure.service.MeasOutService;
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
@RequestMapping("/measures/out")
public class MeasOutController {

  public static final Logger logger = LoggerFactory.getLogger(MeasOutController.class);

  private final MeasOutService measOutService;

  @Autowired
  public MeasOutController(MeasOutService measOutService) {
    this.measOutService = measOutService;
  }

  @GetMapping("")
  public Page<MeasOut> getMeasOutsPage(
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
            .put("measId", FieldTypeEnum.Long)
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
    return this.measOutService.findMeasOuts(params, fields);
  }

  @GetMapping("/list")
  public List<MeasOut> getMeasOutsList(
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
            .put("measId", FieldTypeEnum.Long)
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
    return this.measOutService.findAllMeasOuts(params, fields);
  }

  @GetMapping("/{measOutId}")
  public MeasOut getMeasOut(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measOutId,
      @RequestParam Map<String, Object> params) {
    return this.measOutService.findById(measOutId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/bulk/create")
  public List<MeasOut> postMeasOutsInBulk(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        // .require("stubId", ParamTypeEnum.Long)
        // .require("stubDetId", ParamTypeEnum.Long)
        .require("measOutList", ParamTypeEnum.List)
        .ensure();
    ParamReader globalReader = ParamReader.init(params);
    List<Map<String, Object>> measOutList =
        globalReader
            .listObjFromKey("measOutList")
            .orElseThrow(() -> new BadRequestException("缺少 measOutList 参数"));
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
    for (Map<String, Object> p : measOutList) {
      Map<String, Object> measOutParam =
          ParamBuilder.init(p)
              .put("updateUser", updateUser)
              // .put("stubId", stubId)
              // .put("stubDetId", stubDetId)
              .params();

      ParamChecker.init(measOutParam)
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
          .require("measId", ParamTypeEnum.Long)
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
          // .require("finishOperNC", ParamTypeEnum.String)
          // .require("finishDate", ParamTypeEnum.Timestamp)
          // .require("note", ParamTypeEnum.String)
          // .require("shelfJson", ParamTypeEnum.String)
          .ensure();

      paramList.add(measOutParam);
    }

    return this.measOutService.createMeasOutInBulk(paramList);
  }
}
