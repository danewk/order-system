package com.test.ordersystem.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.test.ordersystem.domain.OrderStatus;
import com.test.ordersystem.domain.TransactionType;
import com.test.ordersystem.domain.entity.OrderEntity;
import com.test.ordersystem.domain.entity.PointEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class OrderRepositoryTest extends BaseRepositoryTest{

  @Autowired
  OrderRepository orderRepository;

  @Test
  @DisplayName("저장 테스트")
  public void saveTest() {

    OrderEntity orderEntity = getEntity();

    OrderEntity savedEntity = orderRepository.save(orderEntity);

    Assertions.assertThat(orderEntity.getUserId())
        .isEqualTo(savedEntity.getUserId());
  }

  private OrderEntity getEntity() {
    return new OrderEntity(1L, LocalDateTime.now(), OrderStatus.READY);
  }

  @Test
  @DisplayName("삭제 테스트")
  public void deleteTest() {

    OrderEntity orderEntity = getEntity();

    OrderEntity savedEntity = orderRepository.save(orderEntity);

    savedEntity.delete();

    Assertions.assertThat(savedEntity.getDeletedAt()).isNotNull();

  }




}