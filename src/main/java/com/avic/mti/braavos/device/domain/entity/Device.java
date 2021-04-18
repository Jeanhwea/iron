package com.avic.mti.iron.device.domain.entity;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.avic.mti.iron.common.symbol.device.DeviceStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "TB_RSRC_DEVC")
public class Device extends MesEntity {

  // 设备编码
  @Column(name = "DEVC_CODE", length = 400, unique = true, nullable = false)
  private String devcCode;

  @JsonProperty("devcCode")
  public String devcCode() {
    return this.devcCode;
  }

  public void devcCode(String devcCode) {
    this.devcCode = devcCode;
  }

  // 设备名称
  @Column(name = "DEVC_NAME", length = 400, nullable = false)
  private String devcName;

  @JsonProperty("devcName")
  public String devcName() {
    return this.devcName;
  }

  public void devcName(String devcName) {
    this.devcName = devcName;
  }

  // 设备规格
  @Column(name = "DEVC_SPEC", length = 400)
  private String devcSpec;

  @JsonProperty("devcSpec")
  public String devcSpec() {
    return this.devcSpec;
  }

  public void devcSpec(String devcSpec) {
    this.devcSpec = devcSpec;
  }

  // 设备分类 ID
  @Column(name = "DEVC_CATE_ID")
  private Long devcCateId;

  @JsonProperty("devcCateId")
  public Long devcCateId() {
    return this.devcCateId;
  }

  public void devcCateId(Long devcCateId) {
    this.devcCateId = devcCateId;
  }

  // 设备分类
  @Formula("(SELECT A.CATE_NAME FROM TB_RSRC_DEVC_CATE A WHERE DEVC_CATE_ID = A.MYID)")
  private String devcCate;

  @JsonProperty("devcCate")
  public String devcCate() {
    return this.devcCate;
  }

  // 所属部门
  @Column(name = "DEVC_DEPT", length = 400)
  private String devcDept;

  @JsonProperty("devcDept")
  public String devcDept() {
    return this.devcDept;
  }

  public void devcDept(String devcDept) {
    this.devcDept = devcDept;
  }

  // 负责人
  @Column(name = "DEVC_ADMIN", length = 400)
  private String devcAdmin;

  @JsonProperty("devcAdmin")
  public String devcAdmin() {
    return this.devcAdmin;
  }

  public void devcAdmin(String devcAdmin) {
    this.devcAdmin = devcAdmin;
  }

  // 制造厂商
  @Column(name = "DEVC_MAKER", length = 400)
  private String devcMaker;

  @JsonProperty("devcMaker")
  public String devcMaker() {
    return this.devcMaker;
  }

  public void devcMaker(String devcMaker) {
    this.devcMaker = devcMaker;
  }

  // 设备状态
  @Column(name = "DEVC_STATUS", length = 400)
  private String devcStatus = DeviceStatusEnum.Enum1_ZCYZ.symbol();

  @JsonProperty("devcStatus")
  public String devcStatus() {
    return this.devcStatus;
  }

  public void devcStatus(String devcStatus) {
    this.devcStatus = devcStatus;
  }

  // 设备类型
  @Column(name = "DEVC_TYPE", length = 100)
  private String devcType;

  @JsonProperty("devcType")
  public String devcType() {
    return this.devcType;
  }

  public void devcType(String devcType) {
    this.devcType = devcType;
  }

  // 入库种类
  @Column(name = "IN_TYPE", length = 400)
  private String inType;

  @JsonProperty("inType")
  public String inType() {
    return this.inType;
  }

  public void inType(String inType) {
    this.inType = inType;
  }

  // 入库单据
  @Column(name = "IN_STUB", length = 400)
  private String inStub;

  @JsonProperty("inStub")
  public String inStub() {
    return this.inStub;
  }

  public void inStub(String inStub) {
    this.inStub = inStub;
  }

  // 入账人
  @Column(name = "IN_NC", length = 400)
  private String inNC;

  @JsonProperty("inNC")
  public String inNC() {
    return this.inNC;
  }

  public void inNC(String inNC) {
    this.inNC = inNC;
  }

  // 入账日期
  @Column(name = "IN_DATE")
  private Timestamp inDate;

  @JsonProperty("inDate")
  public Timestamp inDate() {
    return this.inDate;
  }

  public void inDate(Timestamp inDate) {
    this.inDate = inDate;
  }

  // 设备并行能力
  @Column(name = "MAX_JOB_COUNT")
  private Long maxJobCount = 1L;

  @JsonProperty("maxJobCount")
  public Long maxJobCount() {
    return this.maxJobCount;
  }

  public void maxJobCount(Long maxJobCount) {
    this.maxJobCount = maxJobCount;
  }

  // 使用年限
  @Column(name = "SERVICE_LIFE")
  private Timestamp serviceLife;

