package com.avic.mti.iron.device.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.device.domain.entity.DeviceCategory;
import java.util.Map;

public class DeviceCategoryHelper {

  public static DeviceCategory assignDeviceCategory(
      DeviceCategory deviceCategory, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(deviceCategory::updateUser);

    reader.stringFromKey("cateCode").ifPresent(deviceCategory::cateCode);
    reader.stringFromKey("cateName").ifPresent(deviceCategory::cateName);
    reader.stringFromKey("note").ifPresent(deviceCategory::note);

    return deviceCategory;
  }

  public DeviceCategoryHelper() {}
}
