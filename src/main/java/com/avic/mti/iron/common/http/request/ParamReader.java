package com.avic.mti.iron.common.http.request;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 参数读取类
 *
 * @author Jinghui Hu
 * @since 2020-04-20, JDK1.8
 */
public class ParamReader {

  /**
   * 初始化类
   *
   * @author Jinghui Hu
   * @since 2020-04-20, JDK1.8
   */
  public static ParamReader init(Map<String, Object> params) {
    return new ParamReader(params);
  }

  public Optional<Long> idFromKey(String key) {
    return Optional.ofNullable(this.params.get(key)).map(ParamMapper::toId);
  }

  public Optional<Long> longFromKey(String key) {
    return Optional.ofNullable(this.params.get(key)).map(ParamMapper::toLong);
  }

  public Optional<Integer> intFromKey(String key) {
    return Optional.ofNullable(this.params.get(key)).map(ParamMapper::toInteger);
  }

  public Optional<Double> doubleFromKey(String key) {
    return Optional.ofNullable(this.params.get(key)).map(ParamMapper::toDouble);
  }

  public Optional<Timestamp> timeFromKey(String key) {
    return Optional.ofNullable(this.params.get(key)).map(ParamMapper::toTimestamp);
  }

  public Optional<String> stringFromKey(String key) {
    return Optional.ofNullable(this.params.get(key)).map(ParamMapper::toString);
  }

  public Optional<Boolean> boolFromKey(String key) {
    return Optional.ofNullable(this.params.get(key)).map(ParamMapper::toBoolean);
  }

  public Optional<Map<String, Object>> objFromKey(String key) {
    return Optional.ofNullable(this.params.get(key)).map(ParamMapper::toObject);
  }

  public Optional<List<Timestamp>> listTimeFromKey(String key) {
    return Optional.ofNullable(this.params.get(key)).map(ParamMapper::toListTime);
  }

  public Optional<List<String>> listStringFromKey(String key) {
    return Optional.ofNullable(this.params.get(key)).map(ParamMapper::toListString);
  }

  public Optional<List<Long>> listLongFromKey(String key) {
    return Optional.ofNullable(this.params.get(key)).map(ParamMapper::toListLong);
  }

  public Optional<List<Map<String, Object>>> listObjFromKey(String key) {
    return Optional.ofNullable(this.params.get(key)).map(ParamMapper::toListObj);
  }

  private final Map<String, Object> params;

  private ParamReader(Map<String, Object> params) {
    this.params = params;
  }
}
