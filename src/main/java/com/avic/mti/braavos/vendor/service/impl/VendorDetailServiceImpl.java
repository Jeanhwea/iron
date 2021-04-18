package com.avic.mti.iron.vendor.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.vendor.domain.entity.VendorDetail;
import com.avic.mti.iron.vendor.domain.repo.VendorDetailRepository;
import com.avic.mti.iron.vendor.helper.VendorDetailHelper;
import com.avic.mti.iron.vendor.service.VendorDetailService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VendorDetailServiceImpl implements VendorDetailService {

  public static final Logger logger = LoggerFactory.getLogger(VendorDetailServiceImpl.class);

  private final VendorDetailRepository vendorDetailRepository;
  private final MesConditionBuilder<VendorDetail> mesConditionBuilder;

  @Autowired
  public VendorDetailServiceImpl(
      VendorDetailRepository vendorDetailRepository,
      MesConditionBuilder<VendorDetail> mesConditionBuilder) {
    this.vendorDetailRepository = vendorDetailRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateVendorDetail(VendorDetail vendorDetail) {
    // pass
  }

  private void checkBeforeCreate(VendorDetail vendorDetail) {
    // pass
  }

  private void checkBeforeReplace(VendorDetail vendorDetail) {
    // pass
  }

  private void checkBeforeDelete(VendorDetail vendorDetail) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<VendorDetail> findVendorDetails(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<VendorDetail> builder = this.mesConditionBuilder.init(params, fields);
    return this.vendorDetailRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<VendorDetail> findAllVendorDetails(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<VendorDetail> builder = this.mesConditionBuilder.init(params, fields);
    return this.vendorDetailRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public VendorDetail findById(long vendorDetailId) {
    return this.vendorDetailRepository
        .fetchById(vendorDetailId)
        .orElseThrow(() -> new DatumNotExistException("VendorDetail", vendorDetailId));
  }

  @Override
  public VendorDetail createVendorDetail(Map<String, Object> params) {
    VendorDetail newVendorDetail =
        VendorDetailHelper.assignVendorDetail(new VendorDetail(), params);

    this.checkBeforeCreate(newVendorDetail);
    this.validateVendorDetail(newVendorDetail);
    return this.vendorDetailRepository.save(newVendorDetail);
  }

  @Override
  public VendorDetail replaceVendorDetail(long vendorDetailId, Map<String, Object> params) {
    VendorDetail prevVendorDetail = this.findById(vendorDetailId);

    this.checkBeforeReplace(prevVendorDetail);
    VendorDetail currVendorDetail = VendorDetailHelper.assignVendorDetail(prevVendorDetail, params);
    this.validateVendorDetail(currVendorDetail);
    return this.vendorDetailRepository.save(currVendorDetail);
  }

  @Override
  public void deleteVendorDetail(long vendorDetailId) {
    VendorDetail prevVendorDetail = this.findById(vendorDetailId);
    this.checkBeforeDelete(prevVendorDetail);
    this.vendorDetailRepository.delete(prevVendorDetail);
  }
}
