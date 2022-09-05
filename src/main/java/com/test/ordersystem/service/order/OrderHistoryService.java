package com.test.ordersystem.service.order;

import com.test.ordersystem.domain.entity.OrderHistoryEntity;
import com.test.ordersystem.domain.repository.OrderHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

  private final OrderHistoryRepository orderHistoryRepository;

  @Transactional
  public void addOrderHistory(Long menuId, Long userId) {

    OrderHistoryEntity newOrderHistory = OrderHistoryEntity.builder()
        .menuId(menuId)
        .userId(userId)
        .count(1L)
        .build();

    orderHistoryRepository.save(newOrderHistory);
  }

}