  @JsonProperty("serviceLife")
  public Timestamp serviceLife() {
    return this.serviceLife;
  }

  public void serviceLife(Timestamp serviceLife) {
    this.serviceLife = serviceLife;
  }

  // 设备数控系统 ID
  @Column(name = "DEVC_NCS_ID")
  private Long devcNCSId;

  @JsonProperty("devcNCSId")
  public Long devcNCSId() {
    return this.devcNCSId;
  }

  public void devcNCSId(Long devcNCSId) {
    this.devcNCSId = devcNCSId;
  }

  // 设备数控系统
  @Formula("(SELECT A.NCS_NAME FROM TB_RSRC_DEVC_NCS A WHERE DEVC_NCS_ID = A.MYID)")
  private String devcNCS;

  @JsonProperty("devcNCS")
  public String devcNCS() {
    return this.devcCate;
  }

  // 设备功率
  @Column(name = "DEVC_POWER", length = 400)
  private String devcPower;

  @JsonProperty("devcPower")
  public String devcPower() {
    return this.devcPower;
  }

  public void devcPower(String devcPower) {
    this.devcPower = devcPower;
  }

  // 是否联网
  @Column(name = "CONN_STATUS", length = 400)
  private String connStatus;

  @JsonProperty("connStatus")
  public String connStatus() {
    return this.connStatus;
  }

  public void connStatus(String connStatus) {
    this.connStatus = connStatus;
  }

  // 固定资产
  @Column(name = "ASSET", length = 400)
  private String asset;

  @JsonProperty("asset")
  public String asset() {
    return this.asset;
  }

  public void asset(String asset) {
    this.asset = asset;
  }

  // 资产类别
  @Column(name = "ASSET_CATE", length = 400)
  private String assetCate;

  @JsonProperty("assetCate")
  public String assetCate() {
    return this.assetCate;
  }

  public void assetCate(String assetCate) {
    this.assetCate = assetCate;
  }

  // 保养周期
  @Column(name = "MAINT_PERIOD", length = 400)
  private String maintPeriod;

  @JsonProperty("maintPeriod")
  public String maintPeriod() {
    return this.maintPeriod;
  }

  public void maintPeriod(String maintPeriod) {
    this.maintPeriod = maintPeriod;
  }

  // 保养记录
  @Column(name = "MAINT_RECORD", length = 400)
  private String maintRecord;

  @JsonProperty("maintRecord")
  public String maintRecord() {
    return this.maintRecord;
  }

  public void maintRecord(String maintRecord) {
    this.maintRecord = maintRecord;
  }

  // 备注
  @Column(name = "NOTE", length = 400)
  private String note;

  @JsonProperty("note")
  public String note() {
    return this.note;
  }

  public void note(String note) {
    this.note = note;
  }

  // 主要参数
  @Column(name = "MAIN_PARA", length = 400)
  private String mainPara;

  @JsonProperty("mainPara")
  public String mainPara() {
    return this.mainPara;
  }

  public void mainPara(String mainPara) {
    this.mainPara = mainPara;
  }

  // 设备图片名称
  @Column(name = "DEVC_IMAGE_NAME", length = 400)
  private String devcImageName;

  @JsonProperty("devcImageName")
  public String devcImageName() {
    return this.devcImageName;
  }

  public void devcImageName(String devcImageName) {
    this.devcImageName = devcImageName;
  }

