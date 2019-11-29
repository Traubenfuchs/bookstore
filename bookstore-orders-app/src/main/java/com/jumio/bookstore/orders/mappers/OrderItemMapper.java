package com.jumio.bookstore.orders.mappers;

import com.jumio.bookstore.orders.dtos.*;
import com.jumio.bookstore.orders.entities.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
  OrderItemDto entityToDto(OrderItemEntity source);

  OrderItemEntity dtoToEntity(OrderItemDto destination);
}
