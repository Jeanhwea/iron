package com.avic.mti.iron.waste.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdMaterialVo;
import com.avic.mti.iron.waste.domain.repo.WprdMaterialVoRepository;
import com.avic.mti.iron.waste.service.WprdMaterialVoService;
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
public class WprdMaterialVoServiceImpl implements WprdMaterialVoService {

  public static final Logger logger = LoggerFactory.getLogger(WprdMaterialVoServiceImpl.class);

  private final WprdMaterialVoRepository wprdMaterialVoRepository;
  private final MesConditionBuilder<WprdMaterialVo> mesConditionBuilder;

  @Autowired
  public WprdMaterialVoServiceImpl(
      WprdMaterialVoRepository wprdMaterialVoRepository,
      MesConditionBuilder<WprdMaterialVo> mesConditionBuilder) {
    this.wprdMaterialVoRepository = wprdMaterialVoRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<WprdMaterialVo> findWprdMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<WprdMaterialVo> builder = this.mesConditionBuilder.init(params, fields);
    return this.wprdMaterialVoRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<WprdMaterialVo> findAllWprdMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<WprdMaterialVo> builder = this.mesConditionBuilder.init(params, fields);
    return this.wprdMaterialVoRepository.findAll(builder.spec());
  }
}
