package com.test.ordersystem.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.test.ordersystem.domain.entity.OrderHistoryEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class OrderHistoryRepositoryTest extends BaseRepositoryTest {

  @Autowired
  OrderHistoryRepository orderHistoryRepository;

  @Test
  @DisplayName("저장 테스트")
  public void saveTest() {

    OrderHistoryEntity orderEntity = getEntity();

    OrderHistoryEntity savedEntity = orderHistoryRepository.save(orderEntity);

    Assertions.assertThat(orderEntity.getUserId())
        .isEqualTo(savedEntity.getUserId());
    Assertions.assertThat(orderEntity.getMenuId())
        .isEqualTo(savedEntity.getMenuId());
  }

  private OrderHistoryEntity getEntity() {
    return OrderHistoryEntity.builder()
        .userId(1L)
        .menuId(1L)
        .count(1L)
        .build();
  }


}