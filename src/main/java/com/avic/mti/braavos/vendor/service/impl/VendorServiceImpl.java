package com.avic.mti.iron.vendor.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.vendor.domain.entity.Vendor;
import com.avic.mti.iron.vendor.domain.repo.VendorRepository;
import com.avic.mti.iron.vendor.helper.VendorHelper;
import com.avic.mti.iron.vendor.service.VendorService;
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
public class VendorServiceImpl implements VendorService {

  public static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

  private final VendorRepository vendorRepository;
  private final MesConditionBuilder<Vendor> mesConditionBuilder;

  @Autowired
  public VendorServiceImpl(
      VendorRepository vendorRepository, MesConditionBuilder<Vendor> mesConditionBuilder) {
    this.vendorRepository = vendorRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateVendor(Vendor vendor) {
    // pass
  }

  private void checkBeforeCreate(Vendor vendor) {
    // pass
  }

  private void checkBeforeReplace(Vendor vendor) {
    // pass
  }

  private void checkBeforeDelete(Vendor vendor) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Vendor> findVendors(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<Vendor> builder = this.mesConditionBuilder.init(params, fields);
    return this.vendorRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<Vendor> findAllVendors(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<Vendor> builder = this.mesConditionBuilder.init(params, fields);
    return this.vendorRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public Vendor findById(long vendorId) {
    return this.vendorRepository
        .fetchById(vendorId)
        .orElseThrow(() -> new DatumNotExistException("Vendor", vendorId));
  }

  @Override
  public Vendor createVendor(Map<String, Object> params) {
    Vendor newVendor = VendorHelper.assignVendor(new Vendor(), params);

    boolean conflict = this.vendorRepository.findFirstByVendCode(newVendor.vendCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("供应商 {0} 已经存在", newVendor.vendCode());
    }

    this.checkBeforeCreate(newVendor);
    this.validateVendor(newVendor);
    return this.vendorRepository.save(newVendor);
  }

  @Override
  public Vendor replaceVendor(long vendorId, Map<String, Object> params) {
    Vendor prevVendor = this.findById(vendorId);

    ParamReader reader = ParamReader.init(params);
    String newVendorCode = reader.stringFromKey("vendCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newVendorCode)
            && !newVendorCode.equals(prevVendor.vendCode())
            && this.vendorRepository.findFirstByVendCode(newVendorCode).isPresent();
    if (conflict) {
      throw new BadRequestException("供应商 {0} 已经存在", newVendorCode);
    }

    this.checkBeforeReplace(prevVendor);
    Vendor currVendor = VendorHelper.assignVendor(prevVendor, params);
    this.validateVendor(currVendor);
    return this.vendorRepository.save(currVendor);
  }

  @Override
  public void deleteVendor(long vendorId) {
    Vendor prevVendor = this.findById(vendorId);
    this.checkBeforeDelete(prevVendor);
    this.vendorRepository.delete(prevVendor);
  }
}
