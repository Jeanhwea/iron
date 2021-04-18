package com.avic.mti.iron.tool.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.avic.mti.iron.common.symbol.tool.ToolRoomCateEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 * 工具库房实体类
 *
 * @author Jinghui Hu
 * @since 2020-10-28, JDK1.8
 */
@Entity
@Table(name = "TB_RSRC_TOOL_ROOM")
public class ToolRoom extends MesEntity {

  // 库房类别
  @Column(name = "ROOM_CATE", length = 100, nullable = false)
  private String roomCate;

  @JsonProperty("roomCate")
  public String roomCate() {
    return this.roomCate;
  }

  public void roomCate(String roomCate) {
    this.roomCate = roomCate;
  }

  public void roomCate(int deepness) {
    this.roomCate = ToolRoomCateEnum.of(deepness).symbol();
  }

  // 库房编码
  @Column(name = "ROOM_CODE", length = 400, unique = true, nullable = false)
  private String roomCode;

  @JsonProperty("roomCode")
  public String roomCode() {
    return this.roomCode;
  }

  public void roomCode(String roomCode) {
    this.roomCode = roomCode;
  }

  // 库房名字
  @Column(name = "ROOM_NAME", length = 400, nullable = false)
  private String roomName;

  @JsonProperty("roomName")
  public String roomName() {
    return this.roomName;
  }

  public void roomName(String roomName) {
    this.roomName = roomName;
  }

  // 地理位置
  @Column(name = "LOCATION", length = 2000)
  private String location;

  @JsonProperty("location")
  public String location() {
    return this.location;
  }

  public void location(String location) {
    this.location = location;
  }

  // 上级库房 ID
  @Column(name = "PARENT_ROOM_ID")
  private Long parentRoomId;

  @JsonProperty("parentRoomId")
  public Long parentRoomId() {
    return this.parentRoomId;
  }

  public void parentRoomId(Long parentRoomId) {
    this.parentRoomId = parentRoomId;
  }

  // 区的数量
  @Column(name = "AREA_NUM")
  private Long areaNum;

  @JsonProperty("areaNum")
  public Long areaNum() {
    return this.areaNum;
  }

  public void areaNum(Long areaNum) {
    this.areaNum = areaNum;
  }

  // 架的数量
  @Column(name = "SHELF_NUM")
  private Long shelfNum;

  @JsonProperty("shelfNum")
  public Long shelfNum() {
    return this.shelfNum;
  }

  public void shelfNum(Long shelfNum) {
    this.shelfNum = shelfNum;
  }

  // 层的数量
  @Column(name = "FLOOR_NUM")
  private Long floorNum;

  @JsonProperty("floorNum")
  public Long floorNum() {
    return this.floorNum;
  }

  public void floorNum(Long floorNum) {
    this.floorNum = floorNum;
  }

  // 位的数量
  @Column(name = "PLACE_NUM")
  private Long placeNum;

  @JsonProperty("placeNum")
  public Long placeNum() {
    return this.placeNum;
  }

  public void placeNum(Long placeNum) {
    this.placeNum = placeNum;
  }

  // 格的数量
  @Column(name = "GRID_NUM")
  private Long gridNum;

  @JsonProperty("gridNum")
  public Long gridNum() {
    return this.gridNum;
  }

  public void gridNum(Long gridNum) {
    this.gridNum = gridNum;
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
}
