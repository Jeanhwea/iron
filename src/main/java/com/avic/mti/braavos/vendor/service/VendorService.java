package com.avic.mti.iron.vendor.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.vendor.domain.entity.Vendor;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface VendorService {
  Page<Vendor> findVendors(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<Vendor> findAllVendors(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  Vendor findById(long vendorId);

  Vendor createVendor(Map<String, Object> params);

  Vendor replaceVendor(long vendorId, Map<String, Object> params);

  void deleteVendor(long vendorId);
}
