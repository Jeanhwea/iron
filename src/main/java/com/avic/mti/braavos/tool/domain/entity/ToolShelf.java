package com.avic.mti.iron.tool.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "TB_RSRC_TOOL_SHELF")
public class ToolShelf extends MesEntity {
  // 库房编码
  @Column(name = "ROOM_CODE", length = 400, nullable = false)
  private String roomCode;

  @JsonProperty("roomCode")
  public String roomCode() {
    return this.roomCode;
  }

  public void roomCode(String roomCode) {
    this.roomCode = roomCode;
  }

  // 库位编码
  @Column(name = "SHELF_CODE", length = 400, nullable = false)
  private String shelfCode;

  @JsonProperty("shelfCode")
  public String shelfCode() {
    return this.shelfCode;
  }

  public void shelfCode(String shelfCode) {
    this.shelfCode = shelfCode;
  }

  // 存放数量
  @Column(name = "STORE_NUM", nullable = false)
  private Long storeNum;

  @JsonProperty("storeNum")
  public Long storeNum() {
    return this.storeNum;
  }

  public void storeNum(Long storeNum) {
    this.storeNum = storeNum;
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

  // 明细 JSON
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "DETAIL_JSON", columnDefinition = "CLOB")
  private String detailJson;

  @JsonProperty("detailJson")
  public String detailJson() {
    return this.detailJson;
  }

  public void detailJson(String detailJson) {
    this.detailJson = detailJson;
  }
}
