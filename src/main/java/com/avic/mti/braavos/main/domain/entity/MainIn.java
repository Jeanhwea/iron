package com.avic.mti.iron.main.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.Formula;

/**
 * 主材入库实体类
 *
 * @author Jinghui Hu
 * @since 2020-10-29, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_MAIN_IN")
public class MainIn extends MesEntity {

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
  @Column(name = "MAIN_ID", nullable = false)
  private Long mainId;

  @JsonProperty("mainId")
  public Long mainId() {
    return this.mainId;
  }

  public void mainId(Long mainId) {
    this.mainId = mainId;
  }

  @Formula("(SELECT A.MAIN_CODE FROM TB_RSRC_MAIN A WHERE MAIN_ID = A.MYID)")
  private String mainCode;

  @JsonProperty("mainCode")
  public String mainCode() {
    return this.mainCode;
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

  // 主材名称
  @Column(name = "MAIN_NAME", length = 400)
  private String mainName;

  @JsonProperty("mainName")
  public String mainName() {
    return this.mainName;
  }

  public void mainName(String mainName) {
    this.mainName = mainName;
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

  // 主材类型
  @Column(name = "MAIN_TYPE", length = 400)
  private String mainType;

  @JsonProperty("mainType")
  public String mainType() {
    return this.mainType;
  }

  public void mainType(String mainType) {
    this.mainType = mainType;
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

  // 主材单价
  @Column(name = "MAIN_PRICE", nullable = false)
  private Double mainPrice;

  @JsonProperty("mainPrice")
  public Double mainPrice() {
    return this.mainPrice;
  }

  public void mainPrice(Double mainPrice) {
    this.mainPrice = mainPrice;
  }

  // 库存数量
  @Column(name = "MAIN_STK_NUM", nullable = false)
  private Long mainStkNum = 0L;

  @JsonProperty("mainStkNum")
  public Long mainStkNum() {
    return this.mainStkNum;
  }

  public void mainStkNum(Long mainStkNum) {
    this.mainStkNum = mainStkNum;
  }

  // 可用数量
  @Column(name = "MAIN_AVL_NUM", nullable = false)
  private Long mainAvlNum = 0L;

  @JsonProperty("mainAvlNum")
  public Long mainAvlNum() {
    return this.mainAvlNum;
  }

  public void mainAvlNum(Long mainAvlNum) {
    this.mainAvlNum = mainAvlNum;
  }

  // 占用数量
  @Formula("MAIN_STK_NUM - NVL(MAIN_AVL_NUM,0)")
  private Long mainOpcNum;

  @JsonProperty("mainOpcNum")
  public Long mainOpcNum() {
    return this.mainOpcNum;
  }

  // 计划数量
  @Column(name = "MAIN_PLN_NUM", nullable = false)
  private Long mainPlnNum = 0L;

  @JsonProperty("mainPlnNum")
  public Long mainPlnNum() {
    return this.mainPlnNum;
  }

  public void mainPlnNum(Long mainPlnNum) {
    this.mainPlnNum = mainPlnNum;
  }

  // 消耗数量
  @Formula("NVL(MAIN_PLN_NUM,0) - NVL(MAIN_STK_NUM,0)")
  private Long mainCsmNum;

  @JsonProperty("mainCsmNum")
  public Long mainCsmNum() {
    return this.mainCsmNum;
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
  @Column(name = "MAIN_MAKER", length = 400)
  private String mainMaker;

  @JsonProperty("mainMaker")
  public String mainMaker() {
    return this.mainMaker;
  }

  public void mainMaker(String mainMaker) {
    this.mainMaker = mainMaker;
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

  // 【待启用】
  @Column(name = "MAIN_VAR01_NUM")
  private Double mainVar01Num;

  @JsonProperty("mainVar01Num")
  public Double mainVar01Num() {
    return this.mainVar01Num;
  }

  public void mainVar01Num(Double mainVar01Num) {
    this.mainVar01Num = mainVar01Num;
  }

  // 【待启用】
  @Column(name = "MAIN_VAR02_NUM")
  private Double mainVar02Num;

  @JsonProperty("mainVar02Num")
  public Double mainVar02Num() {
    return this.mainVar02Num;
  }

  public void mainVar02Num(Double mainVar02Num) {
    this.mainVar02Num = mainVar02Num;
  }

  // 【待启用】
  @Column(name = "MAIN_VAR03_NUM")
  private Long mainVar03Num;

  @JsonProperty("mainVar03Num")
  public Long mainVar03Num() {
    return this.mainVar03Num;
  }

  public void mainVar03Num(Long mainVar03Num) {
    this.mainVar03Num = mainVar03Num;
  }

  // 【待启用】
  @Column(name = "MAIN_VAR04_NUM")
  private Long mainVar04Num;

  @JsonProperty("mainVar04Num")
  public Long mainVar04Num() {
    return this.mainVar04Num;
  }

  public void mainVar04Num(Long mainVar04Num) {
    this.mainVar04Num = mainVar04Num;
  }

  // 【待启用】
  @Column(name = "MAIN_VAR01_STR", length = 400)
  private String mainVar01Str;

  @JsonProperty("mainVar01Str")
  public String mainVar01Str() {
    return this.mainVar01Str;
  }

  public void mainVar01Str(String mainVar01Str) {
    this.mainVar01Str = mainVar01Str;
  }

  // 【待启用】
  @Column(name = "MAIN_VAR02_STR", length = 400)
  private String mainVar02Str;

  @JsonProperty("mainVar02Str")
  public String mainVar02Str() {
    return this.mainVar02Str;
  }

  public void mainVar02Str(String mainVar02Str) {
    this.mainVar02Str = mainVar02Str;
  }

  // 【待启用】
  @Column(name = "MAIN_VAR03_STR", length = 400)
  private String mainVar03Str;

  @JsonProperty("mainVar03Str")
  public String mainVar03Str() {
    return this.mainVar03Str;
  }

  public void mainVar03Str(String mainVar03Str) {
    this.mainVar03Str = mainVar03Str;
  }

  // 【待启用】
  @Column(name = "MAIN_VAR04_STR", length = 400)
  private String mainVar04Str;

  @JsonProperty("mainVar04Str")
  public String mainVar04Str() {
    return this.mainVar04Str;
  }

  public void mainVar04Str(String mainVar04Str) {
    this.mainVar04Str = mainVar04Str;
  }

  // 【待启用】
  @Column(name = "MAIN_VAR05_STR", length = 400)
  private String mainVar05Str;

  @JsonProperty("mainVar05Str")
  public String mainVar05Str() {
    return this.mainVar05Str;
  }

  public void mainVar05Str(String mainVar05Str) {
    this.mainVar05Str = mainVar05Str;
  }

  // 【待启用】
  @Column(name = "MAIN_VAR06_STR", length = 400)
  private String mainVar06Str;

  @JsonProperty("mainVar06Str")
  public String mainVar06Str() {
    return this.mainVar06Str;
  }

  public void mainVar06Str(String mainVar06Str) {
    this.mainVar06Str = mainVar06Str;
  }

  // 【待启用】
  @Column(name = "MAIN_VAR07_STR", length = 400)
  private String mainVar07Str;

  @JsonProperty("mainVar07Str")
  public String mainVar07Str() {
    return this.mainVar07Str;
  }

  public void mainVar07Str(String mainVar07Str) {
    this.mainVar07Str = mainVar07Str;
  }

  // 【待启用】
  @Column(name = "MAIN_VAR08_STR", length = 400)
  private String mainVar08Str;

  @JsonProperty("mainVar08Str")
  public String mainVar08Str() {
    return this.mainVar08Str;
  }

  public void mainVar08Str(String mainVar08Str) {
    this.mainVar08Str = mainVar08Str;
  }

  // 【待启用】
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "MAIN_EXT01_JSON", columnDefinition = "CLOB")
  private String mainExt01Json;

  @JsonProperty("mainExt01Json")
  public String mainExt01Json() {
    return this.mainExt01Json;
  }

  public void mainExt01Json(String mainExt01Json) {
    this.mainExt01Json = mainExt01Json;
  }

  // 【待启用】
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "MAIN_EXT02_JSON", columnDefinition = "CLOB")
  private String mainExt02Json;

  @JsonProperty("mainExt02Json")
  public String mainExt02Json() {
    return this.mainExt02Json;
  }

  public void mainExt02Json(String mainExt02Json) {
    this.mainExt02Json = mainExt02Json;
  }

  // 【待启用】
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "MAIN_EXT03_JSON", columnDefinition = "CLOB")
  private String mainExt03Json;

  @JsonProperty("mainExt03Json")
  public String mainExt03Json() {
    return this.mainExt03Json;
  }

  public void mainExt03Json(String mainExt03Json) {
    this.mainExt03Json = mainExt03Json;
  }

  // 【待启用】
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "MAIN_EXT04_JSON", columnDefinition = "CLOB")
  private String mainExt04Json;

  @JsonProperty("mainExt04Json")
  public String mainExt04Json() {
    return this.mainExt04Json;
  }

  public void mainExt04Json(String mainExt04Json) {
    this.mainExt04Json = mainExt04Json;
  }
}
