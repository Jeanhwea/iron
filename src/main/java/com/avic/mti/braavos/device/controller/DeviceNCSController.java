package com.avic.mti.iron.device.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamChecker;
import com.avic.mti.iron.common.http.request.ParamTypeEnum;
import com.avic.mti.iron.device.domain.entity.DeviceNCS;
import com.avic.mti.iron.device.service.DeviceNCSService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices/ncs")
public class DeviceNCSController {

  public static final Logger logger = LoggerFactory.getLogger(DeviceNCSController.class);

  private final DeviceNCSService deviceNCSService;

  @Autowired
  public DeviceNCSController(DeviceNCSService deviceNCSService) {
    this.deviceNCSService = deviceNCSService;
  }

  @GetMapping("/list")
  public List<DeviceNCS> getDeviceNCSList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("ncsCode", FieldTypeEnum.String)
            .put("ncsName", FieldTypeEnum.String)
            .put("note", FieldTypeEnum.String)
            .fields();
    return this.deviceNCSService.findAllDeviceNCS(params, fields);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("")
  public DeviceNCS postDevice(
      @RequestHeader HttpHeaders headers, @RequestBody Map<String, Object> params) {
    ParamChecker.init(params)
        .require("ncsCode", ParamTypeEnum.String)
        .require("ncsName", ParamTypeEnum.String)
        // .require("note", ParamTypeEnum.String)
        .ensure();
    return this.deviceNCSService.createDeviceNCS(params);
  }

  @GetMapping("/{deviceNCSId}")
  public DeviceNCS getDeviceNCS(
      @RequestHeader HttpHeaders headers,
      @PathVariable long deviceNCSId,
      @RequestParam Map<String, Object> params) {
    return this.deviceNCSService.findById(deviceNCSId);
  }

  @PutMapping("/{deviceNCSId}")
  public DeviceNCS putDeviceNCS(
      @RequestHeader HttpHeaders headers,
      @PathVariable long deviceNCSId,
      @RequestBody Map<String, Object> params) {
    return this.deviceNCSService.replaceDeviceNCS(deviceNCSId, params);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{deviceNCSId}")
  public void deleteDeviceNCS(
      @RequestHeader HttpHeaders headers,
      @PathVariable long deviceNCSId,
      @RequestParam Map<String, Object> params) {
    this.deviceNCSService.deleteDeviceNCS(deviceNCSId);
  }
}