  // 设备图片
  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "DEVC_IMAGE_FILE", columnDefinition = "BLOB")
  private byte[] devcImageFile;

  // @JsonProperty("devcImageFile")
  public byte[] devcImageFile() {
    return this.devcImageFile;
  }

  public void devcImageFile(byte[] devcImageFile) {
    this.devcImageFile = devcImageFile;
  }

  @Formula("NVL2(DEVC_IMAGE_FILE, 'y', 'n')")
  private String hasImageFile;

  @JsonProperty("hasImageFile")
  public boolean hasImageFile() {
    return "y".equals(hasImageFile);
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

  // 【待启用】
  @Column(name = "DBL_DEVC_VAR01")
  private Double dblDevcVar01;

  @JsonProperty("dblDevcVar01")
  public Double dblDevcVar01() {
    return this.dblDevcVar01;
  }

  public void dblDevcVar01(Double dblDevcVar01) {
    this.dblDevcVar01 = dblDevcVar01;
  }

  // 【待启用】
  @Column(name = "DBL_DEVC_VAR02")
  private Double dblDevcVar02;

  @JsonProperty("dblDevcVar02")
  public Double dblDevcVar02() {
    return this.dblDevcVar02;
  }

  public void dblDevcVar02(Double dblDevcVar02) {
    this.dblDevcVar02 = dblDevcVar02;
  }

  // 【待启用】
  @Column(name = "DBL_DEVC_VAR03")
  private Double dblDevcVar03;

  @JsonProperty("dblDevcVar03")
  public Double dblDevcVar03() {
    return this.dblDevcVar03;
  }

  public void dblDevcVar03(Double dblDevcVar03) {
    this.dblDevcVar03 = dblDevcVar03;
  }

  // 【待启用】
  @Column(name = "DBL_DEVC_VAR04")
  private Double dblDevcVar04;

  @JsonProperty("dblDevcVar04")
  public Double dblDevcVar04() {
    return this.dblDevcVar04;
  }

  public void dblDevcVar04(Double dblDevcVar04) {
    this.dblDevcVar04 = dblDevcVar04;
  }

  // 【待启用】
  @Column(name = "INT_DEVC_VAR01")
  private Long intDevcVar01;

  @JsonProperty("intDevcVar01")
  public Long intDevcVar01() {
    return this.intDevcVar01;
  }

  public void intDevcVar01(Long intDevcVar01) {
    this.intDevcVar01 = intDevcVar01;
  }

  // 【待启用】
  @Column(name = "INT_DEVC_VAR02")
  private Long intDevcVar02;

  @JsonProperty("intDevcVar02")
  public Long intDevcVar02() {
    return this.intDevcVar02;
  }

  public void intDevcVar02(Long intDevcVar02) {
    this.intDevcVar02 = intDevcVar02;
  }

  // 【待启用】
  @Column(name = "INT_DEVC_VAR03")
  private Long intDevcVar03;

  @JsonProperty("intDevcVar03")
  public Long intDevcVar03() {
    return this.intDevcVar03;
  }

  public void intDevcVar03(Long intDevcVar03) {
    this.intDevcVar03 = intDevcVar03;
  }

  // 【待启用】
  @Column(name = "INT_DEVC_VAR04")
  private Long intDevcVar04;

  @JsonProperty("intDevcVar04")
  public Long intDevcVar04() {
    return this.intDevcVar04;
  }

  public void intDevcVar04(Long intDevcVar04) {
    this.intDevcVar04 = intDevcVar04;
  }

  // 【待启用】
  @Column(name = "STR_DEVC_VAR01", length = 400)
  private String strDevcVar01;

  @JsonProperty("strDevcVar01")
  public String strDevcVar01() {
    return this.strDevcVar01;
  }

  public void strDevcVar01(String strDevcVar01) {
    this.strDevcVar01 = strDevcVar01;
  }

  // 【待启用】
  @Column(name = "STR_DEVC_VAR02", length = 400)
  private String strDevcVar02;

  @JsonProperty("strDevcVar02")
  public String strDevcVar02() {
    return this.strDevcVar02;
  }

  public void strDevcVar02(String strDevcVar02) {
    this.strDevcVar02 = strDevcVar02;
  }

  // 【待启用】
  @Column(name = "STR_DEVC_VAR03", length = 400)
  private String strDevcVar03;

  @JsonProperty("strDevcVar03")
  public String strDevcVar03() {
    return this.strDevcVar03;
  }

  public void strDevcVar03(String strDevcVar03) {
    this.strDevcVar03 = strDevcVar03;
  }

  // 【待启用】
  @Column(name = "STR_DEVC_VAR04", length = 400)
  private String strDevcVar04;

  @JsonProperty("strDevcVar04")
  public String strDevcVar04() {
    return this.strDevcVar04;
  }

  public void strDevcVar04(String strDevcVar04) {
    this.strDevcVar04 = strDevcVar04;
  }

  // 【待启用】
  @Column(name = "STR_DEVC_VAR05", length = 400)
  private String strDevcVar05;

  @JsonProperty("strDevcVar05")
  public String strDevcVar05() {
    return this.strDevcVar05;
  }

  public void strDevcVar05(String strDevcVar05) {
    this.strDevcVar05 = strDevcVar05;
  }

  // 【待启用】
  @Column(name = "STR_DEVC_VAR06", length = 400)
  private String strDevcVar06;

  @JsonProperty("strDevcVar06")
  public String strDevcVar06() {
    return this.strDevcVar06;
  }

  public void strDevcVar06(String strDevcVar06) {
    this.strDevcVar06 = strDevcVar06;
  }

  // 【待启用】
  @Column(name = "STR_DEVC_VAR07", length = 400)
  private String strDevcVar07;

  @JsonProperty("strDevcVar07")
  public String strDevcVar07() {
    return this.strDevcVar07;
  }

  public void strDevcVar07(String strDevcVar07) {
    this.strDevcVar07 = strDevcVar07;
  }

  // 【待启用】
  @Column(name = "STR_DEVC_VAR08", length = 400)
  private String strDevcVar08;

  @JsonProperty("strDevcVar08")
  public String strDevcVar08() {
    return this.strDevcVar08;
  }

  public void strDevcVar08(String strDevcVar08) {
    this.strDevcVar08 = strDevcVar08;
  }
}
