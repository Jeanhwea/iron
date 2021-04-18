package com.avic.mti.iron.common.domain.entity;

import com.avic.mti.iron.common.helper.NameCodeHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * MES 表的基础元类，用来处理如下的四个公共字段
 * <li>1. id（主键）
 * <li>2. validation（是否有效）
 * <li>3. updateTime（更新时间）
 * <li>4. updateUser（更新用户）
 *
 * @author Jinghui Hu
 * @since 2019-05-10, JDK1.8
 */
@MappedSuperclass
public abstract class MesEntity {

  @Id
  @Column(name = "MYID", unique = true)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYSTEM_ID")
  @SequenceGenerator(sequenceName = "SYS_ID", allocationSize = 1, name = "SYSTEM_ID")
  private long id; // 系统 ID

  @JsonProperty("id")
  public long id() {
    return this.id;
  }

  public void id(long id) {
    this.id = id;
  }

  @Column(name = "VALIDATION", nullable = false, length = 1)
  private String validation; // 该项数据是否合法，'y'表示合法，'n'表示不合法。

  public boolean validation() {
    return "y".equals(this.validation);
  }

  public void validation(boolean validation) {
    this.validation = validation ? "y" : "n";
  }

  @UpdateTimestamp
  @JsonIgnore
  @Column(name = "DATETIME", nullable = false)
  private Timestamp updateTime; // 最后更新的时间，对应于 Oracle 数据库中的 Date 类型。

  public Timestamp updateTime() {
    return updateTime;
  }

  public void updateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  @Column(name = "SIGNED_CODE", nullable = false, length = 64)
  private String updateUser; // 最后更新的用户名称，是一个字符串。

  public String updateUser() {
    return this.updateUser;
  }

  public void updateUser(String updateUser) {
    this.updateUser = updateUser;
  }

  public void updateUser(String name, String code) {
    this.updateUser = NameCodeHelper.consNameCode(name, code);
  }

  @PreUpdate
  @PrePersist
  public void mesEntityCreateAndUpdateHook() {
    if (this.validation == null) {
      this.validation(true);
    }

    this.updateTime(new Timestamp(System.currentTimeMillis()));
  }

  @Override
  public String toString() {
    String name =
        Optional.of(getClass().getName())
            .map(n -> n.replaceAll("^.*\\.", ""))
            .orElse("NoClassName");
    return name + "[" + this.id + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MesEntity mesEntity = (MesEntity) o;
    return id == mesEntity.id
        && Objects.equals(validation, mesEntity.validation)
        && Objects.equals(updateTime, mesEntity.updateTime)
        && Objects.equals(updateUser, mesEntity.updateUser);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, validation, updateTime, updateUser);
  }
}
