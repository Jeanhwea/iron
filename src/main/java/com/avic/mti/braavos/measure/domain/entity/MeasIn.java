package com.avic.mti.iron.measure.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.Formula;

/**
 * 工具入库实体类
 *
 * @author Jinghui Hu
 * @since 2020-10-29, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_MEAS_IN")
public class MeasIn extends MesEntity {

  // 入库编码
  @Column(name = "IN_CODE", length = 400, unique = true, nullable = false)
  private String inCode;

  @JsonProperty("inCode")
  public String inCode() {
    return this.inCode;
  }

  public void inCode(String inCode) {
    this.inCode = inCode;
  }

  // 入库类别
  @Column(name = "IN_CATE", length = 400, nullable = false)
  private String inCate;

  @JsonProperty("inCate")
  public String inCate() {
    return this.inCate;
  }

  public void inCate(String inCate) {
    this.inCate = inCate;
  }

  // 入库类型
  @Column(name = "IN_TYPE", length = 400, nullable = false)
  private String inType;

  @JsonProperty("inType")
  public String inType() {
    return this.inType;
  }

  public void inType(String inType) {
    this.inType = inType;
  }

  // 字典 ID
  @Column(name = "MEAS_ID", nullable = false)
  private Long measId;

  @JsonProperty("measId")
  public Long measId() {
    return this.measId;
  }

  public void measId(Long measId) {
    this.measId = measId;
  }

  @Formula("(SELECT A.MEAS_CODE FROM TB_RSRC_MEAS A WHERE MEAS_ID = A.MYID)")
  private String measCode;

  @JsonProperty("measCode")
  public String measCode() {
    return this.measCode;
  }

  // 批号/炉号
  @Column(name = "MTL_BATCH", length = 400, nullable = false)
  private String mtlBatch;

  @JsonProperty("mtlBatch")
  public String mtlBatch() {
    return this.mtlBatch;
  }

  public void mtlBatch(String mtlBatch) {
    this.mtlBatch = mtlBatch;
  }

  // 工具名称
  @Column(name = "MEAS_NAME", length = 400)
  private String measName;

  @JsonProperty("measName")
  public String measName() {
    return this.measName;
  }

  public void measName(String measName) {
    this.measName = measName;
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

  // 工具类型
  @Column(name = "MEAS_TYPE", length = 400)
  private String measType;

  @JsonProperty("measType")
  public String measType() {
    return this.measType;
  }

  public void measType(String measType) {
    this.measType = measType;
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

  // 采购计划编码
  @Column(name = "BPLN_CODE", length = 400)
  private String bplnCode;

  @JsonProperty("bplnCode")
  public String bplnCode() {
    return this.bplnCode;
  }

  public void bplnCode(String bplnCode) {
    this.bplnCode = bplnCode;
  }

  // 手填采购编码
  @Column(name = "PURCH_CODE", length = 400)
  private String purchCode;

  @JsonProperty("purchCode")
  public String purchCode() {
    return this.purchCode;
  }

  public void purchCode(String purchCode) {
    this.purchCode = purchCode;
  }

  // 工具单价
  @Column(name = "MEAS_PRICE", nullable = false)
  private Double measPrice;

  @JsonProperty("measPrice")
  public Double measPrice() {
    return this.measPrice;
  }

  public void measPrice(Double measPrice) {
    this.measPrice = measPrice;
  }

  // 库存数量
  @Column(name = "MEAS_STK_NUM", nullable = false)
  private Long measStkNum = 0L;

  @JsonProperty("measStkNum")
  public Long measStkNum() {
    return this.measStkNum;
  }

  public void measStkNum(Long measStkNum) {
    this.measStkNum = measStkNum;
  }

  // 可用数量
  @Column(name = "MEAS_AVL_NUM", nullable = false)
  private Long measAvlNum = 0L;

  @JsonProperty("measAvlNum")
  public Long measAvlNum() {
    return this.measAvlNum;
  }

  public void measAvlNum(Long measAvlNum) {
    this.measAvlNum = measAvlNum;
  }

  // 计划数量
  @Column(name = "MEAS_PLN_NUM", nullable = false)
  private Long measPlnNum = 0L;

  @JsonProperty("measPlnNum")
  public Long measPlnNum() {
    return this.measPlnNum;
  }

  public void measPlnNum(Long measPlnNum) {
    this.measPlnNum = measPlnNum;
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

  // 常温保质期的剩余秒数
  @Formula("NVL(SERVICE_LIFE, 0) - (TO_NUMBER(SYSDATE - IN_DATE) * 86400)")
  private Long serviceLeftSecond;

  @JsonProperty("serviceLeftSecond")
  public Long serviceLeftSecond() {
    return this.serviceLeftSecond;
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

  @Formula(
      "DECODE(SIGN(NVL(SERVICE_LIFE,0)-30*86400-(TO_NUMBER(NVL(SYSDATE-PRODUCE_TIME,9999999999))*86400)),1,'n','y')")
  private String isExpired;

  @JsonProperty("isExpired")
  public boolean isExpired() {
    return "y".equals(this.isExpired);
  }

  // 供应商
  @Column(name = "MEAS_MAKER", length = 400)
  private String measMaker;

  @JsonProperty("measMaker")
  public String measMaker() {
    return this.measMaker;
  }

  public void measMaker(String measMaker) {
    this.measMaker = measMaker;
  }

  // 入库人
  @Column(name = "IN_NC", length = 400)
  private String inNC;

  @JsonProperty("inNC")
  public String inNC() {
    return this.inNC;
  }

  public void inNC(String inNC) {
    this.inNC = inNC;
  }

  // 入库时间
  @Column(name = "IN_DATE")
  private Timestamp inDate;

  @JsonProperty("inDate")
  public Timestamp inDate() {
    return this.inDate;
  }

  public void inDate(Timestamp inDate) {
    this.inDate = inDate;
  }

  // 前 ID
  @Column(name = "PREV_IN_ID")
  private Long prevInId;

  @JsonProperty("prevInId")
  public Long prevInId() {
    return this.prevInId;
  }

  public void prevInId(Long prevInId) {
    this.prevInId = prevInId;
  }

  // 复验状态
  @Column(name = "RETEST_STATUS", length = 400)
  private String retestStatus;

  @JsonProperty("retestStatus")
  public String retestStatus() {
    return this.retestStatus;
  }

  public void retestStatus(String retestStatus) {
    this.retestStatus = retestStatus;
  }

  // 复验 ID
  @Column(name = "RETEST_ID")
  private Long retestId;

  @JsonProperty("retestId")
  public Long retestId() {
    return this.retestId;
  }

  public void retestId(Long retestId) {
    this.retestId = retestId;
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

  // 复验合格证
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "CHECK_CERT_FILE", columnDefinition = "BLOB")
  private byte[] checkCertFile;

  // @JsonProperty("checkCertFile")
  public byte[] checkCertFile() {
    return this.checkCertFile;
  }

  public void checkCertFile(byte[] checkCertFile) {
    this.checkCertFile = checkCertFile;
  }

  // 原厂合格证
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "FACT_CERT_FILE", columnDefinition = "BLOB")
  private byte[] factCertFile;

  // @JsonProperty("factCertFile")
  public byte[] factCertFile() {
    return this.factCertFile;
  }

  public void factCertFile(byte[] factCertFile) {
    this.factCertFile = factCertFile;
  }
}
