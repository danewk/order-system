package com.test.ordersystem.service.order;

import static org.junit.jupiter.api.Assertions.*;

import com.test.ordersystem.domain.OrderStatus;
import com.test.ordersystem.domain.TransactionType;
import com.test.ordersystem.domain.entity.MenuEntity;
import com.test.ordersystem.domain.entity.OrderHistoryEntity;
import com.test.ordersystem.domain.entity.PointEntity;
import com.test.ordersystem.domain.repository.MenuRepository;
import com.test.ordersystem.domain.repository.OrderHistoryRepository;
import com.test.ordersystem.domain.repository.PointRepository;
import com.test.ordersystem.dto.order.OrderCreateRequest;
import com.test.ordersystem.dto.order.OrderResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OrderServiceTest {

  @Autowired
  OrderService orderService;

  @Autowired
  MenuRepository menuRepository;

  @Autowired
  PointRepository pointRepository;

  @Autowired
  OrderHistoryRepository orderHistoryRepository;

  @Test
  @DisplayName("메뉴에 없는 것을 주문했을때 오류가 발생")
  @Transactional
  void errorTest() {
    MenuEntity menu = MenuEntity.builder()
        .name("테스트음료")
        .price(BigDecimal.valueOf(2300L))
        .build();

    menuRepository.saveAndFlush(menu);

    assertThrows(IllegalStateException.class, () -> {
      orderService.create(new OrderCreateRequest(1L, 4L));
    });
  }

  @Test
  @DisplayName("주문1건테스트")
  @Transactional
  void order(){

    dataSetting();

    OrderResponse orderResponse = orderService.create(new OrderCreateRequest(1L, 1L));
    List<OrderHistoryEntity> historyEntityList = orderHistoryRepository.findAllWithBaseTime(LocalDateTime.now()
        .minusDays(1));

    Long totalOrderCount = historyEntityList.stream()
        .map(OrderHistoryEntity::getCount)
        .reduce(0L, Long::sum);

    assertEquals(orderResponse.getOrderStatus(), OrderStatus.ORDER);
    assertEquals(historyEntityList.size(), 1);
    assertEquals(totalOrderCount, 1);

  }

  @Test
  @DisplayName("주문3건테스트")
  @Transactional
  void order2(){

    dataSetting();

    LongStream.range(1,4).forEach(i -> orderService.create(new OrderCreateRequest(i, 1L)));

    List<OrderHistoryEntity> historyEntityList = orderHistoryRepository.findAllWithBaseTime(LocalDateTime.now()
        .minusDays(1));

    Long totalOrderCount = historyEntityList.stream()
        .map(OrderHistoryEntity::getCount)
        .reduce(0L, Long::sum);

    assertEquals(historyEntityList.size(), 3);
    assertEquals(totalOrderCount, 3);

  }

  private void dataSetting() {
    //MenuEntity 생성 후 저장
    MenuEntity testMenu = MenuEntity.builder()
        .name("테스트음료")
        .price(BigDecimal.valueOf(10000L))
        .build();

    PointEntity testPoint = PointEntity.builder()
        .userId(1L)
        .transactionType(TransactionType.DEPOSIT)
        .depositDate(LocalDateTime.now()
            .minusDays(1))
        .point(BigDecimal.valueOf(100000L))
        .build();

    PointEntity testPoint2 = PointEntity.builder()
        .userId(2L)
        .transactionType(TransactionType.DEPOSIT)
        .depositDate(LocalDateTime.now()
            .minusDays(1))
        .point(BigDecimal.valueOf(100000L))
        .build();

    PointEntity testPoint3 = PointEntity.builder()
        .userId(3L)
        .transactionType(TransactionType.DEPOSIT)
        .depositDate(LocalDateTime.now()
            .minusDays(1))
        .point(BigDecimal.valueOf(100000L))
        .build();

    menuRepository.saveAndFlush(testMenu);
    pointRepository.saveAndFlush(testPoint); //user1
    pointRepository.saveAndFlush(testPoint2); //user2
    pointRepository.saveAndFlush(testPoint3); //user3

  }


}