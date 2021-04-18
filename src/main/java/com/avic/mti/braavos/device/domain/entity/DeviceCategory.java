package com.avic.mti.iron.device.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

/**
 * 设备类别字典
 *
 * @author Jinghui Hu
 * @since 2020-08-30, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_DEVC_CATE")
public class DeviceCategory extends MesEntity {

  // 分类编码
  @Column(name = "CATE_CODE", length = 400, unique = true, nullable = false)
  private String cateCode;

  @JsonProperty("cateCode")
  public String cateCode() {
    return this.cateCode;
  }

  public void cateCode(String cateCode) {
    this.cateCode = cateCode;
  }

  // 分类名称
  @Column(name = "CATE_NAME", length = 400, nullable = false)
  private String cateName;

  @JsonProperty("cateName")
  public String cateName() {
    return this.cateName;
  }

  public void cateName(String cateName) {
    this.cateName = cateName;
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
