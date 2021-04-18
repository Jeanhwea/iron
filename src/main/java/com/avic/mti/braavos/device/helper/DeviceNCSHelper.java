package com.avic.mti.iron.device.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.device.domain.entity.DeviceNCS;
import java.util.Map;

public class DeviceNCSHelper {

  public static DeviceNCS assignDeviceNCS(DeviceNCS deviceNCS, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(deviceNCS::updateUser);

    reader.stringFromKey("ncsCode").ifPresent(deviceNCS::ncsCode);
    reader.stringFromKey("ncsName").ifPresent(deviceNCS::ncsName);
    reader.stringFromKey("note").ifPresent(deviceNCS::note);

    return deviceNCS;
  }

  public DeviceNCSHelper() {}
}
