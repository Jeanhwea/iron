package com.avic.mti.iron.tool.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "TB_RSRC_TOOL")
public class ToolMaterial extends MesEntity {

  // 工具编码
  @Column(name = "TOOL_CODE", length = 400, unique = true, nullable = false)
  private String toolCode;

  @JsonProperty("toolCode")
  public String toolCode() {
    return this.toolCode;
  }

  public void toolCode(String toolCode) {
    this.toolCode = toolCode;
  }

  // 工具名称
  @Column(name = "TOOL_NAME", length = 400, nullable = false)
  private String toolName;

  @JsonProperty("toolName")
  public String toolName() {
    return this.toolName;
  }

  public void toolName(String toolName) {
    this.toolName = toolName;
  }

  // 工具类型
  @Column(name = "TOOL_CATE", length = 400)
  private String toolCate;

  @JsonProperty("toolCate")
  public String toolCate() {
    return this.toolCate;
  }

  public void toolCate(String toolCate) {
    this.toolCate = toolCate;
  }

  // 工具规格
  @Column(name = "TOOL_SPEC", length = 400)
  private String toolSpec;

  @JsonProperty("toolSpec")
  public String toolSpec() {
    return this.toolSpec;
  }

  public void toolSpec(String toolSpec) {
    this.toolSpec = toolSpec;
  }

  // 工具类别
  @Column(name = "TOOL_TYPE", length = 400)
  private String toolType;

  @JsonProperty("toolType")
  public String toolType() {
    return this.toolType;
  }

  public void toolType(String toolType) {
    this.toolType = toolType;
  }

  // 供应厂家
  @Column(name = "TOOL_MAKER", length = 400)
  private String toolMaker;

  @JsonProperty("toolMaker")
  public String toolMaker() {
    return this.toolMaker;
  }

  public void toolMaker(String toolMaker) {
    this.toolMaker = toolMaker;
  }

  // 所属部门编码
  @Column(name = "TOOL_DEPT", length = 400)
  private String toolDept;

  @JsonProperty("toolDept")
  public String toolDept() {
    return this.toolDept;
  }

  public void toolDept(String toolDept) {
    this.toolDept = toolDept;
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
