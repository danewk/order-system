package com.test.ordersystem.domain.repository;

import com.test.ordersystem.domain.entity.OrderHistoryEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderHistoryRepository extends JpaRepository<OrderHistoryEntity, Long> {

  @Query("select o from OrderHistoryEntity o where o.deletedAt is null and o.createdAt >= :baseTime")
  List<OrderHistoryEntity> findAllWithBaseTime(LocalDateTime baseTime);
}
