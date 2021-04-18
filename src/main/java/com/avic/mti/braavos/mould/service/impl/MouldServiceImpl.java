package com.avic.mti.iron.mould.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.asset.AssetInTypeEnum;
import com.avic.mti.iron.mould.domain.entity.Mould;
import com.avic.mti.iron.mould.domain.repo.MouldRepository;
import com.avic.mti.iron.mould.helper.MouldHelper;
import com.avic.mti.iron.mould.service.MouldService;
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
public class MouldServiceImpl implements MouldService {

  public static final Logger logger = LoggerFactory.getLogger(MouldServiceImpl.class);

  private final MouldRepository mouldRepository;
  private final MesConditionBuilder<Mould> mesConditionBuilder;

  @Autowired
  public MouldServiceImpl(
      MouldRepository mouldRepository, MesConditionBuilder<Mould> mesConditionBuilder) {
    this.mouldRepository = mouldRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateMould(Mould mould) {
    if (AssetInTypeEnum.of(mould.inType()) == AssetInTypeEnum.Unknown) {
      throw new UnknownEnumException("inType", mould.inType(), AssetInTypeEnum.expect());
    }
  }

  private void checkBeforeCreate(Mould mould) {
    // pass
  }

  private void checkBeforeReplace(Mould mould) {
    // pass
  }

  private void checkBeforeDelete(Mould mould) {
    // pass
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Mould> findMoulds(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<Mould> builder = this.mesConditionBuilder.init(params, fields);
    return this.mouldRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  public List<Mould> findAllMoulds(Map<String, Object> params) {
    List<String> moldCodes =
        ParamReader.init(params)
            .listStringFromKey("moldCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 moldCodes 参数"));
    return this.mouldRepository.findAllByMoldCodeIn(moldCodes);
  }

  @Override
  public List<Mould> findIdleMoulds(Map<String, Object> params) {
    List<String> moldCodes =
        ParamReader.init(params)
            .listStringFromKey("moldCodes")
            .orElseThrow(() -> new BadRequestException("无法获取 moldCodes 参数"));
    if (moldCodes.isEmpty()) {
      return this.mouldRepository.fetchAll();
    }

    return this.mouldRepository.findAllByMoldCodeNotIn(moldCodes);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Mould> findAllMoulds(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<Mould> builder = this.mesConditionBuilder.init(params, fields);
    return this.mouldRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public Mould findById(long mouldId) {
    return this.mouldRepository
        .fetchById(mouldId)
        .orElseThrow(() -> new DatumNotExistException("Mould", mouldId));
  }

  @Override
  public Mould createMould(Map<String, Object> params) {
    Mould newMould = MouldHelper.assignMould(new Mould(), params);

    boolean conflict = this.mouldRepository.findFirstByMoldCode(newMould.moldCode()).isPresent();
    if (conflict) {
      throw new BadRequestException("模具编码 {0} 已经存在", newMould.moldCode());
    }

    this.checkBeforeCreate(newMould);
    this.validateMould(newMould);
    return this.mouldRepository.save(newMould);
  }

  @Override
  public Mould replaceMould(long mouldId, Map<String, Object> params) {
    Mould prevMould = this.findById(mouldId);

    ParamReader reader = ParamReader.init(params);
    String newMouldCode = reader.stringFromKey("moldCode").orElse(null);
    boolean conflict =
        StringHelper.isNonBlank(newMouldCode)
            && !newMouldCode.equals(prevMould.moldCode())
            && this.mouldRepository.findFirstByMoldCode(newMouldCode).isPresent();
    if (conflict) {
      throw new BadRequestException("模具编码 {0} 已经存在", newMouldCode);
    }

    this.checkBeforeReplace(prevMould);
    Mould currMould = MouldHelper.assignMould(prevMould, params);
    this.validateMould(currMould);
    return this.mouldRepository.save(currMould);
  }

  @Override
  public void deleteMould(long mouldId) {
    Mould prevMould = this.findById(mouldId);
    this.checkBeforeDelete(prevMould);
    this.mouldRepository.delete(prevMould);
  }

  @Override
  public List<Mould> findIdleMoulds() {
    return this.mouldRepository.findIdleMoulds();
  }
}
