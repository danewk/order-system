package com.test.ordersystem.service.pay;

import static org.junit.jupiter.api.Assertions.*;

import com.test.ordersystem.domain.OrderStatus;
import com.test.ordersystem.domain.entity.MenuEntity;
import com.test.ordersystem.domain.entity.OrderEntity;
import com.test.ordersystem.domain.repository.MenuRepository;
import com.test.ordersystem.domain.repository.OrderRepository;
import com.test.ordersystem.dto.order.OrderCreateRequest;
import com.test.ordersystem.dto.point.PointChargeRequest;
import com.test.ordersystem.dto.point.PointPayResponse;
import com.test.ordersystem.service.order.OrderItemService;
import com.test.ordersystem.service.order.OrderService;
import com.test.ordersystem.service.point.PointService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PayServiceTest {

  @Autowired
  PayService payService;
  @Autowired
  OrderItemService orderItemService;
  @Autowired
  MenuRepository menuRepository;
  @Autowired
  OrderRepository orderRepository;
  @Autowired
  PointService pointService;

  @Test
  @DisplayName("주문힘 -> 포인트가 0일 경우 에러 발생")
  @Transactional
  void pay(){

    //MenuEntity 생성 후 저장
    MenuEntity testMenu = MenuEntity.builder()
        .name("테스트음료")
        .price(BigDecimal.valueOf(50000L))
        .build();

    MenuEntity savedMenu = menuRepository.save(testMenu);

    //OrderEntity 생성 후 저장
    OrderEntity orderEntity = OrderEntity.builder()
        .orderDate(LocalDateTime.now())
        .userId(1L)
        .status(OrderStatus.READY)
        .build();

    OrderEntity savedOrder = orderRepository.save(orderEntity);

    //orderItem 생성
    orderItemService.create(savedMenu, orderEntity);

    //포인트를 생성하지 않아 내 포인트는 0
    assertThrows(IllegalStateException.class, () -> {
      payService.pay(orderEntity.getId(), orderEntity.getUserId());
    });
  }

  @Test
  @DisplayName("주문힘 -> 포인트가 있지만 부족할 경우 에러 발생")
  @Transactional
  void pay2(){

    //MenuEntity 생성 후 저장
    MenuEntity testMenu = MenuEntity.builder()
        .name("테스트음료")
        .price(BigDecimal.valueOf(50000L))
        .build();

    MenuEntity savedMenu = menuRepository.save(testMenu);

    //OrderEntity 생성 후 저장
    OrderEntity orderEntity = OrderEntity.builder()
        .orderDate(LocalDateTime.now())
        .userId(1L)
        .status(OrderStatus.READY)
        .build();

    OrderEntity savedOrder = orderRepository.save(orderEntity);

    //orderItem 생성
    orderItemService.create(savedMenu, orderEntity);

    //포인트를 생성
    pointService.charge(new PointChargeRequest(1L, BigDecimal.valueOf(10000L)));

    assertThrows(IllegalStateException.class, () -> {
      payService.pay(orderEntity.getId(), orderEntity.getUserId());
    });
  }

  @Test
  @DisplayName("주문 -> 포인트로 결제 후 잔액이 일치함")
  @Transactional
  void pay3(){

    //MenuEntity 생성 후 저장
    MenuEntity testMenu = MenuEntity.builder()
        .name("테스트음료")
        .price(BigDecimal.valueOf(50000L))
        .build();

    MenuEntity savedMenu = menuRepository.save(testMenu);

    //OrderEntity 생성 후 저장
    OrderEntity orderEntity = OrderEntity.builder()
        .orderDate(LocalDateTime.now())
        .userId(1L)
        .status(OrderStatus.READY)
        .build();

    OrderEntity savedOrder = orderRepository.save(orderEntity);

    //orderItem 생성
    orderItemService.create(savedMenu, orderEntity);

    //포인트를 생성
    pointService.charge(new PointChargeRequest(1L, BigDecimal.valueOf(60000L)));

    PointPayResponse pay = payService.pay(savedOrder.getId(), savedOrder.getUserId());

    assertEquals(pay.getOrderedPrice(), BigDecimal.valueOf(50000L));
    assertEquals(pay.getPointAmount(), BigDecimal.valueOf(10000L));

  }

}