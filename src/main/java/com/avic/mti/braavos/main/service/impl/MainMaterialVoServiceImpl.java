package com.avic.mti.iron.main.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainMaterialVo;
import com.avic.mti.iron.main.domain.repo.MainMaterialVoRepository;
import com.avic.mti.iron.main.service.MainMaterialVoService;
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
public class MainMaterialVoServiceImpl implements MainMaterialVoService {

  public static final Logger logger = LoggerFactory.getLogger(MainMaterialVoServiceImpl.class);

  private final MainMaterialVoRepository mainMaterialVoRepository;
  private final MesConditionBuilder<MainMaterialVo> mesConditionBuilder;

  @Autowired
  public MainMaterialVoServiceImpl(
      MainMaterialVoRepository mainMaterialVoRepository,
      MesConditionBuilder<MainMaterialVo> mesConditionBuilder) {
    this.mainMaterialVoRepository = mainMaterialVoRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MainMaterialVo> findMainMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MainMaterialVo> builder = this.mesConditionBuilder.init(params, fields);
    return this.mainMaterialVoRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<MainMaterialVo> findAllMainMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MainMaterialVo> builder = this.mesConditionBuilder.init(params, fields);
    return this.mainMaterialVoRepository.findAll(builder.spec());
  }
}
