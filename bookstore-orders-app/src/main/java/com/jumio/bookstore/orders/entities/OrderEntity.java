package com.jumio.bookstore.orders.entities;

import com.jumio.bookstore.entities.*;
import com.jumio.bookstore.enums.*;
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
  @Column(name = "USER_ID", nullable = false)
  private String userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "ORDER_STATE", nullable = false)
  private OrderState orderState;

  @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<OrderItemEntity> orderItems = new HashSet<>();

  public OrderEntity replaceOrderItems(Set<OrderItemEntity> newOrderItems) {
    this.orderItems.clear();
    this.orderItems.addAll(newOrderItems);
    this.orderItems.forEach(orderItem -> orderItem.setOrder(this));
    return this;
  }

  @Column(name = "PAID", nullable = false)
  private Boolean paid = false;

  @Column(name = "ADDRESS", nullable = false)
  private String address;

  @PreUpdate
  @PrePersist
  public void orderItemParentSetter() {
    orderItems.forEach(orderItemEntity -> orderItemEntity.setOrder(this));
  }
}
