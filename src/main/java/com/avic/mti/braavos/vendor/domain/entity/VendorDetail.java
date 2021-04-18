package com.avic.mti.iron.vendor.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Table(name = "TB_RSRC_VEND_DET")
public class VendorDetail extends MesEntity {

  // 供应商外键
  @Column(name = "VEND_ID")
  private Long vendId;

  @JsonProperty("vendId")
  public Long vendId() {
    return this.vendId;
  }

  public void vendId(Long vendId) {
    this.vendId = vendId;
  }

  // 供应商编码
  @Column(name = "VEND_CODE", length = 400)
  private String vendCode;

  @JsonProperty("vendCode")
  public String vendCode() {
    return this.vendCode;
  }

  public void vendCode(String vendCode) {
    this.vendCode = vendCode;
  }

  // 产品外键
  @Column(name = "PRDT_ID")
  private Long prdtId;

  @JsonProperty("prdtId")
  public Long prdtId() {
    return this.prdtId;
  }

  public void prdtId(Long prdtId) {
    this.prdtId = prdtId;
  }

  // 产品编码
  @Column(name = "PRDT_CODE", length = 400)
  private String prdtCode;

  @JsonProperty("prdtCode")
  public String prdtCode() {
    return this.prdtCode;
  }

  public void prdtCode(String prdtCode) {
    this.prdtCode = prdtCode;
  }

  // 【待启用】
  @Column(name = "VEND_DET_VAR01_STR", length = 400)
  private String vendDetVar01Str;

  @JsonProperty("vendDetVar01Str")
  public String vendDetVar01Str() {
    return this.vendDetVar01Str;
  }

  public void vendDetVar01Str(String vendDetVar01Str) {
    this.vendDetVar01Str = vendDetVar01Str;
  }

  // 【待启用】
  @Column(name = "VEND_DET_VAR02_STR", length = 400)
  private String vendDetVar02Str;

  @JsonProperty("vendDetVar02Str")
  public String vendDetVar02Str() {
    return this.vendDetVar02Str;
  }

  public void vendDetVar02Str(String vendDetVar02Str) {
    this.vendDetVar02Str = vendDetVar02Str;
  }

  // 【待启用】
  @Column(name = "VEND_DET_VAR03_STR", length = 400)
  private String vendDetVar03Str;

  @JsonProperty("vendDetVar03Str")
  public String vendDetVar03Str() {
    return this.vendDetVar03Str;
  }

  public void vendDetVar03Str(String vendDetVar03Str) {
    this.vendDetVar03Str = vendDetVar03Str;
  }

  // 【待启用】
  @Column(name = "VEND_DET_VAR04_STR", length = 400)
  private String vendDetVar04Str;

  @JsonProperty("vendDetVar04Str")
  public String vendDetVar04Str() {
    return this.vendDetVar04Str;
  }

  public void vendDetVar04Str(String vendDetVar04Str) {
    this.vendDetVar04Str = vendDetVar04Str;
  }

  // 【待启用】
  @Column(name = "VEND_DET_VAR05_STR", length = 400)
  private String vendDetVar05Str;

  @JsonProperty("vendDetVar05Str")
  public String vendDetVar05Str() {
    return this.vendDetVar05Str;
  }

  public void vendDetVar05Str(String vendDetVar05Str) {
    this.vendDetVar05Str = vendDetVar05Str;
  }

  // 【待启用】
  @Column(name = "VEND_DET_VAR06_STR", length = 400)
  private String vendDetVar06Str;

  @JsonProperty("vendDetVar06Str")
  public String vendDetVar06Str() {
    return this.vendDetVar06Str;
  }

  public void vendDetVar06Str(String vendDetVar06Str) {
    this.vendDetVar06Str = vendDetVar06Str;
  }

  // 【待启用】
  @Column(name = "VEND_DET_VAR07_STR", length = 400)
  private String vendDetVar07Str;

  @JsonProperty("vendDetVar07Str")
  public String vendDetVar07Str() {
    return this.vendDetVar07Str;
  }

  public void vendDetVar07Str(String vendDetVar07Str) {
    this.vendDetVar07Str = vendDetVar07Str;
  }

  // 【待启用】
  @Column(name = "VEND_DET_VAR08_STR", length = 400)
  private String vendDetVar08Str;

  @JsonProperty("vendDetVar08Str")
  public String vendDetVar08Str() {
    return this.vendDetVar08Str;
  }

  public void vendDetVar08Str(String vendDetVar08Str) {
    this.vendDetVar08Str = vendDetVar08Str;
  }

  // 【待启用】
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "VEND_DET_EXT01_JSON", columnDefinition = "CLOB")
  private String vendDetExt01Json;

  @JsonProperty("vendDetExt01Json")
  public String vendDetExt01Json() {
    return this.vendDetExt01Json;
  }

  public void vendDetExt01Json(String vendDetExt01Json) {
    this.vendDetExt01Json = vendDetExt01Json;
  }

  // 【待启用】
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "VEND_DET_EXT02_JSON", columnDefinition = "CLOB")
  private String vendDetExt02Json;

  @JsonProperty("vendDetExt02Json")
  public String vendDetExt02Json() {
    return this.vendDetExt02Json;
  }

  public void vendDetExt02Json(String vendDetExt02Json) {
    this.vendDetExt02Json = vendDetExt02Json;
  }

  // 【待启用】
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "VEND_DET_EXT03_JSON", columnDefinition = "CLOB")
  private String vendDetExt03Json;

  @JsonProperty("vendDetExt03Json")
  public String vendDetExt03Json() {
    return this.vendDetExt03Json;
  }

  public void vendDetExt03Json(String vendDetExt03Json) {
    this.vendDetExt03Json = vendDetExt03Json;
  }

  // 【待启用】
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "VEND_DET_EXT04_JSON", columnDefinition = "CLOB")
  private String vendDetExt04Json;

  @JsonProperty("vendDetExt04Json")
  public String vendDetExt04Json() {
    return this.vendDetExt04Json;
  }

  public void vendDetExt04Json(String vendDetExt04Json) {
    this.vendDetExt04Json = vendDetExt04Json;
  }
}
