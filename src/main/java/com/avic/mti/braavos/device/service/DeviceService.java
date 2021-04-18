package com.avic.mti.iron.device.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.device.domain.entity.Device;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface DeviceService {
  Page<Device> findDevices(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<Device> findAllDevices(Map<String, Object> params);

  List<Device> findIdleDevices(Map<String, Object> params);

  List<Device> findAllDevices(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  Device findById(long deviceId);

  Device createDevice(Map<String, Object> params);

  Device replaceDevice(long deviceId, Map<String, Object> params);

  Device updateDeviceStatus(Map<String, Object> params);

  void deleteDevice(long deviceId);

  byte[] getDeviceImage(long deviceId);

  void setDeviceImage(String updateUser, long deviceId, MultipartFile multipartFile);

  List<Device> findIdleDevices();
}
