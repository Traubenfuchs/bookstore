package com.jumio.bookstore.orders.dtos;

import lombok.*;

import java.time.*;

@Getter
public abstract class BaseDto<T extends BaseDto<T>> {
  private String uuid;
  private Instant dtUpdate;
  private Instant dtCreation;

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
  public String toString() {
    return String.format("Dto of type<%s>, with uuid<%s>", getClass().getName(), getUuid());
  }
}
