package com.avic.mti.iron.common.http.request;

import java.util.HashMap;
import java.util.Map;

/**
 * 域建造类
 *
 * @author Jinghui Hu
 * @since 2020-04-17, JDK1.8
 */
public class FieldBuilder {
  public static FieldBuilder init() {
    return new FieldBuilder();
  }

  public Map<String, FieldTypeEnum> fields() {
    return this.fields;
  }

  public FieldBuilder put(String key, FieldTypeEnum value) {
    this.fields.put(key, value);
    return this;
  }

  private final Map<String, FieldTypeEnum> fields;

  private FieldBuilder() {
    this.fields = new HashMap<>();
  }
}
