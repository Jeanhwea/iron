package com.avic.mti.iron.mould.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "TB_RSRC_MOLD")
public class Mould extends MesEntity {

  // 模具编码
  @Column(name = "MOLD_CODE", length = 400, unique = true, nullable = false)
  private String moldCode;

  @JsonProperty("moldCode")
  public String moldCode() {
    return this.moldCode;
  }

  public void moldCode(String moldCode) {
    this.moldCode = moldCode;
  }

  // 模具名称
  @Column(name = "MOLD_NAME", length = 400, nullable = false)
  private String moldName;

  @JsonProperty("moldName")
  public String moldName() {
    return this.moldName;
  }

  public void moldName(String moldName) {
    this.moldName = moldName;
  }

  // 模具分类
  @Column(name = "MOLD_CATE", length = 400)
  private String moldCate;

  @JsonProperty("moldCate")
  public String moldCate() {
    return this.moldCate;
  }

  public void moldCate(String moldCate) {
    this.moldCate = moldCate;
  }

  // 模具规格
  @Column(name = "MOLD_SPEC", length = 400)
  private String moldSpec;

  @JsonProperty("moldSpec")
  public String moldSpec() {
    return this.moldSpec;
  }

  public void moldSpec(String moldSpec) {
    this.moldSpec = moldSpec;
  }

  // 所属部门编码
  @Column(name = "MOLD_DEPT", length = 400)
  private String moldDept;

  @JsonProperty("moldDept")
  public String moldDept() {
    return this.moldDept;
  }

  public void moldDept(String moldDept) {
    this.moldDept = moldDept;
  }

  // 负责人
  @Column(name = "MOLD_ADMIN", length = 400)
  private String moldAdmin;

  @JsonProperty("moldAdmin")
  public String moldAdmin() {
    return this.moldAdmin;
  }

  public void moldAdmin(String moldAdmin) {
    this.moldAdmin = moldAdmin;
  }

  // 制造厂商
  @Column(name = "MOLD_MAKER", length = 400)
  private String moldMaker;

  @JsonProperty("moldMaker")
  public String moldMaker() {
    return this.moldMaker;
  }

  public void moldMaker(String moldMaker) {
    this.moldMaker = moldMaker;
  }

  // 模具状态
  @Column(name = "MOLD_STATUS", length = 400)
  private String moldStatus;

  @JsonProperty("moldStatus")
  public String moldStatus() {
    return this.moldStatus;
  }

  public void moldStatus(String moldStatus) {
    this.moldStatus = moldStatus;
  }

  // 图纸编号
  @Column(name = "BLUEPRINT_NO", length = 400)
  private String blueprintNo;

  @JsonProperty("blueprintNo")
  public String blueprintNo() {
    return this.blueprintNo;
  }

  public void blueprintNo(String blueprintNo) {
    this.blueprintNo = blueprintNo;
  }

  // 设计寿命
  @Column(name = "SERVICE_LIFE")
  private Timestamp serviceLife;

  @JsonProperty("serviceLife")
  public Timestamp serviceLife() {
    return this.serviceLife;
  }

  public void serviceLife(Timestamp serviceLife) {
    this.serviceLife = serviceLife;
  }

  // 模具尺寸
  @Column(name = "MOLD_SIZE", length = 400)
  private String moldSize;

  @JsonProperty("moldSize")
  public String moldSize() {
    return this.moldSize;
  }

  public void moldSize(String moldSize) {
    this.moldSize = moldSize;
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
