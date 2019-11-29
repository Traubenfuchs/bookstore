package com.jumio.bookstore.orders.repositories;

import com.jumio.bookstore.orders.entities.*;
import org.springframework.data.jpa.repository.*;

public interface OrderItemRepository extends JpaRepository<OrderEntity, String> {
}
