package com.avic.mti.iron.ledger.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

/**
 * 制造资源台账
 *
 * @author Jinghui Hu
 * @since 2020-08-30, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_LEDG_RES")
public class Resource extends MesEntity {

  // 制造资源编码
  @Column(name = "RES_CODE", length = 400, unique = true, nullable = false)
  private String resCode;

  @JsonProperty("resCode")
  public String resCode() {
    return this.resCode;
  }

  public void resCode(String resCode) {
    this.resCode = resCode;
  }

  // 制造资源名称
  @Column(name = "RES_NAME", length = 400, nullable = false)
  private String resName;

  @JsonProperty("resName")
  public String resName() {
    return this.resName;
  }

  public void resName(String resName) {
    this.resName = resName;
  }

  // 制造资源行为分类
  @Column(name = "RES_TYPE", length = 400, nullable = false)
  private String resType;

  @JsonProperty("resType")
  public String resType() {
    return this.resType;
  }

  public void resType(String resType) {
    this.resType = resType;
  }

  // 制造资源归属
  @Column(name = "RES_CATE", length = 400, nullable = false)
  private String resCate;

  @JsonProperty("resCate")
  public String resCate() {
    return this.resCate;
  }

  public void resCate(String resCate) {
    this.resCate = resCate;
  }

  // 制造资源规格
  @Column(name = "RES_SPEC", length = 400, nullable = false)
  private String resSpec;

  @JsonProperty("resSpec")
  public String resSpec() {
    return this.resSpec;
  }

  public void resSpec(String resSpec) {
    this.resSpec = resSpec;
  }

  // 制造资源描述
  @Column(name = "RES_DESC", length = 2000)
  private String resDesc;

  @JsonProperty("resDesc")
  public String resDesc() {
    return this.resDesc;
  }

  public void resDesc(String resDesc) {
    this.resDesc = resDesc;
  }

  // 制造资源计量单位
  @Column(name = "RES_MEASURE", length = 64)
  private String resMeasure;

  @JsonProperty("resMeasure")
  public String resMeasure() {
    return this.resMeasure;
  }

  public void resMeasure(String resMeasure) {
    this.resMeasure = resMeasure;
  }

  // 制造资源工艺分类
  @Column(name = "RES_TECH_TYPE", length = 400, nullable = false)
  private String resTechType;

  @JsonProperty("resTechType")
  public String resTechType() {
    return this.resTechType;
  }

  public void resTechType(String resTechType) {
    this.resTechType = resTechType;
  }

  // 接入网络状态
  @Column(name = "CONN_STATUS", length = 400, nullable = false)
  private String connStatus;

  @JsonProperty("connStatus")
  public String connStatus() {
    return this.connStatus;
  }

  public void connStatus(String connStatus) {
    this.connStatus = connStatus;
  }

  // 制造资源编码
  @Column(name = "RES_REF_CODE", length = 400, nullable = false)
  private String resRefCode;

  @JsonProperty("resRefCode")
  public String resRefCode() {
    return this.resRefCode;
  }

  public void resRefCode(String resRefCode) {
    this.resRefCode = resRefCode;
  }

  // 备注
  @Column(name = "NOTE", length = 4000)
  private String note;

  @JsonProperty("note")
  public String note() {
    return this.note;
  }

  public void note(String note) {
    this.note = note;
  }

  // 参数 JSON
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "PARAM_JSON", columnDefinition = "CLOB")
  private String paramJson;

  @JsonProperty("paramJson")
  public String paramJson() {
    return this.paramJson;
  }

  public void paramJson(String paramJson) {
    this.paramJson = paramJson;
  }
}
