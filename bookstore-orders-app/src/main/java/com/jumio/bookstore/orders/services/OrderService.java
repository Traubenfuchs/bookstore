package com.jumio.bookstore.orders.services;

import com.jumio.bookstore.orders.dtos.*;
import com.jumio.bookstore.orders.mappers.*;
import com.jumio.bookstore.orders.repositories.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  public OrderService(
    OrderRepository orderRepository,
    OrderMapper orderMapper
  ) {
    this.orderRepository = orderRepository;
    this.orderMapper = orderMapper;
  }

  public OrderDto create(OrderDto orderDto) {
    return null;
  }

  public OrderDto update(OrderDto orderDto) {
    return null;
  }

  public OrderDto getOrder(String uuid) {
    return orderMapper.entityToDto(orderRepository.getOne(uuid));
  }

  public Set<OrderDto> getMyOrders() {
    return null;
  }

  public void deleteOrder(String uuid) {
    return;
  }
}
