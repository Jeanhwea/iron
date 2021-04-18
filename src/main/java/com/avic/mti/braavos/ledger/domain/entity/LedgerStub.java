package com.avic.mti.iron.ledger.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 * 资源出库单实体类
 *
 * @author Jinghui Hu
 * @since 2020-11-03, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_LEDG_STUB")
public class LedgerStub extends MesEntity {

  // 出库单编码
  @Column(name = "STUB_CODE", length = 400, unique = true, nullable = false)
  private String stubCode;

  @JsonProperty("stubCode")
  public String stubCode() {
    return this.stubCode;
  }

  public void stubCode(String stubCode) {
    this.stubCode = stubCode;
  }

  // 出库单名称
  @Column(name = "STUB_NAME", length = 400, nullable = false)
  private String stubName;

  @JsonProperty("stubName")
  public String stubName() {
    return this.stubName;
  }

  public void stubName(String stubName) {
    this.stubName = stubName;
  }

  // 出库单状态
  @Column(name = "STUB_STATUS", length = 400, nullable = false)
  private String stubStatus;

  @JsonProperty("stubStatus")
  public String stubStatus() {
    return this.stubStatus;
  }

  public void stubStatus(String stubStatus) {
    this.stubStatus = stubStatus;
  }

  // 出库单类别
  @Column(name = "STUB_CATE", length = 400, nullable = false)
  private String stubCate;

  @JsonProperty("stubCate")
  public String stubCate() {
    return this.stubCate;
  }

  public void stubCate(String stubCate) {
    this.stubCate = stubCate;
  }

  // 出库单类型
  @Column(name = "STUB_TYPE", length = 400, nullable = false)
  private String stubType;

  @JsonProperty("stubType")
  public String stubType() {
    return this.stubType;
  }

  public void stubType(String stubType) {
    this.stubType = stubType;
  }

  // 出库单标记
  @Column(name = "STUB_FLAG", length = 400, nullable = false)
  private String stubFlag;

  @JsonProperty("stubFlag")
  public String stubFlag() {
    return this.stubFlag;
  }

  public void stubFlag(String stubFlag) {
    this.stubFlag = stubFlag;
  }

  // 出库单标签
  @Column(name = "STUB_TAG", length = 400, nullable = false)
  private String stubTag;

  @JsonProperty("stubTag")
  public String stubTag() {
    return this.stubTag;
  }

  public void stubTag(String stubTag) {
    this.stubTag = stubTag;
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
