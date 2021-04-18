package com.avic.mti.iron.waste.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.domain.entity.WprdLog;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import com.avic.mti.iron.waste.domain.repo.WprdLogRepository;
import com.avic.mti.iron.waste.helper.WprdLogHelper;
import com.avic.mti.iron.waste.service.WprdLogService;
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
public class WprdLogServiceImpl implements WprdLogService {

  public static final Logger logger = LoggerFactory.getLogger(WprdLogServiceImpl.class);

  private final WprdLogRepository wprdLogRepository;
  private final MesConditionBuilder<WprdLog> mesConditionBuilder;

  @Autowired
  public WprdLogServiceImpl(
      WprdLogRepository wprdLogRepository, MesConditionBuilder<WprdLog> mesConditionBuilder) {
    this.wprdLogRepository = wprdLogRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<WprdLog> findWprdLogs(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<WprdLog> builder = this.mesConditionBuilder.init(params, fields);
    return this.wprdLogRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<WprdLog> findAllWprdLogs(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<WprdLog> builder = this.mesConditionBuilder.init(params, fields);
    return this.wprdLogRepository.findAll(builder.spec());
  }

  @Override
  public WprdLog createWprdLogForIn(WprdIn wprdIn) {
    WprdLog wprdLog = WprdLogHelper.consWprdLogForIn(new WprdLog(), wprdIn);
    return this.wprdLogRepository.save(wprdLog);
  }

  @Override
  public WprdLog createWprdLogForOut(WprdOut wprdOut) {
    WprdLog wprdLog = WprdLogHelper.consWprdLogForOut(new WprdLog(), wprdOut);
    return this.wprdLogRepository.save(wprdLog);
  }
}
