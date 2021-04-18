package com.avic.mti.iron.tool.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolMaterialVo;
import com.avic.mti.iron.tool.domain.repo.ToolMaterialVoRepository;
import com.avic.mti.iron.tool.service.ToolMaterialVoService;
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
public class ToolMaterialVoServiceImpl implements ToolMaterialVoService {

  public static final Logger logger = LoggerFactory.getLogger(ToolMaterialVoServiceImpl.class);

  private final ToolMaterialVoRepository toolMaterialVoRepository;
  private final MesConditionBuilder<ToolMaterialVo> mesConditionBuilder;

  @Autowired
  public ToolMaterialVoServiceImpl(
      ToolMaterialVoRepository toolMaterialVoRepository,
      MesConditionBuilder<ToolMaterialVo> mesConditionBuilder) {
    this.toolMaterialVoRepository = toolMaterialVoRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ToolMaterialVo> findToolMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<ToolMaterialVo> builder = this.mesConditionBuilder.init(params, fields);
    return this.toolMaterialVoRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<ToolMaterialVo> findAllToolMaterialVos(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<ToolMaterialVo> builder = this.mesConditionBuilder.init(params, fields);
    return this.toolMaterialVoRepository.findAll(builder.spec());
  }
}
