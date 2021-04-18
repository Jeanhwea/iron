package com.avic.mti.iron.device.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.device.domain.entity.DeviceNCS;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface DeviceNCSService {

  Page<DeviceNCS> findDeviceNCS(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<DeviceNCS> findAllDeviceNCS(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  DeviceNCS findById(long deviceNCSId);

  DeviceNCS createDeviceNCS(Map<String, Object> params);

  DeviceNCS replaceDeviceNCS(long deviceNCSId, Map<String, Object> params);

  void deleteDeviceNCS(long deviceNCSId);
}
