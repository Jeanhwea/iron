package com.avic.mti.iron.vendor.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.vendor.domain.entity.Vendor;
import java.util.Map;

public class VendorHelper {

  public static Vendor assignVendor(Vendor vendor, Map<String, Object> params) {

    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(vendor::updateUser);

    reader.stringFromKey("vendCode").ifPresent(vendor::vendCode);
    reader.stringFromKey("vendName").ifPresent(vendor::vendName);
    reader.stringFromKey("vendSname").ifPresent(vendor::vendSname);
    reader.stringFromKey("vendCate").ifPresent(vendor::vendCate);
    reader.stringFromKey("vendType").ifPresent(vendor::vendType);
    reader.stringFromKey("vendAddr").ifPresent(vendor::vendAddr);
    reader.stringFromKey("linkman").ifPresent(vendor::linkman);
    reader.stringFromKey("linkmanPhone").ifPresent(vendor::linkmanPhone);
    reader.stringFromKey("recvName").ifPresent(vendor::recvName);
    reader.stringFromKey("recvPhone").ifPresent(vendor::recvPhone);
    reader.stringFromKey("recvAddr").ifPresent(vendor::recvAddr);
    reader.stringFromKey("note").ifPresent(vendor::note);

    return vendor;
  }

  private VendorHelper() {}
}
