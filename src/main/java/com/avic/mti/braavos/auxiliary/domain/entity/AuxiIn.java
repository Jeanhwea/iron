package com.avic.mti.iron.auxiliary.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 * 添加辅材入库实体
 *
 * @author Jinghui Hu
 * @since 2021-01-28, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_AUXI_IN")
public class AuxiIn extends MesEntity {

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
  @Column(name = "IN_TYPE", length = 100)
  private String inType;

  @JsonProperty("inType")
  public String inType() {
    return this.inType;
  }

  public void inType(String inType) {
    this.inType = inType;
  }

  // 辅材字典 ID
  @Column(name = "AUXI_ID", nullable = false)
  private Long auxiId;

  @JsonProperty("auxiId")
  public Long auxiId() {
    return this.auxiId;
  }

  public void auxiId(Long auxiId) {
    this.auxiId = auxiId;
  }

  // 批号/炉号
  @Column(name = "MTL_BATCH", length = 400)
  private String mtlBatch;

  @JsonProperty("mtlBatch")
  public String mtlBatch() {
    return this.mtlBatch;
  }

  public void mtlBatch(String mtlBatch) {
    this.mtlBatch = mtlBatch;
  }

  // 辅材名称
  @Column(name = "AUXI_NAME", length = 400)
  private String auxiName;

  @JsonProperty("auxiName")
  public String auxiName() {
    return this.auxiName;
  }

  public void auxiName(String auxiName) {
    this.auxiName = auxiName;
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

  // 辅材类型
  @Column(name = "AUXI_TYPE", length = 100)
  private String auxiType;

  @JsonProperty("auxiType")
  public String auxiType() {
    return this.auxiType;
  }

  public void auxiType(String auxiType) {
    this.auxiType = auxiType;
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

  // 辅材单价
  @Column(name = "AUXI_PRICE")
  private Double auxiPrice;

  @JsonProperty("auxiPrice")
  public Double auxiPrice() {
    return this.auxiPrice;
  }

  public void auxiPrice(Double auxiPrice) {
    this.auxiPrice = auxiPrice;
  }

  // 库存数量
  @Column(name = "AUXI_STK_NUM")
  private Long auxiStkNum;

  @JsonProperty("auxiStkNum")
  public Long auxiStkNum() {
    return this.auxiStkNum;
  }

  public void auxiStkNum(Long auxiStkNum) {
    this.auxiStkNum = auxiStkNum;
  }

  // 可用数量
  @Column(name = "AUXI_AVL_NUM")
  private Long auxiAvlNum;

  @JsonProperty("auxiAvlNum")
  public Long auxiAvlNum() {
    return this.auxiAvlNum;
  }

  public void auxiAvlNum(Long auxiAvlNum) {
    this.auxiAvlNum = auxiAvlNum;
  }

  // 计划数量
  @Column(name = "AUXI_PLN_NUM")
  private Long auxiPlnNum;

  @JsonProperty("auxiPlnNum")
  public Long auxiPlnNum() {
    return this.auxiPlnNum;
  }

  public void auxiPlnNum(Long auxiPlnNum) {
    this.auxiPlnNum = auxiPlnNum;
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

  // 供应商
  @Column(name = "AUXI_MAKER", length = 400)
  private String auxiMaker;

  @JsonProperty("auxiMaker")
  public String auxiMaker() {
    return this.auxiMaker;
  }

  public void auxiMaker(String auxiMaker) {
    this.auxiMaker = auxiMaker;
  }

  // 入库人
  @Column(name = "IN_NC", length = 400, nullable = false)
  private String inNC;

  @JsonProperty("inNC")
  public String inNC() {
    return this.inNC;
  }

  public void inNC(String inNC) {
    this.inNC = inNC;
  }

  // 入库时间
  @Column(name = "IN_DATE", nullable = false)
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
  @Column(name = "RETEST_STATUS", length = 100)
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
  @Column(name = "SHELF_TEXT", length = 100)
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

  @Override
  public String toString() {
    return super.toString()
        + "{auxiStkNum="
        + auxiStkNum
        + ", auxiAvlNum="
        + auxiAvlNum
        + ", auxiPlnNum="
        + auxiPlnNum
        + '}';
  }
}
