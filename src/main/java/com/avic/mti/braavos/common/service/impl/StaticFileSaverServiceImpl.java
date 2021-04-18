package com.avic.mti.iron.common.service.impl;

import com.avic.mti.iron.common.exception.InternalServerErrorException;
import com.avic.mti.iron.common.exception.MissingConfigKeyException;
import com.avic.mti.iron.common.helper.FileHelper;
import com.avic.mti.iron.common.helper.IdHelper;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.props.PathProps;
import com.avic.mti.iron.common.service.FileSaverService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 存取静态文件的实现类
 *
 * @author Jinghui Hu
 * @since 2020-09-04, JDK1.8
 */
@Service
public class StaticFileSaverServiceImpl implements FileSaverService {

  public static final Logger logger = LoggerFactory.getLogger(StaticFileSaverServiceImpl.class);

  private final PathProps pathProps;

  @Autowired
  public StaticFileSaverServiceImpl(PathProps pathProps) {
    this.pathProps = pathProps;
  }

  private String staticRoot() {
    String staticRoot = this.pathProps.getStaticRoot();
    if (StringHelper.isBlank(staticRoot)) {
      throw new MissingConfigKeyException("app.path.static-root");
    }

    return FileHelper.localPath(staticRoot);
  }

  private String consLocation(String basePath, String fileName) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MMdd/HHmm");
    String shimPath = dateFormat.format(new Date());
    return basePath == null
        ? FileHelper.coercePath(String.format("%s/%s", shimPath, fileName))
        : FileHelper.coercePath(String.format("%s/%s/%s", basePath, shimPath, fileName));
  }

  @Override
  public String store(byte[] fileBytes, String basePath, String fileName) {
    String location = consLocation(basePath, fileName);
    String fullName = FileHelper.localPath(String.format("%s/%s", staticRoot(), location));
    if (FileHelper.writeBytes(fileBytes, fullName)) {
      logger.debug("Saved {}", fullName);
    } else {
      throw new InternalServerErrorException("Failed to save {0}", fullName);
    }

    return location;
  }

  @Override
  public String store(byte[] fileBytes, String fileName) {
    return store(fileBytes, null, fileName);
  }

  @Override
  public String store(byte[] fileBytes) {
    String fileName = IdHelper.getOrdinalN(16);
    return store(fileBytes, null, fileName);
  }

  @Override
  public byte[] withdraw(String location) {
    String filename = FileHelper.localPath(String.format("%s/%s", staticRoot(), location));
    byte[] data = FileHelper.readBytes(filename);
    if (data != null) {
      logger.debug("Load {}", filename);
    } else {
      logger.warn("Failed to load {}", filename);
    }

    return data;
  }
}
