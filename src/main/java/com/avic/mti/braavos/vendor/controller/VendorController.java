package com.avic.mti.iron.vendor.controller;

import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.vendor.domain.entity.Vendor;
import com.avic.mti.iron.vendor.service.VendorService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendors")
public class VendorController {

  public static final Logger logger = LoggerFactory.getLogger(VendorController.class);

  private final VendorService vendorService;

  @Autowired
  public VendorController(VendorService vendorService) {
    this.vendorService = vendorService;
  }

  @GetMapping("")
  public Page<Vendor> getVendorsPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("vendCode", FieldTypeEnum.String)
            .put("vendName", FieldTypeEnum.String)
            .put("vendSname", FieldTypeEnum.String)
            .put("vendCate", FieldTypeEnum.Enumeration)
            .put("vendType", FieldTypeEnum.Enumeration)
            .put("vendAddr", FieldTypeEnum.String)
            .put("linkman", FieldTypeEnum.String)
            .put("linkmanPhone", FieldTypeEnum.String)
            .put("recvName", FieldTypeEnum.String)
            .put("recvPhone", FieldTypeEnum.String)
            .put("recvAddr", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            .fields();
    return this.vendorService.findVendors(params, fields);
  }

  @GetMapping("/list")
  public List<Vendor> getVendorsList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("vendCode", FieldTypeEnum.String)
            .put("vendName", FieldTypeEnum.String)
            .put("vendSname", FieldTypeEnum.String)
            .put("vendCate", FieldTypeEnum.Enumeration)
            .put("vendType", FieldTypeEnum.Enumeration)
            .put("vendAddr", FieldTypeEnum.String)
            .put("linkman", FieldTypeEnum.String)
            .put("linkmanPhone", FieldTypeEnum.String)
            .put("recvName", FieldTypeEnum.String)
            .put("recvPhone", FieldTypeEnum.String)
            .put("recvAddr", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            .fields();
    return this.vendorService.findAllVendors(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public Vendor postVendors(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("vendCode", ParamTypeEnum.String)
        .require("vendName", ParamTypeEnum.String)
        .require("vendSname", ParamTypeEnum.String)
        .require("vendAddr", ParamTypeEnum.String)
        // .require("vendCate", ParamTypeEnum.String)
        // .require("vendType", ParamTypeEnum.String)
        // .require("linkman", ParamTypeEnum.String)
        // .require("linkmanPhone", ParamTypeEnum.String)
        // .require("recvName", ParamTypeEnum.String)
        // .require("recvPhone", ParamTypeEnum.String)
        // .require("recvAddr", ParamTypeEnum.String)
        // .require("note", ParamTypeEnum.String)
        .ensure();
    return this.vendorService.createVendor(params);
  }

  @GetMapping("/{vendorId}")
  public Vendor getVendor(
      @RequestHeader HttpHeaders headers,
      @PathVariable long vendorId,
      @RequestParam Map<String, Object> params) {
    return this.vendorService.findById(vendorId);
  }

  @PutMapping("/{vendorId}")
  public Vendor putVendor(
      @RequestHeader HttpHeaders headers,
      @PathVariable long vendorId,
      @RequestBody Map<String, Object> params) {
    return this.vendorService.replaceVendor(vendorId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{vendorId}")
  public void deleteVendor(
      @RequestHeader HttpHeaders headers,
      @PathVariable long vendorId,
      @RequestParam Map<String, Object> params) {
    this.vendorService.deleteVendor(vendorId);
  }
}
