package com.jumio.bookstore.orders.mappers;

import com.jumio.bookstore.orders.dtos.*;
import com.jumio.bookstore.orders.entities.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderMapper {
  OrderDto entityToDto(OrderEntity source);

  OrderEntity dtoToEntity(OrderDto destination);
}
