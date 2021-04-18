package com.avic.mti.iron.device.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.device.domain.entity.DeviceNCS;
import com.avic.mti.iron.device.domain.repo.DeviceNCSRepository;
import com.avic.mti.iron.device.helper.DeviceNCSHelper;
import com.avic.mti.iron.device.service.DeviceNCSService;
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
public class DeviceNCSServiceImpl implements DeviceNCSService {

  public static final Logger logger = LoggerFactory.getLogger(DeviceNCSServiceImpl.class);

  private final DeviceNCSRepository deviceNCSRepository;
  private final MesConditionBuilder<DeviceNCS> mesConditionBuilder;

  @Autowired
  public DeviceNCSServiceImpl(
      DeviceNCSRepository deviceNCSRepository, MesConditionBuilder<DeviceNCS> mesConditionBuilder) {
    this.deviceNCSRepository = deviceNCSRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateDeviceNCS(DeviceNCS deviceNCS) {
    // pass
  }

  private void checkBeforeCreate(DeviceNCS deviceNCS) {
    // pass
  }

  private void checkBeforeReplace(DeviceNCS deviceNCS) {
    // pass
  }

  private void checkBeforeDelete(DeviceNCS deviceNCS) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<DeviceNCS> findDeviceNCS(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<DeviceNCS> builder = this.mesConditionBuilder.init(params, fields);
    return this.deviceNCSRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<DeviceNCS> findAllDeviceNCS(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<DeviceNCS> builder = this.mesConditionBuilder.init(params, fields);
    return this.deviceNCSRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public DeviceNCS findById(long deviceNCSId) {
    return this.deviceNCSRepository
        .fetchById(deviceNCSId)
        .orElseThrow(() -> new DatumNotExistException("DeviceNCS", deviceNCSId));
  }

  @Override
  public DeviceNCS createDeviceNCS(Map<String, Object> params) {
    DeviceNCS newDeviceNCS = DeviceNCSHelper.assignDeviceNCS(new DeviceNCS(), params);

    boolean conflict =
        this.deviceNCSRepository.findFirstByNcsCode(newDeviceNCS.ncsCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("设备数控系统编码 {0} 已经存在", newDeviceNCS.ncsCode());
    }

    this.checkBeforeCreate(newDeviceNCS);
    this.validateDeviceNCS(newDeviceNCS);
    return this.deviceNCSRepository.save(newDeviceNCS);
  }

  @Override
  public DeviceNCS replaceDeviceNCS(long deviceNCSId, Map<String, Object> params) {
    DeviceNCS prevDeviceNCS = this.findById(deviceNCSId);

    ParamReader reader = ParamReader.init(params);
    String newDeviceNCSCode = reader.stringFromKey("ncsCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newDeviceNCSCode)
            && !newDeviceNCSCode.equals(prevDeviceNCS.ncsCode())
            && this.deviceNCSRepository.findFirstByNcsCode(newDeviceNCSCode).isPresent();
    if (conflict) {
      throw new BadRequestException("设备数控系统编码 {0} 已经存在", newDeviceNCSCode);
    }

    this.checkBeforeReplace(prevDeviceNCS);
    DeviceNCS currDeviceNCS = DeviceNCSHelper.assignDeviceNCS(prevDeviceNCS, params);
    this.validateDeviceNCS(currDeviceNCS);
    return this.deviceNCSRepository.save(currDeviceNCS);
  }

  @Override
  public void deleteDeviceNCS(long deviceNCSId) {
    DeviceNCS prevDeviceNCS = this.findById(deviceNCSId);
    this.checkBeforeDelete(prevDeviceNCS);
    this.deviceNCSRepository.delete(prevDeviceNCS);
  }
}
