package com.jumio.bookstore.orders.repositories;

import com.jumio.bookstore.orders.entities.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
  OrderEntity getByUuid(String uuid);

  Set<OrderEntity> getByUserId(String userId);
}