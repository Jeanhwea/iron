package com.avic.mti.iron.tool.controller;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import com.avic.mti.iron.tool.service.ToolOutService;
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
@RequestMapping("/tools/out")
public class ToolOutController {

  public static final Logger logger = LoggerFactory.getLogger(ToolOutController.class);

  private final ToolOutService toolOutService;

  @Autowired
  public ToolOutController(ToolOutService toolOutService) {
    this.toolOutService = toolOutService;
  }

  @GetMapping("")
  public Page<ToolOut> getToolOutsPage(
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
            .put("toolId", FieldTypeEnum.Long)
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
    return this.toolOutService.findToolOuts(params, fields);
  }

  @GetMapping("/list")
  public List<ToolOut> getToolOutsList(
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
            .put("toolId", FieldTypeEnum.Long)
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
    return this.toolOutService.findAllToolOuts(params, fields);
  }

  @GetMapping("/{toolOutId}")
  public ToolOut getToolOut(
      @RequestHeader HttpHeaders headers,
      @PathVariable long toolOutId,
      @RequestParam Map<String, Object> params) {
    return this.toolOutService.findById(toolOutId);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/bulk/create")
  public List<ToolOut> postToolOutsInBulk(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        // .require("stubId", ParamTypeEnum.Long)
        // .require("stubDetId", ParamTypeEnum.Long)
        .require("toolOutList", ParamTypeEnum.List)
        .ensure();
    ParamReader globalReader = ParamReader.init(params);
    List<Map<String, Object>> toolOutList =
        globalReader
            .listObjFromKey("toolOutList")
            .orElseThrow(() -> new BadRequestException("缺少 toolOutList 参数"));
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
    for (Map<String, Object> p : toolOutList) {
      Map<String, Object> toolOutParam =
          ParamBuilder.init(p)
              .put("updateUser", updateUser)
              // .put("stubId", stubId)
              // .put("stubDetId", stubDetId)
              .params();

      ParamChecker.init(toolOutParam)
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
          .require("toolId", ParamTypeEnum.Long)
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

      paramList.add(toolOutParam);
    }

    return this.toolOutService.createToolOutInBulk(paramList);
  }
}
