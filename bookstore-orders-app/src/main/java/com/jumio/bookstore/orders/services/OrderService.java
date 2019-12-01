package com.jumio.bookstore.orders.services;

import com.jumio.bookstore.data.dtos.*;
import com.jumio.bookstore.data.requests.*;
import com.jumio.bookstore.enums.*;
import com.jumio.bookstore.orders.entities.*;
import com.jumio.bookstore.orders.mappers.*;
import com.jumio.bookstore.orders.repositories.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.server.*;

import java.util.*;
import java.util.stream.*;

@Transactional
@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final BookVerifier bookVerifier;

  public OrderService(
    OrderRepository orderRepository,
    OrderMapper orderMapper,
    BookVerifier bookVerifier
  ) {
    this.orderRepository = orderRepository;
    this.orderMapper = orderMapper;
    this.bookVerifier = bookVerifier;
  }

  public OrderDto create(OrderRequest orderRequest) {
    return orderMapper.entityToDto(
      orderRepository
        .save(new OrderEntity()
          .setPaid(false)
          .setUserId(getUserId())
          .setOrderState(OrderState.CREATED)
          .setAddress(orderRequest.getAddress())
          .setOrderItems(orderDtosToOrderEntities(orderRequest.getOrderItemRequests()))
        )
    );
  }

  public OrderDto getByUuid(String uuid) {
    OrderEntity orderEntity = orderRepository.getByUuid(uuid);

    throwIfMissingOrForbidden(orderEntity);

    return orderMapper.entityToDto(orderEntity);
  }

  public Set<OrderDto> getMyOrders() {
    return orderMapper.entitiesToDtos(orderRepository.getByUserId(getUserId()));
  }

  /**
   * Sets orderState of the Order resource to CANCELLED.
   * Throws if order state is already CANCELLED.
   * Throws if order state is SHIPPED: Shipped orders can not be cancelled anymore.
   */
  public void delete(String orderUuid) {
    OrderEntity orderEntity = orderRepository.getByUuid(orderUuid);
    throwIfMissingOrForbidden(orderEntity);

    if (orderEntity.getOrderState() == OrderState.CREATED) {
      orderEntity.setOrderState(OrderState.CANCELLED);
      return;
    }

    if (orderEntity.getOrderState() == OrderState.CANCELLED) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is already CANCELLED!");
    }

    if (orderEntity.getOrderState() == OrderState.SHIPPED) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is already SHIPPED!");
    }
  }

  /**
   * Updates an existing order. Always replaced all OrderItem entries.
   */
  public OrderDto update(String orderUuid, OrderRequest orderRequest) {
    OrderEntity orderEntity = orderRepository.getByUuid(orderUuid);

    throwIfMissingOrForbidden(orderEntity);

    if (orderEntity.getOrderState() != OrderState.CREATED) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can not update an order with state <" + orderEntity.getOrderState() + ">.");
    }

    orderEntity.getOrderItems().clear();

    orderEntity
      .setAddress(orderRequest.getAddress())
      .replaceOrderItems(orderDtosToOrderEntities(orderRequest.getOrderItemRequests()))
    ;

    return orderMapper.entityToDto(orderEntity);
  }

  /**
   * Throws a ResponseStatusException, if the given orderEntity is null.
   */
  private void throwIfMissing(OrderEntity orderEntity) {
    if (orderEntity == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with given uuid, or rights missing no delete it.");
    }
  }

  /**
   * Throws a ResponseStatusException, if the given orderEntity is null or does not belong to the calling user.
   */
  private void throwIfMissingOrForbidden(OrderEntity orderEntity) {
    throwIfMissing(orderEntity);
    if (!Objects.equals(orderEntity.getUserId(), getUserId())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found with given uuid, or rights missing no delete it.");
    }
  }

  /**
   * Converts the given OrderDto values to entities.
   */
  private Set<OrderItemEntity> orderDtosToOrderEntities(Collection<OrderItemRequest> orderDtos) {
    bookVerifier.verifyOrThrow(orderDtos);

    return orderDtos
      .stream()
      .map(orderItemCreationRequest -> new OrderItemEntity()
        .setQuantity(orderItemCreationRequest.getQuantity())
        .setBookUuid(orderItemCreationRequest.getBookUuid())
      )
      .collect(Collectors.toSet());
  }

  /**
   * Returns the id auf the authenticated user.
   */
  private String getUserId() {
    Authentication a = SecurityContextHolder.getContext().getAuthentication();
    return a.getName();
  }
}
