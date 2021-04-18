package com.avic.mti.iron.measure.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
import com.avic.mti.iron.measure.domain.entity.MeasLog;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import com.avic.mti.iron.measure.domain.repo.MeasLogRepository;
import com.avic.mti.iron.measure.helper.MeasLogHelper;
import com.avic.mti.iron.measure.service.MeasLogService;
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
public class MeasLogServiceImpl implements MeasLogService {

  public static final Logger logger = LoggerFactory.getLogger(MeasLogServiceImpl.class);

  private final MeasLogRepository measLogRepository;
  private final MesConditionBuilder<MeasLog> mesConditionBuilder;

  @Autowired
  public MeasLogServiceImpl(
      MeasLogRepository measLogRepository, MesConditionBuilder<MeasLog> mesConditionBuilder) {
    this.measLogRepository = measLogRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MeasLog> findMeasLogs(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MeasLog> builder = this.mesConditionBuilder.init(params, fields);
    return this.measLogRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<MeasLog> findAllMeasLogs(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MeasLog> builder = this.mesConditionBuilder.init(params, fields);
    return this.measLogRepository.findAll(builder.spec());
  }

  @Override
  public MeasLog createMeasLogForIn(MeasIn measIn) {
    MeasLog measLog = MeasLogHelper.consMeasLogForIn(new MeasLog(), measIn);
    return this.measLogRepository.save(measLog);
  }

  @Override
  public MeasLog createMeasLogForOut(MeasOut measOut) {
    MeasLog measLog = MeasLogHelper.consMeasLogForOut(new MeasLog(), measOut);
    return this.measLogRepository.save(measLog);
  }
}
