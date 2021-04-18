package com.avic.mti.iron.proxy.service.impl;

import com.avic.mti.iron.common.constant.AppNameConst;
import com.avic.mti.iron.common.http.proxy.Eunuch;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import com.avic.mti.iron.proxy.service.ProxyEmployeeService;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxyEmployeeServiceImpl implements ProxyEmployeeService {
  public static final Logger logger = LoggerFactory.getLogger(ProxyEmployeeServiceImpl.class);

  private final Eunuch eunuch;

  @Autowired
  public ProxyEmployeeServiceImpl(Eunuch eunuch) {
    this.eunuch = eunuch;
  }

  @Override
  public Optional<Map<String, Object>> obtainEmployeeByCode(String employeeCode) {
    Map<String, Object> params = ParamBuilder.init().put("code_eq", employeeCode).params();
    return eunuch.getForList(AppNameConst.SKREE_NAME, "/employees", params).stream().findFirst();
  }
}
