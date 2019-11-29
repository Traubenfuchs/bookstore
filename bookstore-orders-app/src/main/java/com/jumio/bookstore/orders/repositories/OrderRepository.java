package com.jumio.bookstore.orders.repositories;

import com.jumio.bookstore.orders.entities.*;
import org.springframework.data.jpa.repository.*;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
}