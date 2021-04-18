package com.avic.mti.iron.vendor.controller;

import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.vendor.domain.entity.VendorDetail;
import com.avic.mti.iron.vendor.service.VendorDetailService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendors/details")
public class VendorDetailController {

  public static final Logger logger = LoggerFactory.getLogger(VendorDetailController.class);

  private final VendorDetailService vendorDetailService;

  @Autowired
  public VendorDetailController(VendorDetailService vendorDetailService) {
    this.vendorDetailService = vendorDetailService;
  }

  @GetMapping("")
  public Page<VendorDetail> getVendorDetailsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("vendId", FieldTypeEnum.Long)
            .put("vendCode", FieldTypeEnum.String)
            .put("prdtId", FieldTypeEnum.Long)
            .put("prdtCode", FieldTypeEnum.String)
            .put("vendDetVar01Str", FieldTypeEnum.String)
            .put("vendDetVar02Str", FieldTypeEnum.String)
            .put("vendDetVar03Str", FieldTypeEnum.String)
            .put("vendDetVar04Str", FieldTypeEnum.String)
            .put("vendDetVar05Str", FieldTypeEnum.String)
            .put("vendDetVar06Str", FieldTypeEnum.String)
            .put("vendDetVar07Str", FieldTypeEnum.String)
            .put("vendDetVar08Str", FieldTypeEnum.String)
            // .put("vendDetExt01Json", FieldTypeEnum.String)
            // .put("vendDetExt02Json", FieldTypeEnum.String)
            // .put("vendDetExt03Json", FieldTypeEnum.String)
            // .put("vendDetExt04Json", FieldTypeEnum.String)
            .fields();
    return this.vendorDetailService.findVendorDetails(params, fields);
  }

  @GetMapping("/list")
  public List<VendorDetail> getVendorDetailsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("vendId", FieldTypeEnum.Long)
            .put("vendCode", FieldTypeEnum.String)
            .put("prdtId", FieldTypeEnum.Long)
            .put("prdtCode", FieldTypeEnum.String)
            .put("vendDetVar01Str", FieldTypeEnum.String)
            .put("vendDetVar02Str", FieldTypeEnum.String)
            .put("vendDetVar03Str", FieldTypeEnum.String)
            .put("vendDetVar04Str", FieldTypeEnum.String)
            .put("vendDetVar05Str", FieldTypeEnum.String)
            .put("vendDetVar06Str", FieldTypeEnum.String)
            .put("vendDetVar07Str", FieldTypeEnum.String)
            .put("vendDetVar08Str", FieldTypeEnum.String)
            // .put("vendDetExt01Json", FieldTypeEnum.String)
            // .put("vendDetExt02Json", FieldTypeEnum.String)
            // .put("vendDetExt03Json", FieldTypeEnum.String)
            // .put("vendDetExt04Json", FieldTypeEnum.String)
            .fields();
    return this.vendorDetailService.findAllVendorDetails(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public VendorDetail postVendorDetails(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("vendId", ParamTypeEnum.Long)
        .require("vendCode", ParamTypeEnum.String)
        .require("prdtId", ParamTypeEnum.Long)
        .require("prdtCode", ParamTypeEnum.String)
        // .require("vendDetVar01Str", ParamTypeEnum.String)
        // .require("vendDetVar02Str", ParamTypeEnum.String)
        // .require("vendDetVar03Str", ParamTypeEnum.String)
        // .require("vendDetVar04Str", ParamTypeEnum.String)
        // .require("vendDetVar05Str", ParamTypeEnum.String)
        // .require("vendDetVar06Str", ParamTypeEnum.String)
        // .require("vendDetVar07Str", ParamTypeEnum.String)
        // .require("vendDetVar08Str", ParamTypeEnum.String)
        // .require("vendDetExt01Json", ParamTypeEnum.String)
        // .require("vendDetExt02Json", ParamTypeEnum.String)
        // .require("vendDetExt03Json", ParamTypeEnum.String)
        // .require("vendDetExt04Json", ParamTypeEnum.String)
        .ensure();
    return this.vendorDetailService.createVendorDetail(params);
  }

  @GetMapping("/{vendorDetailId}")
  public VendorDetail getVendorDetail(
      @RequestHeader HttpHeaders headers,
      @PathVariable long vendorDetailId,
      @RequestParam Map<String, Object> params) {
    return this.vendorDetailService.findById(vendorDetailId);
  }

  @PutMapping("/{vendorDetailId}")
  public VendorDetail putVendorDetail(
      @RequestHeader HttpHeaders headers,
      @PathVariable long vendorDetailId,
      @RequestBody Map<String, Object> params) {
    return this.vendorDetailService.replaceVendorDetail(vendorDetailId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{vendorDetailId}")
  public void deleteVendorDetail(
      @RequestHeader HttpHeaders headers,
      @PathVariable long vendorDetailId,
      @RequestParam Map<String, Object> params) {
    this.vendorDetailService.deleteVendorDetail(vendorDetailId);
  }
}
