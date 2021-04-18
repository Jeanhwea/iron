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
 * 扩展表的元类，MES 系统中的扩展表需要和主表的 ID 值保持一致
 *
 * @author Jinghui Hu
 * @since 2019-06-20, JDK1.8
 */
@MappedSuperclass
public abstract class MesSubEntity {

  @Id
  @Column(name = "MYID", unique = true)
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
    MesSubEntity that = (MesSubEntity) o;
    return id == that.id
        && Objects.equals(validation, that.validation)
        && Objects.equals(updateTime, that.updateTime)
        && Objects.equals(updateUser, that.updateUser);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, validation, updateTime, updateUser);
  }
}
