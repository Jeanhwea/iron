package com.avic.mti.iron.auxiliary.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "TB_RSRC_AUXI")
public class AuxiMaterial extends MesEntity {

  // 辅材编码
  @Column(name = "AUXI_CODE", length = 400, unique = true, nullable = false)
  private String auxiCode;

  @JsonProperty("auxiCode")
  public String auxiCode() {
    return this.auxiCode;
  }

  public void auxiCode(String auxiCode) {
    this.auxiCode = auxiCode;
  }

  // 辅材名称
  @Column(name = "AUXI_NAME", length = 400, nullable = false)
  private String auxiName;

  @JsonProperty("auxiName")
  public String auxiName() {
    return this.auxiName;
  }

  public void auxiName(String auxiName) {
    this.auxiName = auxiName;
  }

  // 辅材类型
  @Column(name = "AUXI_CATE", length = 400)
  private String auxiCate;

  @JsonProperty("auxiCate")
  public String auxiCate() {
    return this.auxiCate;
  }

  public void auxiCate(String auxiCate) {
    this.auxiCate = auxiCate;
  }

  // 辅材规格
  @Column(name = "AUXI_SPEC", length = 400)
  private String auxiSpec;

  @JsonProperty("auxiSpec")
  public String auxiSpec() {
    return this.auxiSpec;
  }

  public void auxiSpec(String auxiSpec) {
    this.auxiSpec = auxiSpec;
  }

  // 辅材类别
  @Column(name = "AUXI_TYPE", length = 400)
  private String auxiType;

  @JsonProperty("auxiType")
  public String auxiType() {
    return this.auxiType;
  }

  public void auxiType(String auxiType) {
    this.auxiType = auxiType;
  }

  // 供应厂家
  @Column(name = "AUXI_MAKER", length = 400)
  private String auxiMaker;

  @JsonProperty("auxiMaker")
  public String auxiMaker() {
    return this.auxiMaker;
  }

  public void auxiMaker(String auxiMaker) {
    this.auxiMaker = auxiMaker;
  }

  // 所属部门编码
  @Column(name = "AUXI_DEPT", length = 400)
  private String auxiDept;

  @JsonProperty("auxiDept")
  public String auxiDept() {
    return this.auxiDept;
  }

  public void auxiDept(String auxiDept) {
    this.auxiDept = auxiDept;
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
