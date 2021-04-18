package com.avic.mti.iron.main.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.exception.WrongParameterFormatException;
import com.avic.mti.iron.common.helper.NameCodeHelper;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.main.RetestStatusEnum;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.RetestDetail;
import com.avic.mti.iron.main.domain.repo.MainInRepository;
import com.avic.mti.iron.main.domain.repo.RetestDetailRepository;
import com.avic.mti.iron.main.helper.RetestDetailHelper;
import com.avic.mti.iron.main.service.RetestDetailService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetestDetailServiceImpl implements RetestDetailService {

  public static final Logger logger = LoggerFactory.getLogger(RetestDetailServiceImpl.class);

  private final RetestDetailRepository retestDetailRepository;

  private final MainInRepository mainInRepository;

  private final MesConditionBuilder<RetestDetail> mesConditionBuilder;

  @Autowired
  public RetestDetailServiceImpl(
      RetestDetailRepository retestDetailRepository,
      MainInRepository mainInRepository,
      MesConditionBuilder<RetestDetail> mesConditionBuilder) {
    this.retestDetailRepository = retestDetailRepository;
    this.mainInRepository = mainInRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateRetestDetail(RetestDetail retestDetail) {
    if (NameCodeHelper.validateNameCode(retestDetail.creatorNC())) {
      throw new WrongParameterFormatException("创建人", "名称[编码]", retestDetail.creatorNC());
    }

    if (StringHelper.isNonBlank(retestDetail.retestStatus())
        && RetestStatusEnum.of(retestDetail.retestStatus()) == RetestStatusEnum.Unknown) {
      throw new UnknownEnumException(
          "retestStatus", retestDetail.retestStatus(), RetestStatusEnum.expect());
    }
  }

  private void checkBeforeCreate(RetestDetail retestDetail) {
    // pass
  }

  private void checkBeforeReplace(RetestDetail retestDetail) {
    // pass
  }

  private void checkBeforeDelete(RetestDetail retestDetail) {
    // pass
  }

  private void syncMainInRetestStatus(List<RetestDetail> retestDetails) {
    if (retestDetails.isEmpty()) {
      throw new BadRequestException("待创建的主材复验明细列表为空");
    }
    RetestDetail firstRetestDetail = retestDetails.get(0);

    List<Long> inIdList =
        retestDetails.stream().map(RetestDetail::inId).distinct().collect(Collectors.toList());

    List<MainIn> mainIns = mainInRepository.findByIdIn(inIdList);

    for (MainIn mainIn : mainIns) {
      mainIn.retestId(firstRetestDetail.retestId());
      MainIn savedMainIn = this.mainInRepository.save(mainIn);
      RetestStatusEnum retestStatusEnum =
          this.retestDetailRepository
                  .findFirstByRetestIdAndRetestStatus(
                      firstRetestDetail.retestId(), RetestStatusEnum.Enum2_BHG.symbol())
                  .isPresent()
              ? RetestStatusEnum.Enum2_BHG
              : RetestStatusEnum.Enum1_HG;
      int countInUpdate =
          this.mainInRepository.updateRetestStatus(
              retestStatusEnum.symbol(), savedMainIn.mtlBatch(), savedMainIn.mainId());
      logger.debug(
          "完成主材入库 {} 的复验状态同步工作: 同步了 {} 条入库记录状态为 {}",
          savedMainIn,
          countInUpdate,
          retestStatusEnum.symbol());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Page<RetestDetail> findRetestDetails(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<RetestDetail> builder = this.mesConditionBuilder.init(params, fields);
    return this.retestDetailRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<RetestDetail> findAllRetestDetails(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<RetestDetail> builder = this.mesConditionBuilder.init(params, fields);
    return this.retestDetailRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public RetestDetail findById(long retestDetailId) {
    return this.retestDetailRepository
        .fetchById(retestDetailId)
        .orElseThrow(() -> new DatumNotExistException("RetestDetail", retestDetailId));
  }

  @Override
  public RetestDetail createRetestDetail(Map<String, Object> params) {
    RetestDetail newRetestDetail =
        RetestDetailHelper.assignRetestDetail(new RetestDetail(), params);

    this.checkBeforeCreate(newRetestDetail);
    this.validateRetestDetail(newRetestDetail);

    RetestDetail savedRetestDetail = this.retestDetailRepository.save(newRetestDetail);
    return savedRetestDetail;
  }

  @Override
  public RetestDetail replaceRetestDetail(long retestId, Map<String, Object> params) {
    RetestDetail prevRetestDetail = this.findById(retestId);

    this.checkBeforeReplace(prevRetestDetail);
    RetestDetail currRetestDetail = RetestDetailHelper.assignRetestDetail(prevRetestDetail, params);
    this.validateRetestDetail(currRetestDetail);
    return this.retestDetailRepository.save(currRetestDetail);
  }

  @Override
  public void deleteRetestDetail(long retestId) {
    RetestDetail prevRetestDetail = this.findById(retestId);
    this.checkBeforeDelete(prevRetestDetail);
    this.retestDetailRepository.delete(prevRetestDetail);
  }

  @Override
  public List<RetestDetail> createRetestDetailInBulk(List<Map<String, Object>> paramList) {
    List<RetestDetail> retestDetails =
        paramList.stream().map(this::createRetestDetail).collect(Collectors.toList());
    logger.debug("创建了 {} 条主材复验明细", retestDetails.size());

    syncMainInRetestStatus(retestDetails);

    return retestDetails;
  }

  @Override
  public void removeRetestDetailInBulk(Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    String updateUser =
        reader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("无法获取更新用户 updateUser 参数"));
    long retestId =
        reader
            .longFromKey("retestId")
            .orElseThrow(() -> new BadRequestException("无法获取 retestId 参数"));
    int countRemoved = this.retestDetailRepository.deleteByRetestId(retestId);
    logger.info("用户 {} 清空了主材复验 {} 对应的 {} 条明细列表", updateUser, retestId, countRemoved);
  }
}
