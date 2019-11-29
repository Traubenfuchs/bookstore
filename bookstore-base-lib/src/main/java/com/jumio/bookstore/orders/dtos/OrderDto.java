package com.jumio.bookstore.orders.dtos;

import lombok.*;
import lombok.experimental.*;

import java.util.*;

@Data
@Accessors(chain = true)
public class OrderDto {
  private Set<OrderItemDto> orderItems = new HashSet<>();
}
