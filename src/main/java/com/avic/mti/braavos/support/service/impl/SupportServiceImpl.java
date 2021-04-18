package com.avic.mti.iron.support.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.asset.AssetInTypeEnum;
import com.avic.mti.iron.support.domain.entity.Support;
import com.avic.mti.iron.support.domain.repo.SupportRepository;
import com.avic.mti.iron.support.helper.SupportHelper;
import com.avic.mti.iron.support.service.SupportService;
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
public class SupportServiceImpl implements SupportService {

  public static final Logger logger = LoggerFactory.getLogger(SupportServiceImpl.class);

  private final SupportRepository supportRepository;
  private final MesConditionBuilder<Support> mesConditionBuilder;

  @Autowired
  public SupportServiceImpl(
      SupportRepository supportRepository, MesConditionBuilder<Support> mesConditionBuilder) {
    this.supportRepository = supportRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateSupport(Support support) {
    if (AssetInTypeEnum.of(support.inType()) == AssetInTypeEnum.Unknown) {
      throw new UnknownEnumException("inType", support.inType(), AssetInTypeEnum.expect());
    }
  }

  private void checkBeforeCreate(Support support) {
    // pass
  }

  private void checkBeforeReplace(Support support) {
    // pass
  }

  private void checkBeforeDelete(Support support) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Support> findSupports(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<Support> builder = this.mesConditionBuilder.init(params, fields);
    return this.supportRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<Support> findAllSupports(Map<String, Object> params) {
    List<String> suptCodes =
        ParamReader.init(params)
            .listStringFromKey("suptCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 suptCodes 参数"));
    return this.supportRepository.findAllBySuptCodeIn(suptCodes);
  }

  @Override
  public List<Support> findIdleSupports(Map<String, Object> params) {
    List<String> suptCodes =
        ParamReader.init(params)
            .listStringFromKey("suptCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 suptCodes 参数"));
    if (suptCodes.isEmpty()) {
      return this.supportRepository.fetchAll();
    }

    return this.supportRepository.findAllBySuptCodeNotIn(suptCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Support> findAllSupports(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<Support> builder = this.mesConditionBuilder.init(params, fields);
    return this.supportRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public Support findById(long supportId) {
    return this.supportRepository
        .fetchById(supportId)
        .orElseThrow(() -> new DatumNotExistException("Support", supportId));
  }

  @Override
  public Support createSupport(Map<String, Object> params) {
    Support newSupport = SupportHelper.assignSupport(new Support(), params);

    boolean conflict =
        this.supportRepository.findFirstBySuptCode(newSupport.suptCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("配套编码 {0} 已经存在", newSupport.suptCode());
    }

    this.checkBeforeCreate(newSupport);
    this.validateSupport(newSupport);
    return this.supportRepository.save(newSupport);
  }

  @Override
  public Support replaceSupport(long supportId, Map<String, Object> params) {
    Support prevSupport = this.findById(supportId);

    ParamReader reader = ParamReader.init(params);
    String newSupportCode = reader.stringFromKey("suptCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newSupportCode)
            && !newSupportCode.equals(prevSupport.suptCode())
            && this.supportRepository.findFirstBySuptCode(newSupportCode).isPresent();
    if (conflict) {
      throw new BadRequestException("配套编码 {0} 已经存在", newSupportCode);
    }

    this.checkBeforeReplace(prevSupport);
    Support currSupport = SupportHelper.assignSupport(prevSupport, params);
    this.validateSupport(currSupport);
    return this.supportRepository.save(currSupport);
  }

  @Override
  public void deleteSupport(long supportId) {
    Support prevSupport = this.findById(supportId);
    this.checkBeforeDelete(prevSupport);
    this.supportRepository.delete(prevSupport);
  }

  @Override
  public List<Support> findIdleSupports() {
    return this.supportRepository.findIdleSupports();
  }
}
