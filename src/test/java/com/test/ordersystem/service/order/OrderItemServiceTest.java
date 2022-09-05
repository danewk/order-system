package com.test.ordersystem.service.order;

import static org.junit.jupiter.api.Assertions.*;

import com.test.ordersystem.domain.OrderStatus;
import com.test.ordersystem.domain.entity.MenuEntity;
import com.test.ordersystem.domain.entity.OrderEntity;
import com.test.ordersystem.domain.entity.OrderItemEntity;
import com.test.ordersystem.domain.repository.MenuRepository;
import com.test.ordersystem.domain.repository.OrderRepository;
import com.test.ordersystem.service.menu.MenuService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OrderItemServiceTest {

  @Autowired
  OrderItemService orderItemService;

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  MenuRepository menuRepository;

  @Test
  @DisplayName("생성 후 조회 테스트")
  @Transactional
  void create(){

    //MenuEntity 생성 후 저장
    MenuEntity testMenu = MenuEntity.builder()
        .name("테스트음료")
        .price(BigDecimal.valueOf(4000L))
        .build();

    MenuEntity savedMenu = menuRepository.save(testMenu);

    //OrderEntity 생성 후 저장
    OrderEntity orderEntity = OrderEntity.builder()
        .orderDate(LocalDateTime.now())
        .userId(1L)
        .status(OrderStatus.READY)
        .build();

    OrderEntity savedOrder = orderRepository.save(orderEntity);

    orderItemService.create(savedMenu, savedOrder);

    OrderItemEntity orderItem = orderItemService.findOrderItem(savedOrder.getId());

    assertEquals(orderItem.getOrderId(), savedOrder.getId());


  }

}