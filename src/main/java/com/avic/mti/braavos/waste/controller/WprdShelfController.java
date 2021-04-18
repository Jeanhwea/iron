package com.avic.mti.iron.waste.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.domain.entity.WprdShelf;
import com.avic.mti.iron.waste.service.WprdShelfService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waste/products/shelfs")
public class WprdShelfController {

  public static final Logger logger = LoggerFactory.getLogger(WprdShelfController.class);

  private final WprdShelfService wprdShelfService;

  @Autowired
  public WprdShelfController(WprdShelfService wprdShelfService) {
    this.wprdShelfService = wprdShelfService;
  }

  @GetMapping("/list")
  public List<WprdShelf> getWprdShelfsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("roomCode", FieldTypeEnum.String)
            .put("shelfCode", FieldTypeEnum.String)
            .put("wprdNum", FieldTypeEnum.Long)
            .put("measure", FieldTypeEnum.String)
            .put("creatorNC", FieldTypeEnum.String)
            .put("createDate", FieldTypeEnum.Timestamp)
            .put("note", FieldTypeEnum.String)
            .put("detailJson", FieldTypeEnum.String)
            .fields();
    return this.wprdShelfService.findAllWprdShelfs(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public WprdShelf postWprdShelfs(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("roomCode", ParamTypeEnum.String)
        .require("shelfCode", ParamTypeEnum.String)
        .require("creatorNC", ParamTypeEnum.String)
        .require("createDate", ParamTypeEnum.Timestamp)
        .ensure();
    return this.wprdShelfService.createWprdShelf(params);
  }

  @GetMapping("/{wprdShelfId}")
  public WprdShelf getWprdShelf(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdShelfId,
      @RequestParam Map<String, Object> params) {
    return this.wprdShelfService.findById(wprdShelfId);
  }

  @PutMapping("/{wprdShelfId}")
  public WprdShelf putWprdShelf(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdShelfId,
      @RequestBody Map<String, Object> params) {
    return this.wprdShelfService.replaceWprdShelf(wprdShelfId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{wprdShelfId}")
  public void deleteWprdShelf(
      @RequestHeader HttpHeaders headers,
      @PathVariable long wprdShelfId,
      @RequestParam Map<String, Object> params) {
    this.wprdShelfService.deleteWprdShelf(wprdShelfId);
  }

  @GetMapping("/in")
  public List<WprdIn> getWprdShelfIn(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.wprdShelfService.findShelfIns(params);
  }
}
