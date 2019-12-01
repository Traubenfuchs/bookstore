package com.jumio.bookstore.data.dtos;

import lombok.*;

import java.time.*;

@EqualsAndHashCode
@Getter
public abstract class BaseDto<T extends BaseDto<T>> {
  private String uuid;
  private Instant dtUpdate;
  private Instant dtCreation;

  public T setUuid(String uuid) {
    this.uuid = uuid;
    return (T) this;
  }

  public T setDtCreation(Instant dtCreation) {
    this.dtCreation = dtCreation;
    return (T) this;
  }

  public T setDtUpdate(Instant dtUpdate) {
    this.dtUpdate = dtUpdate;
    return (T) this;
  }
}