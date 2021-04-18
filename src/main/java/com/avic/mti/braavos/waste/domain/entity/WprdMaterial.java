package com.avic.mti.iron.waste.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "TB_RSRC_WPRD")
public class WprdMaterial extends MesEntity {

  // 废品编码
  @Column(name = "WPRD_CODE", length = 400, unique = true, nullable = false)
  private String wprdCode;

  @JsonProperty("wprdCode")
  public String wprdCode() {
    return this.wprdCode;
  }

  public void wprdCode(String wprdCode) {
    this.wprdCode = wprdCode;
  }

  // 废品名称
  @Column(name = "WPRD_NAME", length = 400, nullable = false)
  private String wprdName;

  @JsonProperty("wprdName")
  public String wprdName() {
    return this.wprdName;
  }

  public void wprdName(String wprdName) {
    this.wprdName = wprdName;
  }

  // 废品类型
  @Column(name = "WPRD_CATE", length = 400)
  private String wprdCate;

  @JsonProperty("wprdCate")
  public String wprdCate() {
    return this.wprdCate;
  }

  public void wprdCate(String wprdCate) {
    this.wprdCate = wprdCate;
  }

  // 废品规格
  @Column(name = "WPRD_SPEC", length = 400)
  private String wprdSpec;

  @JsonProperty("wprdSpec")
  public String wprdSpec() {
    return this.wprdSpec;
  }

  public void wprdSpec(String wprdSpec) {
    this.wprdSpec = wprdSpec;
  }

  // 废品类别
  @Column(name = "WPRD_TYPE", length = 400)
  private String wprdType;

  @JsonProperty("wprdType")
  public String wprdType() {
    return this.wprdType;
  }

  public void wprdType(String wprdType) {
    this.wprdType = wprdType;
  }

  // 供应厂家
  @Column(name = "WPRD_MAKER", length = 400)
  private String wprdMaker;

  @JsonProperty("wprdMaker")
  public String wprdMaker() {
    return this.wprdMaker;
  }

  public void wprdMaker(String wprdMaker) {
    this.wprdMaker = wprdMaker;
  }

  // 所属部门编码
  @Column(name = "WPRD_DEPT", length = 400)
  private String wprdDept;

  @JsonProperty("wprdDept")
  public String wprdDept() {
    return this.wprdDept;
  }

  public void wprdDept(String wprdDept) {
    this.wprdDept = wprdDept;
  }

  // 材料牌号
  @Column(name = "MTL_MARK", length = 400)
  private String mtlMark;

  @JsonProperty("mtlMark")
  public String mtlMark() {
    return this.mtlMark;
  }

  public void mtlMark(String mtlMark) {
    this.mtlMark = mtlMark;
  }

  // 材料标准
  @Column(name = "MTL_STANDARD", length = 400)
  private String mtlStandard;

  @JsonProperty("mtlStandard")
  public String mtlStandard() {
    return this.mtlStandard;
  }

  public void mtlStandard(String mtlStandard) {
    this.mtlStandard = mtlStandard;
  }

  // 计量单位
  @Column(name = "MEASURE", length = 400)
  private String measure;

  @JsonProperty("measure")
  public String measure() {
    return this.measure;
  }

  public void measure(String measure) {
    this.measure = measure;
  }

  // 入库种类
  @Column(name = "IN_TYPE", length = 400)
  private String inType;

  @JsonProperty("inType")
  public String inType() {
    return this.inType;
  }

  public void inType(String inType) {
    this.inType = inType;
  }

  // 入库单据
  @Column(name = "IN_STUB", length = 400)
  private String inStub;

  @JsonProperty("inStub")
  public String inStub() {
    return this.inStub;
  }

  public void inStub(String inStub) {
    this.inStub = inStub;
  }

  // 入账人
  @Column(name = "IN_NC", length = 400)
  private String inNC;

  @JsonProperty("inNC")
  public String inNC() {
    return this.inNC;
  }

  public void inNC(String inNC) {
    this.inNC = inNC;
  }

  // 入账日期
  @Column(name = "IN_DATE")
  private Timestamp inDate;

  @JsonProperty("inDate")
  public Timestamp inDate() {
    return this.inDate;
  }

  public void inDate(Timestamp inDate) {
    this.inDate = inDate;
  }

  // 预警数量
  @Column(name = "MIN_STK_NUM")
  private Long minStkNum;

  @JsonProperty("minStkNum")
  public Long minStkNum() {
    return this.minStkNum;
  }

  public void minStkNum(Long minStkNum) {
    this.minStkNum = minStkNum;
  }

  // 常温保质期(单位：秒)
  @Column(name = "SERVICE_LIFE")
  private Long serviceLife;

  @JsonProperty("serviceLife")
  public Long serviceLife() {
    return this.serviceLife;
  }

  public void serviceLife(Long serviceLife) {
    this.serviceLife = serviceLife;
  }

  // 冷冻保质期(单位：秒)
  @Column(name = "SERVICE_LIFE2")
  private Long serviceLife2;

  @JsonProperty("serviceLife2")
  public Long serviceLife2() {
    return this.serviceLife2;
  }

  public void serviceLife2(Long serviceLife2) {
    this.serviceLife2 = serviceLife2;
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

  // 库位描述
  @Column(name = "SHELF_TEXT", length = 4000)
  private String shelfText;

  @JsonProperty("shelfText")
  public String shelfText() {
    return this.shelfText;
  }

  public void shelfText(String shelfText) {
    this.shelfText = shelfText;
  }

  // 库位
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "SHELF_JSON", columnDefinition = "CLOB")
  private String shelfJson;

  @JsonProperty("shelfJson")
  public String shelfJson() {
    return this.shelfJson;
  }

  public void shelfJson(String shelfJson) {
    this.shelfJson = shelfJson;
  }
}
