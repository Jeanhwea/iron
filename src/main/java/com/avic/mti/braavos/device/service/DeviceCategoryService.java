package com.avic.mti.iron.device.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.device.domain.entity.DeviceCategory;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface DeviceCategoryService {

  Page<DeviceCategory> findDeviceCategories(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<DeviceCategory> findAllDeviceCategories(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  @Transactional(readOnly = true)
  DeviceCategory findById(long deviceCategoryId);

  DeviceCategory createDeviceCategory(Map<String, Object> params);

  DeviceCategory replaceDeviceCategory(long deviceCategoryId, Map<String, Object> params);

  void deleteDeviceCategory(long deviceCategoryId);
}
