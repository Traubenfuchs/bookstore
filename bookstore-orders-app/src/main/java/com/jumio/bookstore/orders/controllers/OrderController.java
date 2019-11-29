package com.jumio.bookstore.orders.controllers;

import com.jumio.bookstore.orders.dtos.*;
import com.jumio.bookstore.orders.services.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("api")
@RestController
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("order")
  public OrderDto post(@RequestBody OrderDto orderDto) {
    return orderService.create(orderDto);
  }

  @PutMapping("order")
  public OrderDto put(@RequestBody OrderDto orderDto) {
    return orderService.update(orderDto);
  }

  @GetMapping("order/{uuid}")
  public OrderDto getOrder(@PathVariable String uuid) {
    return orderService.getOrder(uuid);
  }

  @GetMapping("orders")
  public Set<OrderDto> getMyOrders() {
    return orderService.getMyOrders();
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @DeleteMapping("order/{uuid}")
  public void delete(@PathVariable String uuid) {
    orderService.deleteOrder(uuid);
  }
}
