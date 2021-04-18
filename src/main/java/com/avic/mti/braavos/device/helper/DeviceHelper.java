package com.avic.mti.iron.device.helper;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.device.domain.entity.Device;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class DeviceHelper {

  public static final Logger logger = LoggerFactory.getLogger(DeviceHelper.class);

  public static Device assignDevice(Device device, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(device::updateUser);

    reader.stringFromKey("devcCode").ifPresent(device::devcCode);
    reader.stringFromKey("devcName").ifPresent(device::devcName);
    reader.stringFromKey("devcSpec").ifPresent(device::devcSpec);
    reader.longFromKey("devcCateId").ifPresent(device::devcCateId);
    reader.stringFromKey("devcDept").ifPresent(device::devcDept);
    reader.stringFromKey("devcAdmin").ifPresent(device::devcAdmin);
    reader.stringFromKey("devcMaker").ifPresent(device::devcMaker);
    reader.stringFromKey("devcStatus").ifPresent(device::devcStatus);
    reader.stringFromKey("devcType").ifPresent(device::devcType);
    reader.stringFromKey("inType").ifPresent(device::inType);
    reader.stringFromKey("inStub").ifPresent(device::inStub);
    reader.stringFromKey("inNC").ifPresent(device::inNC);
    reader.timeFromKey("inDate").ifPresent(device::inDate);
    reader.longFromKey("maxJobCount").ifPresent(device::maxJobCount);
    reader.timeFromKey("serviceLife").ifPresent(device::serviceLife);
    reader.longFromKey("devcNCSId").ifPresent(device::devcNCSId);
    reader.stringFromKey("devcPower").ifPresent(device::devcPower);
    reader.stringFromKey("connStatus").ifPresent(device::connStatus);
    reader.stringFromKey("asset").ifPresent(device::asset);
    reader.stringFromKey("assetCate").ifPresent(device::assetCate);
    reader.stringFromKey("maintPeriod").ifPresent(device::maintPeriod);
    reader.stringFromKey("maintRecord").ifPresent(device::maintRecord);
    reader.stringFromKey("note").ifPresent(device::note);
    reader.stringFromKey("mainPara").ifPresent(device::mainPara);
    reader.stringFromKey("devcImageName").ifPresent(device::devcImageName);
    reader.stringFromKey("shelfJson").ifPresent(device::shelfJson);

    reader.doubleFromKey("dblDevcVar01").ifPresent(device::dblDevcVar01);
    reader.doubleFromKey("dblDevcVar02").ifPresent(device::dblDevcVar02);
    reader.doubleFromKey("dblDevcVar03").ifPresent(device::dblDevcVar03);
    reader.doubleFromKey("dblDevcVar04").ifPresent(device::dblDevcVar04);
    reader.longFromKey("intDevcVar01").ifPresent(device::intDevcVar01);
    reader.longFromKey("intDevcVar02").ifPresent(device::intDevcVar02);
    reader.longFromKey("intDevcVar03").ifPresent(device::intDevcVar03);
    reader.longFromKey("intDevcVar04").ifPresent(device::intDevcVar04);
    reader.stringFromKey("strDevcVar01").ifPresent(device::strDevcVar01);
    reader.stringFromKey("strDevcVar02").ifPresent(device::strDevcVar02);
    reader.stringFromKey("strDevcVar03").ifPresent(device::strDevcVar03);
    reader.stringFromKey("strDevcVar04").ifPresent(device::strDevcVar04);
    reader.stringFromKey("strDevcVar05").ifPresent(device::strDevcVar05);
    reader.stringFromKey("strDevcVar06").ifPresent(device::strDevcVar06);
    reader.stringFromKey("strDevcVar07").ifPresent(device::strDevcVar07);
    reader.stringFromKey("strDevcVar08").ifPresent(device::strDevcVar08);

    return device;
  }

  public static byte[] readFile(MultipartFile multipartFile) {
    if (multipartFile.getSize() > 50 * 1024 * 1024) {
      throw new BadRequestException("附件大小不能超过 50M");
    }

    try {
      return multipartFile.getBytes();
    } catch (IOException e) {
      logger.warn("读取上传的文件出现问题: {}, {}", e.getMessage(), Arrays.toString(e.getStackTrace()));
      throw new BadRequestException("无法读取上传的 PDF 文件");
    }
  }

  public DeviceHelper() {}
}
