package com.avic.mti.iron.measure.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 * 工具日志实体类
 *
 * @author Jinghui Hu
 * @since 2020-11-03, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_MEAS_LOG")
public class MeasLog extends MesEntity {

  // 出库状态
  @Column(name = "LOG_STATUS", length = 400, nullable = false)
  private String logStatus;

  @JsonProperty("logStatus")
  public String logStatus() {
    return this.logStatus;
  }

  public void logStatus(String logStatus) {
    this.logStatus = logStatus;
  }

  // 出库类别
  @Column(name = "LOG_CATE", length = 400, nullable = false)
  private String logCate;

  @JsonProperty("logCate")
  public String logCate() {
    return this.logCate;
  }

  public void logCate(String logCate) {
    this.logCate = logCate;
  }

  // 出库类型
  @Column(name = "LOG_TYPE", length = 400, nullable = false)
  private String logType;

  @JsonProperty("logType")
  public String logType() {
    return this.logType;
  }

  public void logType(String logType) {
    this.logType = logType;
  }

  // 出库标记
  @Column(name = "LOG_FLAG", length = 400)
  private String logFlag;

  @JsonProperty("logFlag")
  public String logFlag() {
    return this.logFlag;
  }

  public void logFlag(String logFlag) {
    this.logFlag = logFlag;
  }

  // 工具字典 ID
  @Column(name = "MEAS_ID")
  private Long measId;

  @JsonProperty("measId")
  public Long measId() {
    return this.measId;
  }

  public void measId(Long measId) {
    this.measId = measId;
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

  // 出库单 ID
  @Column(name = "STUB_ID")
  private Long stubId;

  @JsonProperty("stubId")
  public Long stubId() {
    return this.stubId;
  }

  public void stubId(Long stubId) {
    this.stubId = stubId;
  }

  // 出库单明细 ID
  @Column(name = "STUB_DET_ID")
  private Long stubDetId;

  @JsonProperty("stubDetId")
  public Long stubDetId() {
    return this.stubDetId;
  }

  public void stubDetId(Long stubDetId) {
    this.stubDetId = stubDetId;
  }

  // 制单数量
  @Column(name = "PLAN_ENUM")
  private Long planEnum;

  @JsonProperty("planEnum")
  public Long planEnum() {
    return this.planEnum;
  }

  public void planEnum(Long planEnum) {
    this.planEnum = planEnum;
  }

  // 完成数量
  @Column(name = "PLAN_FNUM")
  private Long planFnum;

  @JsonProperty("planFnum")
  public Long planFnum() {
    return this.planFnum;
  }

  public void planFnum(Long planFnum) {
    this.planFnum = planFnum;
  }

  // 归还/移库数量
  @Column(name = "RET_NUM")
  private Long retNum;

  @JsonProperty("retNum")
  public Long retNum() {
    return this.retNum;
  }

  public void retNum(Long retNum) {
    this.retNum = retNum;
  }

  // 归还/移库前 ID
  @Column(name = "RET_PREV_ID")
  private Long retPrevId;

  @JsonProperty("retPrevId")
  public Long retPrevId() {
    return this.retPrevId;
  }

  public void retPrevId(Long retPrevId) {
    this.retPrevId = retPrevId;
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

  // 批号
  @Column(name = "MTL_BATCH", length = 400)
  private String mtlBatch;

  @JsonProperty("mtlBatch")
  public String mtlBatch() {
    return this.mtlBatch;
  }

  public void mtlBatch(String mtlBatch) {
    this.mtlBatch = mtlBatch;
  }

  // 保质期(单位：秒)
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

  // 生产时间
  @Column(name = "PRODUCE_TIME")
  private Timestamp produceTime;

  @JsonProperty("produceTime")
  public Timestamp produceTime() {
    return this.produceTime;
  }

  public void produceTime(Timestamp produceTime) {
    this.produceTime = produceTime;
  }

  // 失效时间
  @Column(name = "EXPIRE_TIME")
  private Timestamp expireTime;

  @JsonProperty("expireTime")
  public Timestamp expireTime() {
    return this.expireTime;
  }

  public void expireTime(Timestamp expireTime) {
    this.expireTime = expireTime;
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

  // 流转卡编码
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

  // 创建人
  @Column(name = "CREATOR_NC", length = 400)
  private String creatorNC;

  @JsonProperty("creatorNC")
  public String creatorNC() {
    return this.creatorNC;
  }

  public void creatorNC(String creatorNC) {
    this.creatorNC = creatorNC;
  }

  // 创建时间
  @Column(name = "CREATE_DATE")
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

  // 备注
  @Column(name = "NOTE", length = 400)
  private String note;

  @JsonProperty("note")
  public String note() {
    return this.note;
  }

  public void note(String note) {
    this.note = note;
  }
}
