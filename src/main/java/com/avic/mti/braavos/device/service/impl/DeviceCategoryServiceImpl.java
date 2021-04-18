package com.avic.mti.iron.device.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.device.domain.entity.DeviceCategory;
import com.avic.mti.iron.device.domain.repo.DeviceCategoryRepository;
import com.avic.mti.iron.device.helper.DeviceCategoryHelper;
import com.avic.mti.iron.device.service.DeviceCategoryService;
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
public class DeviceCategoryServiceImpl implements DeviceCategoryService {

  public static final Logger logger = LoggerFactory.getLogger(DeviceCategoryServiceImpl.class);

  private final DeviceCategoryRepository deviceCategoryRepository;
  private final MesConditionBuilder<DeviceCategory> mesConditionBuilder;

  @Autowired
  public DeviceCategoryServiceImpl(
      DeviceCategoryRepository deviceCategoryRepository,
      MesConditionBuilder<DeviceCategory> mesConditionBuilder) {
    this.deviceCategoryRepository = deviceCategoryRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateDeviceCategory(DeviceCategory deviceCategory) {
    // pass
  }

  private void checkBeforeCreate(DeviceCategory deviceCategory) {
    // pass
  }

  private void checkBeforeReplace(DeviceCategory deviceCategory) {
    // pass
  }

  private void checkBeforeDelete(DeviceCategory deviceCategory) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<DeviceCategory> findDeviceCategories(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<DeviceCategory> builder = this.mesConditionBuilder.init(params, fields);
    return this.deviceCategoryRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<DeviceCategory> findAllDeviceCategories(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<DeviceCategory> builder = this.mesConditionBuilder.init(params, fields);
    return this.deviceCategoryRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public DeviceCategory findById(long deviceCategoryId) {
    return this.deviceCategoryRepository
        .fetchById(deviceCategoryId)
        .orElseThrow(() -> new DatumNotExistException("DeviceCategory", deviceCategoryId));
  }

  @Override
  public DeviceCategory createDeviceCategory(Map<String, Object> params) {
    DeviceCategory newDeviceCategory =
        DeviceCategoryHelper.assignDeviceCategory(new DeviceCategory(), params);

    boolean conflict =
        this.deviceCategoryRepository.findFirstByCateCode(newDeviceCategory.cateCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("设备类别编码 {0} 已经存在", newDeviceCategory.cateCode());
    }

    this.checkBeforeCreate(newDeviceCategory);
    this.validateDeviceCategory(newDeviceCategory);
    return this.deviceCategoryRepository.save(newDeviceCategory);
  }

  @Override
  public DeviceCategory replaceDeviceCategory(long deviceCategoryId, Map<String, Object> params) {
    DeviceCategory prevDeviceCategory = this.findById(deviceCategoryId);

    ParamReader reader = ParamReader.init(params);
    String newDeviceCategoryCode = reader.stringFromKey("cateCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newDeviceCategoryCode)
            && !newDeviceCategoryCode.equals(prevDeviceCategory.cateCode())
            && this.deviceCategoryRepository.findFirstByCateCode(newDeviceCategoryCode).isPresent();
    if (conflict) {
      throw new BadRequestException("设备类别编码 {0} 已经存在", newDeviceCategoryCode);
    }

    this.checkBeforeReplace(prevDeviceCategory);
    DeviceCategory currDeviceCategory =
        DeviceCategoryHelper.assignDeviceCategory(prevDeviceCategory, params);
    this.validateDeviceCategory(currDeviceCategory);
    return this.deviceCategoryRepository.save(currDeviceCategory);
  }

  @Override
  public void deleteDeviceCategory(long deviceCategoryId) {
    DeviceCategory prevDeviceCategory = this.findById(deviceCategoryId);
    this.checkBeforeDelete(prevDeviceCategory);
    this.deviceCategoryRepository.delete(prevDeviceCategory);
  }
}
