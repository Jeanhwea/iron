package com.avic.mti.iron.main.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.MainLog;
import com.avic.mti.iron.main.domain.entity.MainOut;
import com.avic.mti.iron.main.domain.repo.MainLogRepository;
import com.avic.mti.iron.main.helper.MainLogHelper;
import com.avic.mti.iron.main.service.MainLogService;
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
public class MainLogServiceImpl implements MainLogService {

  public static final Logger logger = LoggerFactory.getLogger(MainLogServiceImpl.class);

  private final MainLogRepository mainLogRepository;
  private final MesConditionBuilder<MainLog> mesConditionBuilder;

  @Autowired
  public MainLogServiceImpl(
      MainLogRepository mainLogRepository, MesConditionBuilder<MainLog> mesConditionBuilder) {
    this.mainLogRepository = mainLogRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MainLog> findMainLogs(Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MainLog> builder = this.mesConditionBuilder.init(params, fields);
    return this.mainLogRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public List<MainLog> findAllMainLogs(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MainLog> builder = this.mesConditionBuilder.init(params, fields);
    return this.mainLogRepository.findAll(builder.spec());
  }

  @Override
  public MainLog createMainLogForIn(MainIn mainIn) {
    MainLog mainLog = MainLogHelper.consMainLogForIn(new MainLog(), mainIn);
    return this.mainLogRepository.save(mainLog);
  }

  @Override
  public MainLog createMainLogForOut(MainOut mainOut) {
    MainLog mainLog = MainLogHelper.consMainLogForOut(new MainLog(), mainOut);
    return this.mainLogRepository.save(mainLog);
  }
}
