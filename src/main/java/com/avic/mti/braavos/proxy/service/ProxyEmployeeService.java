package com.avic.mti.iron.proxy.service;

import java.util.Map;
import java.util.Optional;

public interface ProxyEmployeeService {
  Optional<Map<String, Object>> obtainEmployeeByCode(String employeeCode);
}
