package com.avic.mti.iron.main.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.MainShelf;
import com.avic.mti.iron.main.service.MainShelfService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materials/shelfs")
public class MainShelfController {

  public static final Logger logger = LoggerFactory.getLogger(MainShelfController.class);

  private final MainShelfService mainShelfService;

  @Autowired
  public MainShelfController(MainShelfService mainShelfService) {
    this.mainShelfService = mainShelfService;
  }

  @GetMapping("/list")
  public List<MainShelf> getMainShelfsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("roomCode", FieldTypeEnum.String)
            .put("shelfCode", FieldTypeEnum.String)
            .put("mainNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            .put("detailJson", FieldTypeEnum.String)
            .fields();
    return this.mainShelfService.findAllMainShelfs(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public MainShelf postMainShelfs(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("roomCode", ParamTypeEnum.String)
        .require("shelfCode", ParamTypeEnum.String)
        .require("creatorNC", ParamTypeEnum.String)
        .require("createDate", ParamTypeEnum.Timestamp)
        .ensure();
    return this.mainShelfService.createMainShelf(params);
  }

  @GetMapping("/{mainShelfId}")
  public MainShelf getMainShelf(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainShelfId,
      @RequestParam Map<String, Object> params) {
    return this.mainShelfService.findById(mainShelfId);
  }

  @PutMapping("/{mainShelfId}")
  public MainShelf putMainShelf(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainShelfId,
      @RequestBody Map<String, Object> params) {
    return this.mainShelfService.replaceMainShelf(mainShelfId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{mainShelfId}")
  public void deleteMainShelf(
      @RequestHeader HttpHeaders headers,
      @PathVariable long mainShelfId,
      @RequestParam Map<String, Object> params) {
    this.mainShelfService.deleteMainShelf(mainShelfId);
  }

  @GetMapping("/in")
  public List<MainIn> getMainShelfIn(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.mainShelfService.findShelfIns(params);
  }
}
