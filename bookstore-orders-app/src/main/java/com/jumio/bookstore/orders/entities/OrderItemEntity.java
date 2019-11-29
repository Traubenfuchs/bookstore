package com.jumio.bookstore.orders.entities;

import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;

@Setter
@Getter
@Accessors(chain = true)
@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItemEntity extends BaseEntity<OrderItemEntity> {
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_ORDER_UUID", nullable = false)
  private OrderEntity order;

  @Column(name = "QUANTITY", nullable = false)
  private Integer quantity;
}
