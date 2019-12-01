package com.jumio.bookstore.orders.mappers;

import com.jumio.bookstore.data.dtos.*;
import com.jumio.bookstore.orders.entities.*;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
  OrderDto entityToDto(OrderEntity source);

  Set<OrderDto> entitiesToDtos(Collection<OrderEntity> orderEntities);
}