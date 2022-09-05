package com.test.ordersystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.test.ordersystem.domain.TransactionType;
import com.test.ordersystem.domain.entity.PointEntity;
import com.test.ordersystem.domain.repository.PointRepository;
import com.test.ordersystem.dto.point.PointRetrieveResponse;
import com.test.ordersystem.service.point.PointService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PointServiceTest {

  @Autowired
  PointService pointService;

  @Autowired
  PointRepository pointRepository;

  @BeforeEach
  void 메뉴_세팅() {

    PointEntity pointEntity1 = new PointEntity(BigDecimal.valueOf(10000L), LocalDateTime.now(), null, TransactionType.DEPOSIT, 1L);
    PointEntity pointEntity2 = new PointEntity(BigDecimal.valueOf(20000L), LocalDateTime.now(), null, TransactionType.DEPOSIT, 1L);
    PointEntity pointEntity3 = new PointEntity(BigDecimal.valueOf(30000L), LocalDateTime.now(), null, TransactionType.DEPOSIT, 1L);
    PointEntity pointEntity4 = new PointEntity(BigDecimal.valueOf(40000L), LocalDateTime.now(), null, TransactionType.DEPOSIT, 1L);
    PointEntity pointEntity5 = new PointEntity(BigDecimal.valueOf(10000L), LocalDateTime.now(), null, TransactionType.DEPOSIT, 1L);
    PointEntity pointEntity6 = new PointEntity(BigDecimal.valueOf(50000L), null, LocalDateTime.now(), TransactionType.WITHDRAWAL, 1L);

    pointRepository.saveAll(
        List.of(pointEntity1, pointEntity2, pointEntity3, pointEntity4, pointEntity5, pointEntity6)
    );

  }

  @AfterEach
  public void delete() {
    pointRepository.deleteAll();
  }


  @Test
  void 포인트잔액조회_테스트(){
    PointRetrieveResponse pointChargeResponse = pointService.retrievePoint(1L);

    assertEquals(BigDecimal.valueOf(60000L),pointChargeResponse.getPointAmount());
  }

  @Test
  void 포인트잔액조회_테스트_출금내역존재(){
    PointRetrieveResponse pointChargeResponse = pointService.retrievePoint(1L);

    assertEquals(BigDecimal.valueOf(60000L),pointChargeResponse.getPointAmount());
  }

}