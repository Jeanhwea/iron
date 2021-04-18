package com.avic.mti.iron.vendor.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Table(name = "TB_RSRC_VEND")
public class Vendor extends MesEntity {

  // 供应商编码
  @Column(name = "VEND_CODE", length = 400, unique = true, nullable = false)
  private String vendCode;

  @JsonProperty("vendCode")
  public String vendCode() {
    return this.vendCode;
  }

  public void vendCode(String vendCode) {
    this.vendCode = vendCode;
  }

  // 供应商名称
  @Column(name = "VEND_NAME", length = 400, nullable = false)
  private String vendName;

  @JsonProperty("vendName")
  public String vendName() {
    return this.vendName;
  }

  public void vendName(String vendName) {
    this.vendName = vendName;
  }

  // 供应商简称
  @Column(name = "VEND_SNAME", length = 400, nullable = false)
  private String vendSname;

  @JsonProperty("vendSname")
  public String vendSname() {
    return this.vendSname;
  }

  public void vendSname(String vendSname) {
    this.vendSname = vendSname;
  }

  // 供应商类别
  @Column(name = "VEND_CATE", length = 400)
  private String vendCate;

  @JsonProperty("vendCate")
  public String vendCate() {
    return this.vendCate;
  }

  public void vendCate(String vendCate) {
    this.vendCate = vendCate;
  }

  // 供应商类型
  @Column(name = "VEND_TYPE", length = 400)
  private String vendType;

  @JsonProperty("vendType")
  public String vendType() {
    return this.vendType;
  }

  public void vendType(String vendType) {
    this.vendType = vendType;
  }

  // 供应商详细地址
  @Column(name = "VEND_ADDR", length = 4000)
  private String vendAddr;

  @JsonProperty("vendAddr")
  public String vendAddr() {
    return this.vendAddr;
  }

  public void vendAddr(String vendAddr) {
    this.vendAddr = vendAddr;
  }

  // 供应商联络人
  @Column(name = "LINKMAN", length = 400)
  private String linkman;

  @JsonProperty("linkman")
  public String linkman() {
    return this.linkman;
  }

  public void linkman(String linkman) {
    this.linkman = linkman;
  }

  // 联络人电话
  @Column(name = "LINKMAN_PHONE", length = 400)
  private String linkmanPhone;

  @JsonProperty("linkmanPhone")
  public String linkmanPhone() {
    return this.linkmanPhone;
  }

  public void linkmanPhone(String linkmanPhone) {
    this.linkmanPhone = linkmanPhone;
  }

  // 收货人
  @Column(name = "RECV_NAME", length = 400)
  private String recvName;

  @JsonProperty("recvName")
  public String recvName() {
    return this.recvName;
  }

  public void recvName(String recvName) {
    this.recvName = recvName;
  }

  // 收货人电话
  @Column(name = "RECV_PHONE", length = 400)
  private String recvPhone;

  @JsonProperty("recvPhone")
  public String recvPhone() {
    return this.recvPhone;
  }

  public void recvPhone(String recvPhone) {
    this.recvPhone = recvPhone;
  }

  // 收货人地址
  @Column(name = "RECV_ADDR", length = 4000)
  private String recvAddr;

  @JsonProperty("recvAddr")
  public String recvAddr() {
    return this.recvAddr;
  }

  public void recvAddr(String recvAddr) {
    this.recvAddr = recvAddr;
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
