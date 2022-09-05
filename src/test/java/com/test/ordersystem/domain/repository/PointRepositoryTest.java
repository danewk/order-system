package com.test.ordersystem.domain.repository;

import com.test.ordersystem.domain.TransactionType;
import com.test.ordersystem.domain.entity.PointEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PointRepositoryTest extends BaseRepositoryTest {

  @Autowired
  PointRepository pointRepository;

  private List<PointEntity> getEntityList() {

    PointEntity pointEntity1 = new PointEntity(BigDecimal.valueOf(10000L), LocalDateTime.now(), null, TransactionType.DEPOSIT, 1L);
    PointEntity pointEntity2 = new PointEntity(BigDecimal.valueOf(20000L), LocalDateTime.now(), null, TransactionType.DEPOSIT, 1L);
    PointEntity pointEntity3 = new PointEntity(BigDecimal.valueOf(30000L), LocalDateTime.now(), null, TransactionType.DEPOSIT, 1L);
    PointEntity pointEntity4 = new PointEntity(BigDecimal.valueOf(40000L), LocalDateTime.now(), null, TransactionType.DEPOSIT, 1L);
    PointEntity pointEntity5 = new PointEntity(BigDecimal.valueOf(10000L), LocalDateTime.now(), null, TransactionType.DEPOSIT, 1L);
    PointEntity pointEntity6 = new PointEntity(BigDecimal.valueOf(50000L), null, LocalDateTime.now(), TransactionType.WITHDRAWAL, 1L);

    return List.of(pointEntity1, pointEntity2, pointEntity3, pointEntity4, pointEntity5, pointEntity6);

  }

  private PointEntity getEntity() {
    return new PointEntity(BigDecimal.valueOf(10000L), LocalDateTime.now(), null, TransactionType.DEPOSIT, 1L);

  }


  @Test
  @DisplayName("저장 테스트")
  public void saveTest() {

    List<PointEntity> pointEntityList = getEntityList();

    List<PointEntity> savedPoints = pointRepository.saveAllAndFlush(pointEntityList);

    Assertions.assertThat(savedPoints.size())
        .isEqualTo(6);
    Assertions.assertThat(savedPoints.get(5)
            .getPoint())
        .isEqualTo(BigDecimal.valueOf(50000L));

  }

  @Test
  @DisplayName("삭제 테스트")
  public void deleteTest() {

    PointEntity entity = getEntity();

    PointEntity save = pointRepository.save(entity);

    save.delete();

    Assertions.assertThat(save.getDeletedAt()).isNotNull();

  }


}