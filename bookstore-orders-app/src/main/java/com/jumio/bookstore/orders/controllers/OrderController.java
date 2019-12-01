package com.jumio.bookstore.orders.controllers;

import com.jumio.bookstore.data.dtos.*;
import com.jumio.bookstore.data.requests.*;
import com.jumio.bookstore.orders.services.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

/**
 * Offers RESTful CRUD operations for the Order resource.
 */
@RequestMapping("api")
@RestController
@Slf4j
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("order")
  public OrderDto post(@Valid @RequestBody OrderRequest orderRequest) {
    log.info("OrderController.post called.");
    return orderService.create(orderRequest);
  }

  @PutMapping("order/{orderUuid}")
  public OrderDto put(
    @PathVariable String orderUuid,
    @Valid @RequestBody OrderRequest orderRequest
  ) {
    log.info("OrderController.put called.");
    return orderService.update(orderUuid, orderRequest);
  }

  @GetMapping("order/{orderUuid}")
  public OrderDto getOrder(@PathVariable String orderUuid) {
    log.info("OrderController.orderUuid called.");
    return orderService.getByUuid(orderUuid);
  }

  @GetMapping("orders")
  public Set<OrderDto> getMyOrders() {
    log.info("OrderController.getMyOrders called.");
    return orderService.getMyOrders();
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @DeleteMapping("order/{uuid}")
  public void delete(@PathVariable String uuid) {
    log.info("OrderController.delete called.");
    orderService.delete(uuid);
  }
}
