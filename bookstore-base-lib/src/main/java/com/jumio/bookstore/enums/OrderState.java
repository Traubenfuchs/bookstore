package com.jumio.bookstore.enums;

/**
 * Orders can be CREATED, waiting to be shipped, SHIPPED or CANCELLED.
 * SHIPPED orders can not be CANCELLED anymore.
 */
public enum OrderState {
  CREATED,
  SHIPPED,
  CANCELLED
}