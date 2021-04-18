package com.avic.mti.iron.tool.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.domain.entity.ToolLog;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import com.avic.mti.iron.tool.domain.repo.ToolLogRepository;
import com.avic.mti.iron.tool.helper.ToolLogHelper;
import com.avic.mti.iron.tool.service.ToolLogService;
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
public class ToolLogServiceImpl implements ToolLogService {

  public static final Logger logger = LoggerFactory.getLogger(ToolLogServiceImpl.class);

  private final ToolLogRepository toolLogRepository;
  private final MesConditionBuilder<ToolLog> mesConditionBuilder;

  @Autowired
  public ToolLogServiceImpl(
      ToolLogRepository toolLogRepository, MesConditionBuilder<ToolLog> mesConditionBuilder) {
    this.toolLogRepository = toolLogRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ToolLog> findToolLogs(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<ToolLog> builder = this.mesConditionBuilder.init(params, fields);
    return this.toolLogRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<ToolLog> findAllToolLogs(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<ToolLog> builder = this.mesConditionBuilder.init(params, fields);
    return this.toolLogRepository.findAll(builder.spec());
  }

  @Override
  public ToolLog createToolLogForIn(ToolIn toolIn) {
    ToolLog toolLog = ToolLogHelper.consToolLogForIn(new ToolLog(), toolIn);
    return this.toolLogRepository.save(toolLog);
  }

  @Override
  public ToolLog createToolLogForOut(ToolOut toolOut) {
    ToolLog toolLog = ToolLogHelper.consToolLogForOut(new ToolLog(), toolOut);
    return this.toolLogRepository.save(toolLog);
  }
}
