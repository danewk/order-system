package com.test.ordersystem.service.order;

import static org.junit.jupiter.api.Assertions.*;

import com.test.ordersystem.domain.entity.OrderHistoryEntity;
import com.test.ordersystem.domain.repository.OrderHistoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OrderHistoryServiceTest {

  @Autowired
  OrderHistoryService orderHistoryService;

  @Autowired
  OrderHistoryRepository orderHistoryRepository;

  @Test
  @DisplayName("생성 후 조회 테스트")
  @Transactional
  void create(){
    //history 데이터 저장
    LongStream.range(0,5).forEach(i -> orderHistoryService.addOrderHistory(i, i));

    //1일전 데이터 조회
    List<OrderHistoryEntity> orderHistoryEntityList = orderHistoryRepository.findAllWithBaseTime(LocalDateTime.now()
        .minusDays(1));

    //5개임을 보장
    assertEquals(orderHistoryEntityList.size(),5);

  }


}