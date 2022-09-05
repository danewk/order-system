package com.test.ordersystem.domain.repository;

import com.test.ordersystem.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
