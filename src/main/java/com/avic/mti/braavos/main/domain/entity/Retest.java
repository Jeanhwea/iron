package com.avic.mti.iron.main.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.Formula;

/**
 * 主材复验实体
 *
 * @author Jinghui Hu
 * @since 2020-12-04, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_MAIN_RETEST")
public class Retest extends MesEntity {

  // 复验编码
  @Column(name = "RETEST_CODE", length = 400, nullable = false)
  private String retestCode;

  @JsonProperty("retestCode")
  public String retestCode() {
    return this.retestCode;
  }

  public void retestCode(String retestCode) {
    this.retestCode = retestCode;
  }

  // 复验单位编码
  @Column(name = "RETEST_DEPT_CODE", length = 400)
  private String retestDeptCode;

  @JsonProperty("retestDeptCode")
  public String retestDeptCode() {
    return this.retestDeptCode;
  }

  public void retestDeptCode(String retestDeptCode) {
    this.retestDeptCode = retestDeptCode;
  }

  // 复验单位名称
  @Column(name = "RETEST_DEPT_NAME", length = 400)
  private String retestDeptName;

  @JsonProperty("retestDeptName")
  public String retestDeptName() {
    return this.retestDeptName;
  }

  public void retestDeptName(String retestDeptName) {
    this.retestDeptName = retestDeptName;
  }

  // 复验人
  @Column(name = "RETESTER", length = 400)
  private String retester;

  @JsonProperty("retester")
  public String retester() {
    return this.retester;
  }

  public void retester(String retester) {
    this.retester = retester;
  }

  // 复验时间
  @Column(name = "RETEST_TIME")
  private Timestamp retestTime;

  @JsonProperty("retestTime")
  public Timestamp retestTime() {
    return this.retestTime;
  }

  public void retestTime(Timestamp retestTime) {
    this.retestTime = retestTime;
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

  // 复验报告页数
  @Column(name = "RETEST_FILE_PAGE")
  private Long retestFilePage;

  @JsonProperty("retestFilePage")
  public Long retestFilePage() {
    return this.retestFilePage;
  }

  public void retestFilePage(Long retestFilePage) {
    this.retestFilePage = retestFilePage;
  }

  // 复验报告名称
  @Column(name = "RETEST_FILE_PATH", length = 4000)
  private String retestFilePath;

  @JsonProperty("retestFilePath")
  public String retestFilePath() {
    return this.retestFilePath;
  }

  public void retestFilePath(String retestFilePath) {
    this.retestFilePath = retestFilePath;
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

  // 处理单编号
  @Column(name = "SHEET_CODE", length = 4000)
  private String sheetCode;

  @JsonProperty("sheetCode")
  public String sheetCode() {
    return this.sheetCode;
  }

  public void sheetCode(String sheetCode) {
    this.sheetCode = sheetCode;
  }

  // 复验结论
  @Column(name = "RESULT_TEXT", length = 4000)
  private String resultText;

  @JsonProperty("resultText")
  public String resultText() {
    return this.resultText;
  }

  public void resultText(String resultText) {
    this.resultText = resultText;
  }

  // 技术标准/复验规程
  @Column(name = "RETEST_STANDARD", length = 400)
  private String retestStandard;

  @JsonProperty("retestStandard")
  public String retestStandard() {
    return this.retestStandard;
  }

  public void retestStandard(String retestStandard) {
    this.retestStandard = retestStandard;
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

  // 数字变量 01
  @Column(name = "VAR01_NUM")
  private Long var01Num;

  @JsonProperty("var01Num")
  public Long var01Num() {
    return this.var01Num;
  }

  public void var01Num(Long var01Num) {
    this.var01Num = var01Num;
  }

  // 数字变量 02
  @Column(name = "VAR02_NUM")
  private Long var02Num;

  @JsonProperty("var02Num")
  public Long var02Num() {
    return this.var02Num;
  }

  public void var02Num(Long var02Num) {
    this.var02Num = var02Num;
  }

  // 数字变量 03
  @Column(name = "VAR03_NUM")
  private Long var03Num;

  @JsonProperty("var03Num")
  public Long var03Num() {
    return this.var03Num;
  }

  public void var03Num(Long var03Num) {
    this.var03Num = var03Num;
  }

  // 数字变量 04
  @Column(name = "VAR04_NUM")
  private Long var04Num;

  @JsonProperty("var04Num")
  public Long var04Num() {
    return this.var04Num;
  }

  public void var04Num(Long var04Num) {
    this.var04Num = var04Num;
  }

  // 数字变量 05
  @Column(name = "VAR05_NUM")
  private Long var05Num;

  @JsonProperty("var05Num")
  public Long var05Num() {
    return this.var05Num;
  }

  public void var05Num(Long var05Num) {
    this.var05Num = var05Num;
  }

  // 字符串变量 01
  @Column(name = "VAR01_STR", length = 400)
  private String var01Str;

  @JsonProperty("var01Str")
  public String var01Str() {
    return this.var01Str;
  }

  public void var01Str(String var01Str) {
    this.var01Str = var01Str;
  }

  // 字符串变量 02
  @Column(name = "VAR02_STR", length = 400)
  private String var02Str;

  @JsonProperty("var02Str")
  public String var02Str() {
    return this.var02Str;
  }

  public void var02Str(String var02Str) {
    this.var02Str = var02Str;
  }

  // 字符串变量 03
  @Column(name = "VAR03_STR", length = 400)
  private String var03Str;

  @JsonProperty("var03Str")
  public String var03Str() {
    return this.var03Str;
  }

  public void var03Str(String var03Str) {
    this.var03Str = var03Str;
  }

  // 字符串变量 04
  @Column(name = "VAR04_STR", length = 400)
  private String var04Str;

  @JsonProperty("var04Str")
  public String var04Str() {
    return this.var04Str;
  }

  public void var04Str(String var04Str) {
    this.var04Str = var04Str;
  }

  // 字符串变量 05
  @Column(name = "VAR05_STR", length = 400)
  private String var05Str;

  @JsonProperty("var05Str")
  public String var05Str() {
    return this.var05Str;
  }

  public void var05Str(String var05Str) {
    this.var05Str = var05Str;
  }

  // 复验项目集合 JSON
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "RETEST_ITEM_JSON", columnDefinition = "CLOB")
  private String retestItemJson;

  @JsonProperty("retestItemJson")
  public String retestItemJson() {
    return this.retestItemJson;
  }

  public void retestItemJson(String retestItemJson) {
    this.retestItemJson = retestItemJson;
  }

  @Formula("NVL2(RETEST_FILE_BLOB, 'y', 'n')")
  private String hasRetestFile;

  @JsonProperty("hasRetestFile")
  public boolean hasRetestFile() {
    return "y".equals(hasRetestFile);
  }

  // 复验报告文件
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "RETEST_FILE_BLOB", columnDefinition = "BLOB")
  private byte[] retestFileBlob;

  // @JsonProperty("retestFileBlob")
  public byte[] retestFileBlob() {
    return this.retestFileBlob;
  }

  public void retestFileBlob(byte[] retestFileBlob) {
    this.retestFileBlob = retestFileBlob;
  }
}
