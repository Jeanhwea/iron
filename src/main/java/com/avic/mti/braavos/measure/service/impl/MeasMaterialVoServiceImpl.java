package com.avic.mti.iron.measure.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasMaterialVo;
import com.avic.mti.iron.measure.domain.repo.MeasMaterialVoRepository;
import com.avic.mti.iron.measure.service.MeasMaterialVoService;
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
public class MeasMaterialVoServiceImpl implements MeasMaterialVoService {

  public static final Logger logger = LoggerFactory.getLogger(MeasMaterialVoServiceImpl.class);

  private final MeasMaterialVoRepository measMaterialVoRepository;
  private final MesConditionBuilder<MeasMaterialVo> mesConditionBuilder;

  @Autowired
  public MeasMaterialVoServiceImpl(
      MeasMaterialVoRepository measMaterialVoRepository,
      MesConditionBuilder<MeasMaterialVo> mesConditionBuilder) {
    this.measMaterialVoRepository = measMaterialVoRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MeasMaterialVo> findMeasMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MeasMaterialVo> builder = this.mesConditionBuilder.init(params, fields);
    return this.measMaterialVoRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<MeasMaterialVo> findAllMeasMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MeasMaterialVo> builder = this.mesConditionBuilder.init(params, fields);
    return this.measMaterialVoRepository.findAll(builder.spec());
  }
}
