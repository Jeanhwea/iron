package com.avic.mti.iron.device.controller;

import com.avic.mti.iron.common.http.request.*;
import com.avic.mti.iron.device.domain.entity.Device;
import com.avic.mti.iron.device.service.DeviceService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/devices")
public class DeviceController {

  public static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

  private final DeviceService deviceService;

  @Autowired
  public DeviceController(DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  @GetMapping("")
  public Page<Device> getDevicesPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("devcCode", FieldTypeEnum.String)
            .put("devcName", FieldTypeEnum.String)
            .put("devcSpec", FieldTypeEnum.String)
            .put("devcCateId", FieldTypeEnum.Long)
            .put("devcCate", FieldTypeEnum.Enumeration)
            .put("devcDept", FieldTypeEnum.String)
            .put("devcAdmin", FieldTypeEnum.String)
            .put("devcMaker", FieldTypeEnum.String)
            .put("devcStatus", FieldTypeEnum.Enumeration)
            .put("devcType", FieldTypeEnum.Enumeration)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("inStub", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("maxJobCount", FieldTypeEnum.Long)
            .put("serviceLife", FieldTypeEnum.Timestamp)
            .put("devcNCSId", FieldTypeEnum.Long)
            .put("devcNCS", FieldTypeEnum.Enumeration)
            .put("devcPower", FieldTypeEnum.String)
            .put("connStatus", FieldTypeEnum.String)
            .put("asset", FieldTypeEnum.String)
            .put("assetCate", FieldTypeEnum.Enumeration)
            .put("maintPeriod", FieldTypeEnum.String)
            .put("maintRecord", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            .put("mainPara", FieldTypeEnum.String)
            .put("devcImageName", FieldTypeEnum.String)
            .put("hasImageFile", FieldTypeEnum.Boolean)
            // .put("shelfJson", FieldTypeEnum.String)
            .put("dblDevcVar01", FieldTypeEnum.Double)
            .put("dblDevcVar02", FieldTypeEnum.Double)
            .put("dblDevcVar03", FieldTypeEnum.Double)
            .put("dblDevcVar04", FieldTypeEnum.Double)
            .put("intDevcVar01", FieldTypeEnum.Long)
            .put("intDevcVar02", FieldTypeEnum.Long)
            .put("intDevcVar03", FieldTypeEnum.Long)
            .put("intDevcVar04", FieldTypeEnum.Long)
            .put("strDevcVar01", FieldTypeEnum.String)
            .put("strDevcVar02", FieldTypeEnum.String)
            .put("strDevcVar03", FieldTypeEnum.String)
            .put("strDevcVar04", FieldTypeEnum.String)
            .put("strDevcVar05", FieldTypeEnum.String)
            .put("strDevcVar06", FieldTypeEnum.String)
            .put("strDevcVar07", FieldTypeEnum.String)
            .put("strDevcVar08", FieldTypeEnum.String)
            .fields();
    return this.deviceService.findDevices(params, fields);
  }

  @GetMapping("/list")
  public List<Device> getDevicesList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("devcCode", FieldTypeEnum.String)
            .put("devcName", FieldTypeEnum.String)
            .put("devcSpec", FieldTypeEnum.String)
            .put("devcCateId", FieldTypeEnum.Long)
            .put("devcCate", FieldTypeEnum.Enumeration)
            .put("devcDept", FieldTypeEnum.String)
            .put("devcAdmin", FieldTypeEnum.String)
            .put("devcMaker", FieldTypeEnum.String)
            .put("devcStatus", FieldTypeEnum.Enumeration)
            .put("devcType", FieldTypeEnum.Enumeration)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("inStub", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("maxJobCount", FieldTypeEnum.Long)
            .put("serviceLife", FieldTypeEnum.Timestamp)
            .put("devcNCSId", FieldTypeEnum.Long)
            .put("devcNCS", FieldTypeEnum.Enumeration)
            .put("devcPower", FieldTypeEnum.String)
            .put("connStatus", FieldTypeEnum.String)
            .put("asset", FieldTypeEnum.String)
            .put("assetCate", FieldTypeEnum.Enumeration)
            .put("maintPeriod", FieldTypeEnum.String)
            .put("maintRecord", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            .put("mainPara", FieldTypeEnum.String)
            .put("devcImageName", FieldTypeEnum.String)
            .put("hasImageFile", FieldTypeEnum.Boolean)
            // .put("shelfJson", FieldTypeEnum.String)
            .put("dblDevcVar01", FieldTypeEnum.Double)
            .put("dblDevcVar02", FieldTypeEnum.Double)
            .put("dblDevcVar03", FieldTypeEnum.Double)
            .put("dblDevcVar04", FieldTypeEnum.Double)
            .put("intDevcVar01", FieldTypeEnum.Long)
            .put("intDevcVar02", FieldTypeEnum.Long)
            .put("intDevcVar03", FieldTypeEnum.Long)
            .put("intDevcVar04", FieldTypeEnum.Long)
            .put("strDevcVar01", FieldTypeEnum.String)
            .put("strDevcVar02", FieldTypeEnum.String)
            .put("strDevcVar03", FieldTypeEnum.String)
            .put("strDevcVar04", FieldTypeEnum.String)
            .put("strDevcVar05", FieldTypeEnum.String)
            .put("strDevcVar06", FieldTypeEnum.String)
            .put("strDevcVar07", FieldTypeEnum.String)
            .put("strDevcVar08", FieldTypeEnum.String)
            .fields();
    return this.deviceService.findAllDevices(params, fields);
  }

  @GetMapping("/idle")
  public List<Device> getIdleDevicesList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    return this.deviceService.findIdleDevices();
  }

  @PostMapping("/bulk/read")
  public List<Device> postDevicesList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("devcCodes", ParamTypeEnum.List).ensure();
    return this.deviceService.findAllDevices(params);
  }

  @PostMapping("/bulk/read/idle")
  public List<Device> postIdleDevicesList(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params).require("devcCodes", ParamTypeEnum.List).ensure();
    return this.deviceService.findIdleDevices(params);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public Device postDevices(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("devcCode", ParamTypeEnum.String)
        .require("devcName", ParamTypeEnum.String)
        // .require("devcSpec", ParamTypeEnum.String)
        .require("devcCateId", ParamTypeEnum.Long)
        // .require("devcDept", ParamTypeEnum.String)
        // .require("devcAdmin", ParamTypeEnum.String)
        // .require("devcMaker", ParamTypeEnum.String)
        .require("devcStatus", ParamTypeEnum.String)
        .require("inType", ParamTypeEnum.String)
        // .require("inStub", ParamTypeEnum.String)
        // .require("inNC", ParamTypeEnum.String)
        // .require("inDate", ParamTypeEnum.Timestamp)
        .require("maxJobCount", ParamTypeEnum.Long)
        // .require("serviceLife", ParamTypeEnum.Timestamp)
        .require("devcNCSId", ParamTypeEnum.Long)
        // .require("devcPower", ParamTypeEnum.String)
        .require("connStatus", ParamTypeEnum.String)
        // .require("asset", ParamTypeEnum.String)
        .require("assetCate", ParamTypeEnum.String)
        // .require("maintPeriod", ParamTypeEnum.String)
        // .require("maintRecord", ParamTypeEnum.String)
        // .require("note", ParamTypeEnum.String)
        // .require("mainPara", ParamTypeEnum.String)
        // .require("devcImageName", ParamTypeEnum.String)
        // .require("shelfJson", ParamTypeEnum.String)
        .ensure();
    return this.deviceService.createDevice(params);
  }

  @GetMapping("/{deviceId}")
  public Device getDevice(
      @RequestHeader HttpHeaders headers,
      @PathVariable long deviceId,
      @RequestParam Map<String, Object> params) {
    return this.deviceService.findById(deviceId);
  }

  @PutMapping("/{deviceId}")
  public Device putDevice(
      @RequestHeader HttpHeaders headers,
      @PathVariable long deviceId,
      @RequestBody Map<String, Object> params) {
    return this.deviceService.replaceDevice(deviceId, params);
  }

  @PutMapping("/status")
  public Device updateDeviceStatus(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("devcCode", ParamTypeEnum.String)
        .require("devcStatus", ParamTypeEnum.String)
        .ensure();
    return this.deviceService.updateDeviceStatus(params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{deviceId}")
  public void deleteDevice(
      @RequestHeader HttpHeaders headers,
      @PathVariable long deviceId,
      @RequestParam Map<String, Object> params) {
    this.deviceService.deleteDevice(deviceId);
  }

  @GetMapping(value = "/{deviceId}/image", produces = MediaType.IMAGE_PNG_VALUE)
  public byte[] getDeviceImage(@RequestHeader HttpHeaders headers, @PathVariable long deviceId) {
    return this.deviceService.getDeviceImage(deviceId);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @PostMapping("/{deviceId}/image")
  public void postDeviceImage(
      @RequestHeader HttpHeaders headers,
      @PathVariable long deviceId,
      @RequestParam(value = "file") MultipartFile multipartFile) {
    String updateUser = HeaderHelper.getUpdateUser(headers);
    this.deviceService.setDeviceImage(updateUser, deviceId, multipartFile);
  }
}
