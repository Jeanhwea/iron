package com.avic.mti.iron.main.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "VW_RSRC_MAIN")
public class MainMaterialVo extends MesEntity {

  // 主材编码
  @Column(name = "MAIN_CODE", length = 400, unique = true, nullable = false)
  private String mainCode;

  @JsonProperty("mainCode")
  public String mainCode() {
    return this.mainCode;
  }

  public void mainCode(String mainCode) {
    this.mainCode = mainCode;
  }

  // 主材名称
  @Column(name = "MAIN_NAME", length = 400, nullable = false)
  private String mainName;

  @JsonProperty("mainName")
  public String mainName() {
    return this.mainName;
  }

  public void mainName(String mainName) {
    this.mainName = mainName;
  }

  // 主材类型
  @Column(name = "MAIN_CATE", length = 400)
  private String mainCate;

  @JsonProperty("mainCate")
  public String mainCate() {
    return this.mainCate;
  }

  public void mainCate(String mainCate) {
    this.mainCate = mainCate;
  }

  // 主材规格
  @Column(name = "MAIN_SPEC", length = 400)
  private String mainSpec;

  @JsonProperty("mainSpec")
  public String mainSpec() {
    return this.mainSpec;
  }

  public void mainSpec(String mainSpec) {
    this.mainSpec = mainSpec;
  }

  // 主材类别
  @Column(name = "MAIN_TYPE", length = 400)
  private String mainType;

  @JsonProperty("mainType")
  public String mainType() {
    return this.mainType;
  }

  public void mainType(String mainType) {
    this.mainType = mainType;
  }

  // 供应厂家
  @Column(name = "MAIN_MAKER", length = 400)
  private String mainMaker;

  @JsonProperty("mainMaker")
  public String mainMaker() {
    return this.mainMaker;
  }

  public void mainMaker(String mainMaker) {
    this.mainMaker = mainMaker;
  }

  // 所属部门编码
  @Column(name = "MAIN_DEPT", length = 400)
  private String mainDept;

  @JsonProperty("mainDept")
  public String mainDept() {
    return this.mainDept;
  }

  public void mainDept(String mainDept) {
    this.mainDept = mainDept;
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
