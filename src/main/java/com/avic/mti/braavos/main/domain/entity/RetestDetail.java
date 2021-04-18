package com.avic.mti.iron.main.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 * 主材复验明细实体
 *
 * @author Jinghui Hu
 * @since 2020-12-04, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_MAIN_RETEST_DET")
public class RetestDetail extends MesEntity {

  // 主材复验主表 ID
  @Column(name = "RETEST_ID", nullable = false)
  private Long retestId;

  @JsonProperty("retestId")
  public Long retestId() {
    return this.retestId;
  }

  public void retestId(Long retestId) {
    this.retestId = retestId;
  }

  // 入库 ID
  @Column(name = "IN_ID", nullable = false)
  private Long inId;

  @JsonProperty("inId")
  public Long inId() {
    return this.inId;
  }

  public void inId(Long inId) {
    this.inId = inId;
  }

  // 入库编码
  @Column(name = "IN_CODE", length = 400)
  private String inCode;

  @JsonProperty("inCode")
  public String inCode() {
    return this.inCode;
  }

  public void inCode(String inCode) {
    this.inCode = inCode;
  }

  // 主材字典 ID
  @Column(name = "MAIN_ID", nullable = false)
  private Long mainId;

  @JsonProperty("mainId")
  public Long mainId() {
    return this.mainId;
  }

  public void mainId(Long mainId) {
    this.mainId = mainId;
  }

  // 主材编码
  @Column(name = "MAIN_CODE", length = 400)
  private String mainCode;

  @JsonProperty("mainCode")
  public String mainCode() {
    return this.mainCode;
  }

  public void mainCode(String mainCode) {
    this.mainCode = mainCode;
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

  // 复验项编码
  @Column(name = "SMPL_CODE", length = 400)
  private String smplCode;

  @JsonProperty("smplCode")
  public String smplCode() {
    return this.smplCode;
  }

  public void smplCode(String smplCode) {
    this.smplCode = smplCode;
  }

  // 复验项名称
  @Column(name = "SMPL_NAME", length = 400)
  private String smplName;

  @JsonProperty("smplName")
  public String smplName() {
    return this.smplName;
  }

  public void smplName(String smplName) {
    this.smplName = smplName;
  }

  // 复验结果
  @Column(name = "RETEST_STATUS", length = 400)
  private String retestStatus;

  @JsonProperty("retestStatus")
  public String retestStatus() {
    return this.retestStatus;
  }

  public void retestStatus(String retestStatus) {
    this.retestStatus = retestStatus;
  }

  // 申请数量
  @Column(name = "RETEST_NUM01")
  private Long retestNum01;

  @JsonProperty("retestNum01")
  public Long retestNum01() {
    return this.retestNum01;
  }

  public void retestNum01(Long retestNum01) {
    this.retestNum01 = retestNum01;
  }

  // 申请数量单位
  @Column(name = "RETEST_UOM01", length = 400)
  private String retestUom01;

  @JsonProperty("retestUom01")
  public String retestUom01() {
    return this.retestUom01;
  }

  public void retestUom01(String retestUom01) {
    this.retestUom01 = retestUom01;
  }

  // 申请件数
  @Column(name = "RETEST_NUM02")
  private Long retestNum02;

  @JsonProperty("retestNum02")
  public Long retestNum02() {
    return this.retestNum02;
  }

  public void retestNum02(Long retestNum02) {
    this.retestNum02 = retestNum02;
  }

  // 申请件数单位
  @Column(name = "RETEST_UOM02", length = 400)
  private String retestUom02;

  @JsonProperty("retestUom02")
  public String retestUom02() {
    return this.retestUom02;
  }

  public void retestUom02(String retestUom02) {
    this.retestUom02 = retestUom02;
  }

  // 复验数字变量 01
  @Column(name = "RETEST_VAR01_NUM")
  private Long retestVar01Num;

  @JsonProperty("retestVar01Num")
  public Long retestVar01Num() {
    return this.retestVar01Num;
  }

  public void retestVar01Num(Long retestVar01Num) {
    this.retestVar01Num = retestVar01Num;
  }

  // 复验数字变量 02
  @Column(name = "RETEST_VAR02_NUM")
  private Long retestVar02Num;

  @JsonProperty("retestVar02Num")
  public Long retestVar02Num() {
    return this.retestVar02Num;
  }

  public void retestVar02Num(Long retestVar02Num) {
    this.retestVar02Num = retestVar02Num;
  }

  // 复验数字变量 03
  @Column(name = "RETEST_VAR03_NUM")
  private Long retestVar03Num;

  @JsonProperty("retestVar03Num")
  public Long retestVar03Num() {
    return this.retestVar03Num;
  }

  public void retestVar03Num(Long retestVar03Num) {
    this.retestVar03Num = retestVar03Num;
  }

  // 复验数字变量 04
  @Column(name = "RETEST_VAR04_NUM")
  private Long retestVar04Num;

  @JsonProperty("retestVar04Num")
  public Long retestVar04Num() {
    return this.retestVar04Num;
  }

  public void retestVar04Num(Long retestVar04Num) {
    this.retestVar04Num = retestVar04Num;
  }

  // 复验数字变量 05
  @Column(name = "RETEST_VAR05_NUM")
  private Long retestVar05Num;

  @JsonProperty("retestVar05Num")
  public Long retestVar05Num() {
    return this.retestVar05Num;
  }

  public void retestVar05Num(Long retestVar05Num) {
    this.retestVar05Num = retestVar05Num;
  }

  // 复验字符串变量 01
  @Column(name = "RETEST_VAR01_STR", length = 400)
  private String retestVar01Str;

  @JsonProperty("retestVar01Str")
  public String retestVar01Str() {
    return this.retestVar01Str;
  }

  public void retestVar01Str(String retestVar01Str) {
    this.retestVar01Str = retestVar01Str;
  }

  // 复验字符串变量 02
  @Column(name = "RETEST_VAR02_STR", length = 400)
  private String retestVar02Str;

  @JsonProperty("retestVar02Str")
  public String retestVar02Str() {
    return this.retestVar02Str;
  }

  public void retestVar02Str(String retestVar02Str) {
    this.retestVar02Str = retestVar02Str;
  }

  // 复验字符串变量 03
  @Column(name = "RETEST_VAR03_STR", length = 400)
  private String retestVar03Str;

  @JsonProperty("retestVar03Str")
  public String retestVar03Str() {
    return this.retestVar03Str;
  }

  public void retestVar03Str(String retestVar03Str) {
    this.retestVar03Str = retestVar03Str;
  }

  // 复验字符串变量 04
  @Column(name = "RETEST_VAR04_STR", length = 400)
  private String retestVar04Str;

  @JsonProperty("retestVar04Str")
  public String retestVar04Str() {
    return this.retestVar04Str;
  }

  public void retestVar04Str(String retestVar04Str) {
    this.retestVar04Str = retestVar04Str;
  }

  // 复验字符串变量 05
  @Column(name = "RETEST_VAR05_STR", length = 400)
  private String retestVar05Str;

  @JsonProperty("retestVar05Str")
  public String retestVar05Str() {
    return this.retestVar05Str;
  }

  public void retestVar05Str(String retestVar05Str) {
    this.retestVar05Str = retestVar05Str;
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

  // 创建时间
  @Column(name = "CREATE_DATE", nullable = false)
  private Timestamp createDate;

  @JsonProperty("createDate")
  public Timestamp createDate() {
    return this.createDate;
  }

  public void createDate(Timestamp createDate) {
    this.createDate = createDate;
  }
}
