package com.jumio.bookstore.data.dtos;

import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@Accessors(chain = true)
public class OrderItemDto extends BaseDto<OrderItemDto> {
  private String bookUuid;
  private Integer quantity;
}