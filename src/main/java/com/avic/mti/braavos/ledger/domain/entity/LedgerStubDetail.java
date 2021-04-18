package com.avic.mti.iron.ledger.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 * 资源出库单明细实体类
 *
 * @author Jinghui Hu
 * @since 2020-11-03, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_LEDG_STUB_DET")
public class LedgerStubDetail extends MesEntity {

  // 出库单 ID
  @Column(name = "STUB_ID", nullable = false)
  private Long stubId;

  @JsonProperty("stubId")
  public Long stubId() {
    return this.stubId;
  }

  public void stubId(Long stubId) {
    this.stubId = stubId;
  }

  // 出库单编码
  @Column(name = "STUB_CODE", length = 400)
  private String stubCode;

  @JsonProperty("stubCode")
  public String stubCode() {
    return this.stubCode;
  }

  public void stubCode(String stubCode) {
    this.stubCode = stubCode;
  }

  // 明细状态
  @Column(name = "STUB_DET_STATUS", length = 400, nullable = false)
  private String stubDetStatus;

  @JsonProperty("stubDetStatus")
  public String stubDetStatus() {
    return this.stubDetStatus;
  }

  public void stubDetStatus(String stubDetStatus) {
    this.stubDetStatus = stubDetStatus;
  }

  // 明细类别
  @Column(name = "STUB_DET_CATE", length = 400, nullable = false)
  private String stubDetCate;

  @JsonProperty("stubDetCate")
  public String stubDetCate() {
    return this.stubDetCate;
  }

  public void stubDetCate(String stubDetCate) {
    this.stubDetCate = stubDetCate;
  }

  // 物料类别
  @Column(name = "STUB_DET_TYPE", length = 400, nullable = false)
  private String stubDetType;

  @JsonProperty("stubDetType")
  public String stubDetType() {
    return this.stubDetType;
  }

  public void stubDetType(String stubDetType) {
    this.stubDetType = stubDetType;
  }

  // 材料编码
  @Column(name = "MTL_CODE", length = 400)
  private String mtlCode;

  @JsonProperty("mtlCode")
  public String mtlCode() {
    return this.mtlCode;
  }

  public void mtlCode(String mtlCode) {
    this.mtlCode = mtlCode;
  }

  // 材料名称
  @Column(name = "MTL_NAME", length = 400)
  private String mtlName;

  @JsonProperty("mtlName")
  public String mtlName() {
    return this.mtlName;
  }

  public void mtlName(String mtlName) {
    this.mtlName = mtlName;
  }

  // 材料种类
  @Column(name = "MTL_TYPE", length = 400)
  private String mtlType;

  @JsonProperty("mtlType")
  public String mtlType() {
    return this.mtlType;
  }

  public void mtlType(String mtlType) {
    this.mtlType = mtlType;
  }

  // 材料规格
  @Column(name = "MTL_SPEC", length = 400)
  private String mtlSpec;

  @JsonProperty("mtlSpec")
  public String mtlSpec() {
    return this.mtlSpec;
  }

  public void mtlSpec(String mtlSpec) {
    this.mtlSpec = mtlSpec;
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

  // 材料批号
  @Column(name = "MTL_BATCH", length = 400)
  private String mtlBatch;

  @JsonProperty("mtlBatch")
  public String mtlBatch() {
    return this.mtlBatch;
  }

  public void mtlBatch(String mtlBatch) {
    this.mtlBatch = mtlBatch;
  }

  // 入库 ID
  @Column(name = "IN_ID")
  private Long inId;

  @JsonProperty("inId")
  public Long inId() {
    return this.inId;
  }

  public void inId(Long inId) {
    this.inId = inId;
  }

  // 需求数量
  @Column(name = "QD_NUM", nullable = false)
  private Long qdNum;

  @JsonProperty("qdNum")
  public Long qdNum() {
    return this.qdNum;
  }

  public void qdNum(Long qdNum) {
    this.qdNum = qdNum;
  }

  // 出库数量
  @Column(name = "OUT_NUM", nullable = false)
  private Long outNum;

  @JsonProperty("outNum")
  public Long outNum() {
    return this.outNum;
  }

  public void outNum(Long outNum) {
    this.outNum = outNum;
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

  // 创建人
  @Column(name = "CREATOR_NC", length = 400, nullable = false)
  private String creatorNC;

  @JsonProperty("creatorNC")
  public String creatorNC() {
    return this.creatorNC;
  }

  public void creatorNC(String creatorNC) {
    this.creatorNC = creatorNC;
  }

  // 创建日期
  @Column(name = "CREATE_DATE", nullable = false)
  private Timestamp createDate;

  @JsonProperty("createDate")
  public Timestamp createDate() {
    return this.createDate;
  }

  public void createDate(Timestamp createDate) {
    this.createDate = createDate;
  }

  // 完成人
  @Column(name = "FINISH_OPER_NC", length = 400)
  private String finishOperNC;

  @JsonProperty("finishOperNC")
  public String finishOperNC() {
    return this.finishOperNC;
  }

  public void finishOperNC(String finishOperNC) {
    this.finishOperNC = finishOperNC;
  }

  // 完成时间
  @Column(name = "FINISH_DATE")
  private Timestamp finishDate;

  @JsonProperty("finishDate")
  public Timestamp finishDate() {
    return this.finishDate;
  }

  public void finishDate(Timestamp finishDate) {
    this.finishDate = finishDate;
  }

  // 是否超出
  @Column(name = "IS_OVERFLOW", length = 1, nullable = false)
  private String isOverflow = "n";

  @JsonProperty("isOverflow")
  public boolean isOverflow() {
    return "y".equals(this.isOverflow);
  }

  public void isOverflow(boolean isOverflow) {
    this.isOverflow = isOverflow ? "y" : "n";
  }

  // 任务编码
  @Column(name = "TASK_CODE", length = 400)
  private String taskCode;

  @JsonProperty("taskCode")
  public String taskCode() {
    return this.taskCode;
  }

  public void taskCode(String taskCode) {
    this.taskCode = taskCode;
  }

  // 生产订单号
  @Column(name = "BRGN_CODE", length = 400)
  private String brgnCode;

  @JsonProperty("brgnCode")
  public String brgnCode() {
    return this.brgnCode;
  }

  public void brgnCode(String brgnCode) {
    this.brgnCode = brgnCode;
  }

  // 项目令号
  @Column(name = "PROJ_CODE", length = 400)
  private String projCode;

  @JsonProperty("projCode")
  public String projCode() {
    return this.projCode;
  }

  public void projCode(String projCode) {
    this.projCode = projCode;
  }

  // 出库目的
  @Column(name = "OUT_REASON", length = 4000)
  private String outReason;

  @JsonProperty("outReason")
  public String outReason() {
    return this.outReason;
  }

  public void outReason(String outReason) {
    this.outReason = outReason;
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
}
