package com.avic.mti.iron.device.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

/**
 * 数控系统字典实体
 *
 * @author Jinghui Hu
 * @since 2020-08-30, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_DEVC_NCS")
public class DeviceNCS extends MesEntity {

  // 数控编码
  @Column(name = "NCS_CODE", length = 400, unique = true, nullable = false)
  private String ncsCode;

  @JsonProperty("ncsCode")
  public String ncsCode() {
    return this.ncsCode;
  }

  public void ncsCode(String ncsCode) {
    this.ncsCode = ncsCode;
  }

  // 数控名称
  @Column(name = "NCS_NAME", length = 400, nullable = false)
  private String ncsName;

  @JsonProperty("ncsName")
  public String ncsName() {
    return this.ncsName;
  }

  public void ncsName(String ncsName) {
    this.ncsName = ncsName;
  }

  // 备注
  @Column(name = "NOTE", length = 400)
  private String note;

  @JsonProperty("note")
  public String note() {
    return this.note;
  }

  public void note(String note) {
    this.note = note;
  }
}
