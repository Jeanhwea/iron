package com.avic.mti.iron.waste.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.Formula;

/**
 * 废品入库实体类
 *
 * @author Jinghui Hu
 * @since 2020-10-29, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_WPRD_IN")
public class WprdIn extends MesEntity {

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
  @Column(name = "WPRD_ID", nullable = false)
  private Long wprdId;

  @JsonProperty("wprdId")
  public Long wprdId() {
    return this.wprdId;
  }

  public void wprdId(Long wprdId) {
    this.wprdId = wprdId;
  }

  @Formula("(SELECT A.WPRD_CODE FROM TB_RSRC_WPRD A WHERE WPRD_ID = A.MYID)")
  private String wprdCode;

  @JsonProperty("wprdCode")
  public String wprdCode() {
    return this.wprdCode;
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

  // 废品名称
  @Column(name = "WPRD_NAME", length = 400)
  private String wprdName;

  @JsonProperty("wprdName")
  public String wprdName() {
    return this.wprdName;
  }

  public void wprdName(String wprdName) {
    this.wprdName = wprdName;
  }

  // 废品规格
  @Column(name = "WPRD_SPEC", length = 400)
  private String wprdSpec;

  @JsonProperty("wprdSpec")
  public String wprdSpec() {
    return this.wprdSpec;
  }

  public void wprdSpec(String wprdSpec) {
    this.wprdSpec = wprdSpec;
  }

  // 废品类别
  @Column(name = "WPRD_CATE", length = 100)
  private String wprdCate;

  @JsonProperty("wprdCate")
  public String wprdCate() {
    return this.wprdCate;
  }

  public void wprdCate(String wprdCate) {
    this.wprdCate = wprdCate;
  }

  // 废品类型
  @Column(name = "WPRD_TYPE", length = 400)
  private String wprdType;

  @JsonProperty("wprdType")
  public String wprdType() {
    return this.wprdType;
  }

  public void wprdType(String wprdType) {
    this.wprdType = wprdType;
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

  // 废品单价
  @Column(name = "WPRD_PRICE", nullable = false)
  private Double wprdPrice;

  @JsonProperty("wprdPrice")
  public Double wprdPrice() {
    return this.wprdPrice;
  }

  public void wprdPrice(Double wprdPrice) {
    this.wprdPrice = wprdPrice;
  }

  // 库存数量
  @Column(name = "WPRD_STK_NUM", nullable = false)
  private Long wprdStkNum = 0L;

  @JsonProperty("wprdStkNum")
  public Long wprdStkNum() {
    return this.wprdStkNum;
  }

  public void wprdStkNum(Long wprdStkNum) {
    this.wprdStkNum = wprdStkNum;
  }

  // 可用数量
  @Column(name = "WPRD_AVL_NUM", nullable = false)
  private Long wprdAvlNum = 0L;

  @JsonProperty("wprdAvlNum")
  public Long wprdAvlNum() {
    return this.wprdAvlNum;
  }

  public void wprdAvlNum(Long wprdAvlNum) {
    this.wprdAvlNum = wprdAvlNum;
  }

  // 计划数量
  @Column(name = "WPRD_PLN_NUM", nullable = false)
  private Long wprdPlnNum = 0L;

  @JsonProperty("wprdPlnNum")
  public Long wprdPlnNum() {
    return this.wprdPlnNum;
  }

  public void wprdPlnNum(Long wprdPlnNum) {
    this.wprdPlnNum = wprdPlnNum;
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

  // 供应商
  @Column(name = "WPRD_MAKER", length = 400)
  private String wprdMaker;

  @JsonProperty("wprdMaker")
  public String wprdMaker() {
    return this.wprdMaker;
  }

  public void wprdMaker(String wprdMaker) {
    this.wprdMaker = wprdMaker;
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
