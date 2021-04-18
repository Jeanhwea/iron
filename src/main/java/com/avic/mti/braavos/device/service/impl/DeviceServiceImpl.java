package com.avic.mti.iron.device.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.asset.AssetCateEnum;
import com.avic.mti.iron.common.symbol.asset.AssetInTypeEnum;
import com.avic.mti.iron.common.symbol.device.DeviceConnStatusEnum;
import com.avic.mti.iron.common.symbol.device.DeviceStatusEnum;
import com.avic.mti.iron.device.domain.entity.Device;
import com.avic.mti.iron.device.domain.repo.DeviceRepository;
import com.avic.mti.iron.device.helper.DeviceHelper;
import com.avic.mti.iron.device.service.DeviceService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

  public static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

  private final DeviceRepository deviceRepository;
  private final MesConditionBuilder<Device> mesConditionBuilder;

  @Autowired
  public DeviceServiceImpl(
      DeviceRepository deviceRepository, MesConditionBuilder<Device> mesConditionBuilder) {
    this.deviceRepository = deviceRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateDevice(Device device) {
    if (StringHelper.isNonBlank(device.devcStatus())
        && DeviceStatusEnum.of(device.devcStatus()) == DeviceStatusEnum.Unknown) {
      throw new UnknownEnumException("devcStatus", device.devcStatus(), DeviceStatusEnum.expect());
    }

    if (StringHelper.isNonBlank(device.connStatus())
        && DeviceConnStatusEnum.of(device.connStatus()) == DeviceConnStatusEnum.Unknown) {
      throw new UnknownEnumException(
          "connStatus", device.connStatus(), DeviceConnStatusEnum.expect());
    }

    if (StringHelper.isNonBlank(device.inType())
        && AssetInTypeEnum.of(device.inType()) == AssetInTypeEnum.Unknown) {
      throw new UnknownEnumException("inType", device.inType(), AssetInTypeEnum.expect());
    }

    if (StringHelper.isNonBlank(device.assetCate())
        && AssetCateEnum.of(device.assetCate()) == AssetCateEnum.Unknown) {
      throw new UnknownEnumException("assetCate", device.assetCate(), AssetCateEnum.expect());
    }
  }

  private void checkBeforeCreate(Device device) {
    // pass
  }

  private void checkBeforeReplace(Device device) {
    // pass
  }

  private void checkBeforeDelete(Device device) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Device> findDevices(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<Device> builder = this.mesConditionBuilder.init(params, fields);
    return this.deviceRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<Device> findAllDevices(Map<String, Object> params) {
    List<String> devcCodes =
        ParamReader.init(params)
            .listStringFromKey("devcCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 devcCodes 参数"));
    return this.deviceRepository.findAllByDevcCodeIn(devcCodes);
  }

  @Override
  public List<Device> findIdleDevices(Map<String, Object> params) {
    List<String> devcCodes =
        ParamReader.init(params)
            .listStringFromKey("devcCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 devcCodes 参数"));
    if (devcCodes.isEmpty()) {
      return this.deviceRepository.fetchAll();
    }

    return this.deviceRepository.findAllByDevcCodeNotIn(devcCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Device> findAllDevices(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<Device> builder = this.mesConditionBuilder.init(params, fields);
    return this.deviceRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public Device findById(long deviceId) {
    return this.deviceRepository
        .fetchById(deviceId)
        .orElseThrow(() -> new DatumNotExistException("Device", deviceId));
  }

  @Override
  public Device createDevice(Map<String, Object> params) {
    Device newDevice = DeviceHelper.assignDevice(new Device(), params);

    boolean conflict = this.deviceRepository.findFirstByDevcCode(newDevice.devcCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("设备编码 {0} 已经存在", newDevice.devcCode());
    }

    this.checkBeforeCreate(newDevice);
    this.validateDevice(newDevice);
    return this.deviceRepository.save(newDevice);
  }

  @Override
  public Device replaceDevice(long deviceId, Map<String, Object> params) {
    Device prevDevice = this.findById(deviceId);

    ParamReader reader = ParamReader.init(params);
    String newDeviceCode = reader.stringFromKey("devcCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newDeviceCode)
            && !newDeviceCode.equals(prevDevice.devcCode())
            && this.deviceRepository.findFirstByDevcCode(newDeviceCode).isPresent();
    if (conflict) {
      throw new BadRequestException("设备编码 {0} 已经存在", newDeviceCode);
    }

    this.checkBeforeReplace(prevDevice);
    Device currDevice = DeviceHelper.assignDevice(prevDevice, params);
    this.validateDevice(currDevice);
    return this.deviceRepository.save(currDevice);
  }

  @Override
  public Device updateDeviceStatus(Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    String deviceCode = reader.stringFromKey("devcCode").orElse(null);
    String devcStatus = reader.stringFromKey("devcStatus").orElse(null);

    Device device =
        this.deviceRepository
            .findFirstByDevcCode(deviceCode)
            .orElseThrow(() -> new BadRequestException("无法找到 {0} 的设备", deviceCode));

    device.devcStatus(devcStatus);
    this.validateDevice(device);
    return this.deviceRepository.save(device);
  }

  @Override
  public void deleteDevice(long deviceId) {
    Device prevDevice = this.findById(deviceId);
    this.checkBeforeDelete(prevDevice);
    this.deviceRepository.delete(prevDevice);
  }

  @Override
  public byte[] getDeviceImage(long deviceId) {
    Device device = this.findById(deviceId);
    if (!device.hasImageFile()) {
      throw new BadRequestException("设备 {0} 不存在设备图片", device);
    }

    return device.devcImageFile();
  }

  @Override
  public void setDeviceImage(String updateUser, long deviceId, MultipartFile multipartFile) {
    Device device = this.findById(deviceId);
    byte[] imageBytes = DeviceHelper.readFile(multipartFile);
    device.updateUser(updateUser);
    device.devcImageFile(imageBytes);
    this.deviceRepository.save(device);
    logger.info("用户 {} 上传了设备 {} 的图片", updateUser, device);
  }

  @Override
  public List<Device> findIdleDevices() {
    return this.deviceRepository.findIdleDevices();
  }
}
