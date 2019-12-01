package com.jumio.bookstore.data.dtos;

import com.jumio.bookstore.enums.*;
import lombok.*;
import lombok.experimental.*;

import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
public class OrderDto extends BaseDto<OrderDto> {
  private Set<OrderItemDto> orderItems = new HashSet<>();
  private String address;
  private Boolean paid;
  private OrderState orderState;
}