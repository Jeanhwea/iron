package com.avic.mti.iron.vendor.service;

import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.vendor.domain.entity.VendorDetail;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface VendorDetailService {
  Page<VendorDetail> findVendorDetails(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  List<VendorDetail> findAllVendorDetails(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  VendorDetail findById(long vendorDetailId);

  VendorDetail createVendorDetail(Map<String, Object> params);

  VendorDetail replaceVendorDetail(long vendorDetailId, Map<String, Object> params);

  void deleteVendorDetail(long vendorDetailId);
}
