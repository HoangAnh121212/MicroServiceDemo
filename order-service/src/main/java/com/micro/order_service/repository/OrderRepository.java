package com.micro.order_service.repository;

import com.micro.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
