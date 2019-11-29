package com.jumio.bookstore.orders.entities;

import com.jumio.bookstore.orders.enums.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;
import java.util.*;

@Setter
@Getter
@Accessors(chain = true)
@Entity
@Table(name = "ORDERS")
public class OrderEntity extends BaseEntity<OrderEntity> {
  private String customerId;

  @Enumerated(EnumType.STRING)
  @Column(name = "ORDER_STATE", nullable = false)
  private OrderState orderState;

  @OneToMany(mappedBy = "order")
  private Set<OrderItemEntity> items = new HashSet<>();

  @Column(name = "PAID", nullable = false)
  private Boolean paid = false;
}
