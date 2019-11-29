package com.jumio.bookstore.orders.entities;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import java.time.*;

@Getter
@MappedSuperclass
public abstract class BaseEntity<T extends BaseEntity<T>> {
  @Column(name = "UUID", length = 36, nullable = false)
  @GenericGenerator(
    name = "UUID_GENERATOR",
    strategy = "com.jumio.bookstore.orders.entities.UuidGenerator")
  @GeneratedValue(generator = "UUID_GENERATOR")
  @Id
  private String uuid;
  @Column(name = "DT_CREATION", nullable = false)
  private Instant dtCreation;
  @Column(name = "DT_UPDATE", nullable = false)
  private Instant dtUpdate;

  public T setUuid(String uuid) {
    this.uuid = uuid;
    return (T) this;
  }

  public T setTsCreation(Instant dtCreation) {
    this.dtCreation = dtCreation;
    return (T) this;
  }

  public T setTsUpdate(Instant dtUpdate) {
    this.dtUpdate = dtUpdate;
    return (T) this;
  }

  @Override
  public final boolean equals(Object obj) {
    return this == obj;
  }

  @Override
  public final int hashCode() {
    if (uuid == null) {
      return 0;
    }
    return uuid.hashCode();
  }

  @PrePersist
  public void prePersistDtCreationDtUpdate() {
    Instant now = Instant.now();
    this.dtCreation = now;
    this.dtUpdate = now;
  }

  @PreUpdate
  public void preUpdateDtUpdate() {
    this.dtUpdate = Instant.now();
  }

  @Override
  public String toString() {
    return String.format("Entity of type <%s> with uuid<%s>", getClass().getName(), getUuid());
  }
}