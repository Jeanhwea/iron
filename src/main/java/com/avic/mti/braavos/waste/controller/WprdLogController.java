package com.avic.mti.iron.waste.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdLog;
import com.avic.mti.iron.waste.service.WprdLogService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waste/products/logs")
public class WprdLogController {

  public static final Logger logger = LoggerFactory.getLogger(WprdLogController.class);

  private final WprdLogService wprdLogService;

  @Autowired
  public WprdLogController(WprdLogService wprdLogService) {
    this.wprdLogService = wprdLogService;
  }

  @GetMapping("")
  public Page<WprdLog> getWprdLogsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("logStatus", FieldTypeEnum.Enumeration)
            .put("logCate", FieldTypeEnum.Enumeration)
            .put("logType", FieldTypeEnum.Enumeration)
            .put("logFlag", FieldTypeEnum.Enumeration)
            .put("wprdId", FieldTypeEnum.Long)
            .put("inId", FieldTypeEnum.Long)
            .put("stubId", FieldTypeEnum.Long)
            .put("stubDetId", FieldTypeEnum.Long)
            .put("planEnum", FieldTypeEnum.Long)
            .put("planFnum", FieldTypeEnum.Long)
            .put("retNum", FieldTypeEnum.Long)
            .put("retPrevId", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
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
            .fields();
    return this.wprdLogService.findWprdLogs(params, fields);
  }

  @GetMapping("/list")
  public List<WprdLog> getWprdLogsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("logStatus", FieldTypeEnum.Enumeration)
            .put("logCate", FieldTypeEnum.Enumeration)
            .put("logType", FieldTypeEnum.Enumeration)
            .put("logFlag", FieldTypeEnum.Enumeration)
            .put("wprdId", FieldTypeEnum.Long)
            .put("inId", FieldTypeEnum.Long)
            .put("stubId", FieldTypeEnum.Long)
            .put("stubDetId", FieldTypeEnum.Long)
            .put("planEnum", FieldTypeEnum.Long)
            .put("planFnum", FieldTypeEnum.Long)
            .put("retNum", FieldTypeEnum.Long)
            .put("retPrevId", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("mtlBatch", FieldTypeEnum.String)
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
            .fields();
    return this.wprdLogService.findAllWprdLogs(params, fields);
  }
}
