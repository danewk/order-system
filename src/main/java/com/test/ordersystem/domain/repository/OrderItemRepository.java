package com.test.ordersystem.domain.repository;

import com.test.ordersystem.domain.entity.OrderItemEntity;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

  Optional<OrderItemEntity> findByOrderIdAndDeletedAtIsNull(@NonNull Long orderId);
}
