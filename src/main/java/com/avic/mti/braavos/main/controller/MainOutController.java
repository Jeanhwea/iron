package com.avic.mti.iron.main.controller;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.main.domain.entity.MainOut;
import com.avic.mti.iron.main.service.MainOutService;
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
@RequestMapping("/materials/out")
public class MainOutController {

  public static final Logger logger = LoggerFactory.getLogger(MainOutController.class);

  private final MainOutService mainOutService;

  @Autowired
  public MainOutController(MainOutService mainOutService) {
    this.mainOutService = mainOutService;
  }

  @GetMapping("")
  public Page<MainOut> getMainOutsPage(
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
            .put("mainId", FieldTypeEnum.Long)
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
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.mainOutService.findMainOuts(params, fields);
  }

  @GetMapping("/list")
  public List<MainOut> getMainOutsList(
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
            .put("mainId", FieldTypeEnum.Long)
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
            // .put("shelfJson", FieldTypeEnum.String)
            .fields();
    return this.mainOutService.findAllMainOuts(params, fields);
  }

  @GetMapping("/{mainOutId}")
  public MainOut getMainOut(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainOutId,
      @RequestParam Map<String, Object> params) {
    return this.mainOutService.findById(mainOutId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/bulk/create")
  public List<MainOut> postMainOutsInBulk(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("stubId", ParamTypeEnum.Long)
        .require("stubDetId", ParamTypeEnum.Long)
        .require("mainOutList", ParamTypeEnum.List)
        .ensure();
    ParamReader globalReader = ParamReader.init(params);
    List<Map<String, Object>> mainOutList =
        globalReader
            .listObjFromKey("mainOutList")
            .orElseThrow(() -> new BadRequestException("缺少 mainOutList 参数"));
    Long stubId =
        globalReader
            .longFromKey("stubId")
            .orElseThrow(() -> new BadRequestException("缺少 stubId 参数"));
    Long stubDetId =
        globalReader
            .longFromKey("stubDetId")
            .orElseThrow(() -> new BadRequestException("缺少 stubDetId 参数"));
    String updateUser =
        globalReader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少 updateUser 参数"));

    List<Map<String, Object>> paramList = new LinkedList<>();
    for (Map<String, Object> p : mainOutList) {
      Map<String, Object> mainOutParam =
          ParamBuilder.init(p)
              .put("updateUser", updateUser)
              .put("stubId", stubId)
              .put("stubDetId", stubDetId)
              .params();

      ParamChecker.init(mainOutParam)
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
          .require("mainId", ParamTypeEnum.Long)
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
          .require("shelfJson", ParamTypeEnum.String)
          .ensure();

      paramList.add(mainOutParam);
    }

    return this.mainOutService.createMainOutInBulk(paramList);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/bulk/create2")
  public List<MainOut> postMainOutsInBulk2(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        // .require("stubId", ParamTypeEnum.Long)
        // .require("stubDetId", ParamTypeEnum.Long)
        .require("mainOutList", ParamTypeEnum.List)
        .ensure();
    ParamReader globalReader = ParamReader.init(params);
    List<Map<String, Object>> mainOutList =
        globalReader
            .listObjFromKey("mainOutList")
            .orElseThrow(() -> new BadRequestException("缺少 mainOutList 参数"));
    String updateUser =
        globalReader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少 updateUser 参数"));

    List<Map<String, Object>> paramList = new LinkedList<>();
    for (Map<String, Object> p : mainOutList) {
      Map<String, Object> mainOutParam =
          ParamBuilder.init(p).put("updateUser", updateUser).params();

      ParamChecker.init(mainOutParam)
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
          .require("mainId", ParamTypeEnum.Long)
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
          .require("shelfJson", ParamTypeEnum.String)
          .ensure();

      paramList.add(mainOutParam);
    }

    return this.mainOutService.createMainOutInBulk2(paramList);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/bulk/transfer")
  public List<MainOut> transferMainOutsInBulk(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        // .require("stubId", ParamTypeEnum.Long)
        // .require("stubDetId", ParamTypeEnum.Long)
        .require("mainOutList", ParamTypeEnum.List)
        .require("newMainIn", ParamTypeEnum.Object)
        .ensure();
    ParamReader globalReader = ParamReader.init(params);
    List<Map<String, Object>> mainOutList =
        globalReader
            .listObjFromKey("mainOutList")
            .orElseThrow(() -> new BadRequestException("缺少 mainOutList 参数"));
    String updateUser =
        globalReader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少 updateUser 参数"));
    Map<String, Object> newMainInParam =
        globalReader
            .objFromKey("newMainIn")
            .orElseThrow(() -> new BadRequestException("缺少 newMainIn 参数"));

    List<Map<String, Object>> paramOutList = new LinkedList<>();
    for (Map<String, Object> p : mainOutList) {
      Map<String, Object> mainOutParam =
          ParamBuilder.init(p).put("updateUser", updateUser).params();

      ParamChecker.init(mainOutParam)
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
          .require("mainId", ParamTypeEnum.Long)
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
          .require("shelfJson", ParamTypeEnum.String)
          .ensure();

      paramOutList.add(mainOutParam);
    }

    ParamChecker.init(newMainInParam)
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
        .require("prevInId", ParamTypeEnum.Long)
        .require("retestStatus", ParamTypeEnum.String)
        // .require("retestId", ParamTypeEnum.Long)
        .require("shelfJson", ParamTypeEnum.String)
        .ensure();

    Map<String, Object> mainInParam =
        ParamBuilder.init(newMainInParam).put("updateUser", updateUser).params();

    return this.mainOutService.transferMainOutInBulk(paramOutList, mainInParam);
  }
}
