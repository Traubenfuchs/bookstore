package com.jumio.bookstore.orders.controllers;

import com.jumio.bookstore.orders.services.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderItemController {
  private final OrderService orderService;

  public OrderItemController(OrderService orderService) {
    this.orderService = orderService;
  }
}
