package com.jumio.bookstore.orders.mappers;

import com.jumio.bookstore.data.dtos.*;
import com.jumio.bookstore.orders.entities.*;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
  OrderItemDto entityToDto(OrderItemEntity source);

  Set<OrderItemDto> entityToDto(Collection<OrderItemEntity> source);
}
