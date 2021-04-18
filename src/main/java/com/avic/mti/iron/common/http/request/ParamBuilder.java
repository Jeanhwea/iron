package com.avic.mti.iron.common.http.request;

import com.avic.mti.iron.common.helper.JsonHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 参数建造类
 *
 * @author Jinghui Hu
 * @since 2019-08-26, JDK1.8
 */
public class ParamBuilder {

  public static ParamBuilder init() {
    return new ParamBuilder();
  }

  public static ParamBuilder init(Map<String, Object> params) {
    return new ParamBuilder(params);
  }

  public Map<String, Object> params() {
    return this.params;
  }

  public void params(Map<String, Object> params) {
    this.params = new HashMap<>(params);
  }

  public ParamBuilder put(String key, Object value) {
    this.params.put(key, value);
    return this;
  }

  public ParamBuilder putAll(Map<String, Object> params) {
    this.params.putAll(params);
    return this;
  }

  public Optional<String> toJsonString() {
    return JsonHelper.stringifyObject(this.params);
  }

  public ParamBuilder tween() {
    return new ParamBuilder(new HashMap<>(this.params));
  }

  private Map<String, Object> params;

  private ParamBuilder() {
    this.params = new HashMap<>();
  }

  private ParamBuilder(Map<String, Object> params) {
    this.params = new HashMap<>(params);
  }
}
