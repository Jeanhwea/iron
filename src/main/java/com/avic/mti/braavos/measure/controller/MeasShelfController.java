package com.avic.mti.iron.measure.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
import com.avic.mti.iron.measure.domain.entity.MeasShelf;
import com.avic.mti.iron.measure.service.MeasShelfService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/measures/shelfs")
public class MeasShelfController {

  public static final Logger logger = LoggerFactory.getLogger(MeasShelfController.class);

  private final MeasShelfService measShelfService;

  @Autowired
  public MeasShelfController(MeasShelfService measShelfService) {
    this.measShelfService = measShelfService;
  }

  @GetMapping("/list")
  public List<MeasShelf> getMeasShelfsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("roomCode", FieldTypeEnum.String)
            .put("shelfCode", FieldTypeEnum.String)
            .put("measNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            .put("detailJson", FieldTypeEnum.String)
            .fields();
    return this.measShelfService.findAllMeasShelfs(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public MeasShelf postMeasShelfs(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("roomCode", ParamTypeEnum.String)
        .require("shelfCode", ParamTypeEnum.String)
        .require("creatorNC", ParamTypeEnum.String)
        .require("createDate", ParamTypeEnum.Timestamp)
        .ensure();
    return this.measShelfService.createMeasShelf(params);
  }

  @GetMapping("/{measShelfId}")
  public MeasShelf getMeasShelf(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measShelfId,
      @RequestParam Map<String, Object> params) {
    return this.measShelfService.findById(measShelfId);
  }

  @PutMapping("/{measShelfId}")
  public MeasShelf putMeasShelf(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measShelfId,
      @RequestBody Map<String, Object> params) {
    return this.measShelfService.replaceMeasShelf(measShelfId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{measShelfId}")
  public void deleteMeasShelf(
      @RequestHeader HttpHeaders headers,
      @PathVariable long measShelfId,
      @RequestParam Map<String, Object> params) {
    this.measShelfService.deleteMeasShelf(measShelfId);
  }

  @GetMapping("/in")
  public List<MeasIn> getMeasShelfIn(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.measShelfService.findShelfIns(params);
  }
}
