package com.test.ordersystem.service.order;

import com.test.ordersystem.domain.entity.MenuEntity;
import com.test.ordersystem.domain.entity.OrderEntity;
import com.test.ordersystem.domain.entity.OrderItemEntity;
import com.test.ordersystem.domain.repository.OrderItemRepository;
import com.test.ordersystem.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

  private final OrderItemRepository orderItemRepository;

  @Transactional
  public void create(MenuEntity menuEntity, OrderEntity savedOrder) {
    //주문아이템 엔티티 생성
    OrderItemEntity orderItem = createOrderItem(savedOrder, menuEntity);

    //주문 아이템 저장
    orderItemRepository.save(orderItem);
  }

  private OrderItemEntity createOrderItem(OrderEntity orderEntity, MenuEntity menuEntity) {
    return OrderItemEntity.builder()
        .orderId(orderEntity.getId())
        .orderPrice(menuEntity.getPrice())
        .menuId(menuEntity.getId())
        .build();
  }

  public OrderItemEntity findOrderItem(Long orderId) {
    return orderItemRepository.findByOrderIdAndDeletedAtIsNull(orderId)
        .orElseThrow(() -> new IllegalStateException(ErrorMessage.NOT_EXIST_ORDER.getMessage()));
  }


}
