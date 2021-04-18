package com.avic.mti.iron.measure.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "VW_RSRC_MEAS")
public class MeasMaterialVo extends MesEntity {

  // 工具编码
  @Column(name = "MEAS_CODE", length = 400, unique = true, nullable = false)
  private String measCode;

  @JsonProperty("measCode")
  public String measCode() {
    return this.measCode;
  }

  public void measCode(String measCode) {
    this.measCode = measCode;
  }

  // 工具名称
  @Column(name = "MEAS_NAME", length = 400, nullable = false)
  private String measName;

  @JsonProperty("measName")
  public String measName() {
    return this.measName;
  }

  public void measName(String measName) {
    this.measName = measName;
  }

  // 工具类型
  @Column(name = "MEAS_CATE", length = 400)
  private String measCate;

  @JsonProperty("measCate")
  public String measCate() {
    return this.measCate;
  }

  public void measCate(String measCate) {
    this.measCate = measCate;
  }

  // 工具规格
  @Column(name = "MEAS_SPEC", length = 400)
  private String measSpec;

  @JsonProperty("measSpec")
  public String measSpec() {
    return this.measSpec;
  }

  public void measSpec(String measSpec) {
    this.measSpec = measSpec;
  }

  // 工具类别
  @Column(name = "MEAS_TYPE", length = 400)
  private String measType;

  @JsonProperty("measType")
  public String measType() {
    return this.measType;
  }

  public void measType(String measType) {
    this.measType = measType;
  }

  // 供应厂家
  @Column(name = "MEAS_MAKER", length = 400)
  private String measMaker;

  @JsonProperty("measMaker")
  public String measMaker() {
    return this.measMaker;
  }

  public void measMaker(String measMaker) {
    this.measMaker = measMaker;
  }

  // 量检具用途
  @Column(name = "MEAS_USAGE", length = 4000)
  private String measUsage;

  @JsonProperty("measUsage")
  public String measUsage() {
    return this.measUsage;
  }

  public void measUsage(String measUsage) {
    this.measUsage = measUsage;
  }

  // 所属部门编码
  @Column(name = "MEAS_DEPT", length = 400)
  private String measDept;

  @JsonProperty("measDept")
  public String measDept() {
    return this.measDept;
  }

  public void measDept(String measDept) {
    this.measDept = measDept;
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

  // 库存数量
  @Column(name = "TOTAL_STK_NUM", nullable = false)
  private Long totalStkNum = 0L;

  @JsonProperty("totalStkNum")
  public Long totalStkNum() {
    return this.totalStkNum;
  }

  public void totalStkNum(Long totalStkNum) {
    this.totalStkNum = totalStkNum;
  }

  // 可用数量
  @Column(name = "TOTAL_AVL_NUM", nullable = false)
  private Long totalAvlNum = 0L;

  @JsonProperty("totalAvlNum")
  public Long totalAvlNum() {
    return this.totalAvlNum;
  }

  public void totalAvlNum(Long totalAvlNum) {
    this.totalAvlNum = totalAvlNum;
  }

  // 入库过期计数
  @Column(name = "EXPIRED_COUNT")
  private Long expiredCount;

  @JsonProperty("expiredCount")
  public Long expiredCount() {
    return this.expiredCount;
  }

  public void expiredCount(Long expiredCount) {
    this.expiredCount = expiredCount;
  }

  // 出库制单数量汇总
  @Column(name = "TOTAL_PLAN_ENUM")
  private Long totalPlanEnum;

  @JsonProperty("totalPlanEnum")
  public Long totalPlanEnum() {
    return this.totalPlanEnum;
  }

  public void totalPlanEnum(Long totalPlanEnum) {
    this.totalPlanEnum = totalPlanEnum;
  }

  // 出库完成数量汇总
  @Column(name = "TOTAL_PLAN_FNUM")
  private Long totalPlanFnum;

  @JsonProperty("totalPlanFnum")
  public Long totalPlanFnum() {
    return this.totalPlanFnum;
  }

  public void totalPlanFnum(Long totalPlanFnum) {
    this.totalPlanFnum = totalPlanFnum;
  }
}
