package com.avic.mti.iron.vendor.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.vendor.domain.entity.VendorDetail;
import java.util.Map;

public class VendorDetailHelper {

  public static VendorDetail assignVendorDetail(
      VendorDetail vendorDetail, Map<String, Object> params) {

    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(vendorDetail::updateUser);

    reader.longFromKey("vendId").ifPresent(vendorDetail::vendId);
    reader.stringFromKey("vendCode").ifPresent(vendorDetail::vendCode);
    reader.longFromKey("prdtId").ifPresent(vendorDetail::prdtId);
    reader.stringFromKey("prdtCode").ifPresent(vendorDetail::prdtCode);
    reader.stringFromKey("vendDetVar01Str").ifPresent(vendorDetail::vendDetVar01Str);
    reader.stringFromKey("vendDetVar02Str").ifPresent(vendorDetail::vendDetVar02Str);
    reader.stringFromKey("vendDetVar03Str").ifPresent(vendorDetail::vendDetVar03Str);
    reader.stringFromKey("vendDetVar04Str").ifPresent(vendorDetail::vendDetVar04Str);
    reader.stringFromKey("vendDetVar05Str").ifPresent(vendorDetail::vendDetVar05Str);
    reader.stringFromKey("vendDetVar06Str").ifPresent(vendorDetail::vendDetVar06Str);
    reader.stringFromKey("vendDetVar07Str").ifPresent(vendorDetail::vendDetVar07Str);
    reader.stringFromKey("vendDetVar08Str").ifPresent(vendorDetail::vendDetVar08Str);
    reader.stringFromKey("vendDetExt01Json").ifPresent(vendorDetail::vendDetExt01Json);
    reader.stringFromKey("vendDetExt02Json").ifPresent(vendorDetail::vendDetExt02Json);
    reader.stringFromKey("vendDetExt03Json").ifPresent(vendorDetail::vendDetExt03Json);
    reader.stringFromKey("vendDetExt04Json").ifPresent(vendorDetail::vendDetExt04Json);

    return vendorDetail;
  }
}
