package com.avic.mti.iron.ledger.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.ledger.domain.entity.Resource;
import com.avic.mti.iron.ledger.service.ResourceService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ledgers/resources")
public class ResourceController {

  public static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

  private final ResourceService resourceService;

  @Autowired
  public ResourceController(ResourceService resourceService) {
    this.resourceService = resourceService;
  }

  @GetMapping("")
  public Page<Resource> getResourcesPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("resCode", FieldTypeEnum.String)
            .put("resName", FieldTypeEnum.String)
            .put("resType", FieldTypeEnum.Enumeration)
            .put("resCate", FieldTypeEnum.Enumeration)
            .put("resSpec", FieldTypeEnum.String)
            .put("resDesc", FieldTypeEnum.String)
            .put("resMeasure", FieldTypeEnum.String)
            .put("resTechType", FieldTypeEnum.String)
            .put("connStatus", FieldTypeEnum.String)
            .put("resRefCode", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            // .put("paramJson", FieldTypeEnum.String)
            .fields();
    return this.resourceService.findResources(params, fields);
  }

  @GetMapping("/list")
  public List<Resource> getResourcesList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("resCode", FieldTypeEnum.String)
            .put("resName", FieldTypeEnum.String)
            .put("resType", FieldTypeEnum.Enumeration)
            .put("resCate", FieldTypeEnum.Enumeration)
            .put("resSpec", FieldTypeEnum.String)
            .put("resDesc", FieldTypeEnum.String)
            .put("resMeasure", FieldTypeEnum.String)
            .put("resTechType", FieldTypeEnum.String)
            .put("connStatus", FieldTypeEnum.String)
            .put("resRefCode", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            // .put("paramJson", FieldTypeEnum.String)
            .fields();
    return this.resourceService.findAllResources(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public Resource postResources(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("resCode", ParamTypeEnum.String)
        .require("resName", ParamTypeEnum.String)
        .require("resType", ParamTypeEnum.String)
        .require("resCate", ParamTypeEnum.String)
        // .require("resSpec", ParamTypeEnum.String)
        // .require("resDesc", ParamTypeEnum.String)
        // .require("resMeasure", ParamTypeEnum.String)
        // .require("resTechType", ParamTypeEnum.String)
        // .require("connStatus", ParamTypeEnum.String)
        .require("resRefCode", ParamTypeEnum.String)
        // .require("note", ParamTypeEnum.String)
        // .require("paramJson", ParamTypeEnum.String)
        .ensure();
    return this.resourceService.createResource(params);
  }

  @GetMapping("/{resourceId}")
  public Resource getResource(
      @RequestHeader HttpHeaders headers,
      @PathVariable long resourceId,
      @RequestParam Map<String, Object> params) {
    return this.resourceService.findById(resourceId);
  }

  @PutMapping("/{resourceId}")
  public Resource putResource(
      @RequestHeader HttpHeaders headers,
      @PathVariable long resourceId,
      @RequestBody Map<String, Object> params) {
    return this.resourceService.replaceResource(resourceId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{resourceId}")
  public void deleteResource(
      @RequestHeader HttpHeaders headers,
      @PathVariable long resourceId,
      @RequestParam Map<String, Object> params) {
    this.resourceService.deleteResource(resourceId);
  }
}
