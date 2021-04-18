package com.avic.mti.iron.device.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.device.domain.entity.DeviceCategory;
import com.avic.mti.iron.device.service.DeviceCategoryService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices/categories")
public class DeviceCategoryController {

  public static final Logger logger = LoggerFactory.getLogger(DeviceCategoryController.class);

  private final DeviceCategoryService deviceCategoryService;

  @Autowired
  public DeviceCategoryController(DeviceCategoryService deviceCategoryService) {
    this.deviceCategoryService = deviceCategoryService;
  }

  @GetMapping("/list")
  public List<DeviceCategory> getDeviceCategoriesList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("cateCode", FieldTypeEnum.String)
            .put("cateName", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            .fields();
    return this.deviceCategoryService.findAllDeviceCategories(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public DeviceCategory postDeviceCategories(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("cateCode", ParamTypeEnum.String)
        .require("cateName", ParamTypeEnum.String)
        // .require("note",ParamTypeEnum.String )
        .ensure();
    return this.deviceCategoryService.createDeviceCategory(params);
  }

  @GetMapping("/{deviceCategoryId}")
  public DeviceCategory getDeviceCategory(
      @RequestHeader HttpHeaders headers,
      @PathVariable long deviceCategoryId,
      @RequestParam Map<String, Object> params) {
    return this.deviceCategoryService.findById(deviceCategoryId);
  }

  @PutMapping("/{deviceCategoryId}")
  public DeviceCategory putDeviceCategory(
      @RequestHeader HttpHeaders headers,
      @PathVariable long deviceCategoryId,
      @RequestBody Map<String, Object> params) {
    return this.deviceCategoryService.replaceDeviceCategory(deviceCategoryId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{deviceCategoryId}")
  public void deleteDeviceCategory(
      @RequestHeader HttpHeaders headers,
      @PathVariable long deviceCategoryId,
      @RequestParam Map<String, Object> params) {
    this.deviceCategoryService.deleteDeviceCategory(deviceCategoryId);
  }
}
