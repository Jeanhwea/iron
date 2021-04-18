package com.avic.mti.iron.main.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.WrongParameterFormatException;
import com.avic.mti.iron.common.helper.NameCodeHelper;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.main.RetestStatusEnum;
import com.avic.mti.iron.main.domain.entity.Retest;
import com.avic.mti.iron.main.domain.repo.MainInRepository;
import com.avic.mti.iron.main.domain.repo.RetestRepository;
import com.avic.mti.iron.main.helper.RetestHelper;
import com.avic.mti.iron.main.service.RetestService;
import java.io.IOException;
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
public class RetestServiceImpl implements RetestService {

  public static final Logger logger = LoggerFactory.getLogger(RetestServiceImpl.class);

  private final RetestRepository retestRepository;
  private final MainInRepository mainInRepository;

  private final MesConditionBuilder<Retest> mesConditionBuilder;

  @Autowired
  public RetestServiceImpl(
      RetestRepository retestRepository,
      MainInRepository mainInRepository,
      MesConditionBuilder<Retest> mesConditionBuilder) {
    this.retestRepository = retestRepository;
    this.mainInRepository = mainInRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateRetest(Retest retest) {
    if (NameCodeHelper.validateNameCode(retest.creatorNC())) {
      throw new WrongParameterFormatException("创建人", "名称[编码]", retest.creatorNC());
    }
  }

  private void checkBeforeCreate(Retest retest) {
    // pass
  }

  private void checkBeforeReplace(Retest retest) {
    // pass
  }

  private void checkBeforeDelete(Retest retest) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Retest> findRetests(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<Retest> builder = this.mesConditionBuilder.init(params, fields);
    return this.retestRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<Retest> findAllRetests(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<Retest> builder = this.mesConditionBuilder.init(params, fields);
    return this.retestRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public Retest findById(long retestId) {
    return this.retestRepository
        .fetchById(retestId)
        .orElseThrow(() -> new DatumNotExistException("Retest", retestId));
  }

  @Override
  public Retest createRetest(Map<String, Object> params) {
    Retest newRetest = RetestHelper.assignRetest(new Retest(), params);

    boolean conflict =
        this.retestRepository.findFirstByRetestCode(newRetest.retestCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("主材复验编码 {0} 已经存在", newRetest.retestCode());
    }

    this.checkBeforeCreate(newRetest);
    this.validateRetest(newRetest);

    Retest savedRetest = this.retestRepository.save(newRetest);
    return savedRetest;
  }

  @Override
  public Retest replaceRetest(long retestId, Map<String, Object> params) {
    Retest prevRetest = this.findById(retestId);

    ParamReader reader = ParamReader.init(params);
    String newRetestCode = reader.stringFromKey("retestCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newRetestCode)
            && !newRetestCode.equals(prevRetest.retestCode())
            && this.retestRepository.findFirstByRetestCode(newRetestCode).isPresent();
    if (conflict) {
      throw new BadRequestException("主材复验编码 {0} 已经存在", newRetestCode);
    }

    this.checkBeforeReplace(prevRetest);
    Retest currRetest = RetestHelper.assignRetest(prevRetest, params);
    this.validateRetest(currRetest);
    return this.retestRepository.save(currRetest);
  }

  @Override
  public void deleteRetest(long retestId) {
    Retest prevRetest = this.findById(retestId);
    this.checkBeforeDelete(prevRetest);
    int countMainIn =
        this.mainInRepository.clearRetestStatus(
            RetestStatusEnum.Enum2_BHG.symbol(), prevRetest.id());
    logger.info("删除主材复验报告时，影响了 {} 条主材入库的复验状态", countMainIn);
    this.retestRepository.delete(prevRetest);
  }

  @Override
  public byte[] getRetestFile(long retestId) {
    Retest retest = this.findById(retestId);
    if (!retest.hasRetestFile()) {
      throw new BadRequestException("无法找到复验 {0} 的附件", retest);
    }
    return retest.retestFileBlob();
  }

  @Override
  public void setRetestFile(String updateUser, long retestId, MultipartFile multipartFile) {
    try {
      byte[] bytes = multipartFile.getBytes();
      Retest retest = this.findById(retestId);
      retest.updateUser(updateUser);
      retest.retestFileBlob(bytes);
      this.retestRepository.save(retest);
    } catch (IOException e) {
      throw new BadRequestException("无法读取上传的文件内容");
    }
  }
}
