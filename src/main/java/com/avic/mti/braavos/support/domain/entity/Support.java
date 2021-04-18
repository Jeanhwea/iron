package com.avic.mti.iron.support.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "TB_RSRC_SUPT")
public class Support extends MesEntity {

  // 配套编码
  @Column(name = "SUPT_CODE", length = 400, unique = true, nullable = false)
  private String suptCode;

  @JsonProperty("suptCode")
  public String suptCode() {
    return this.suptCode;
  }

  public void suptCode(String suptCode) {
    this.suptCode = suptCode;
  }

  // 配套名称
  @Column(name = "SUPT_NAME", length = 400, nullable = false)
  private String suptName;

  @JsonProperty("suptName")
  public String suptName() {
    return this.suptName;
  }

  public void suptName(String suptName) {
    this.suptName = suptName;
  }

  // 配套类型
  @Column(name = "SUPT_CATE", length = 400)
  private String suptCate;

  @JsonProperty("suptCate")
  public String suptCate() {
    return this.suptCate;
  }

  public void suptCate(String suptCate) {
    this.suptCate = suptCate;
  }

  // 配套规格
  @Column(name = "SUPT_SPEC", length = 400)
  private String suptSpec;

  @JsonProperty("suptSpec")
  public String suptSpec() {
    return this.suptSpec;
  }

  public void suptSpec(String suptSpec) {
    this.suptSpec = suptSpec;
  }

  // 配套类别
  @Column(name = "SUPT_TYPE", length = 400)
  private String suptType;

  @JsonProperty("suptType")
  public String suptType() {
    return this.suptType;
  }

  public void suptType(String suptType) {
    this.suptType = suptType;
  }

  // 所属部门编码
  @Column(name = "SUPT_DEPT", length = 400)
  private String suptDept;

  @JsonProperty("suptDept")
  public String suptDept() {
    return this.suptDept;
  }

  public void suptDept(String suptDept) {
    this.suptDept = suptDept;
  }

  // 配套用途
  @Column(name = "SUPT_USAGE", length = 400)
  private String suptUsage;

  @JsonProperty("suptUsage")
  public String suptUsage() {
    return this.suptUsage;
  }

  public void suptUsage(String suptUsage) {
    this.suptUsage = suptUsage;
  }

  // 供应厂家
  @Column(name = "SUPT_MAKER", length = 400)
  private String suptMaker;

  @JsonProperty("suptMaker")
  public String suptMaker() {
    return this.suptMaker;
  }

  public void suptMaker(String suptMaker) {
    this.suptMaker = suptMaker;
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
