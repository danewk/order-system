package com.test.ordersystem.service.point;

import com.test.ordersystem.domain.TransactionType;
import com.test.ordersystem.domain.entity.PointEntity;
import com.test.ordersystem.domain.repository.PointRepository;
import com.test.ordersystem.dto.point.PointChargeRequest;
import com.test.ordersystem.dto.point.PointChargeResponse;
import com.test.ordersystem.dto.point.PointPayResponse;
import com.test.ordersystem.dto.point.PointRetrieveResponse;
import com.test.ordersystem.exception.ErrorMessage;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {

  private final PointRepository pointRepository;

  @Transactional
  public PointChargeResponse charge(PointChargeRequest request) {
    PointEntity newPoint = PointEntity.builder()
        .point(request.getPointAmount())
        .depositDate(LocalDateTime.now())
        .transactionType(TransactionType.DEPOSIT)
        .userId(request.getUserId())
        .build();

    final PointEntity savedPointEntity = pointRepository.save(newPoint);

    final BigDecimal pointAmount = getPointAmount(request.getUserId());

    return PointChargeResponse.builder()
        .userId(savedPointEntity.getUserId())
        .pointAmount(pointAmount)
        .chargeAmount(savedPointEntity.getPoint())
        .build();
  }

  @Transactional
  public PointPayResponse pay(Long userId, BigDecimal totalPrice){
    PointEntity newPoint = PointEntity.builder()
        .point(totalPrice)
        .depositDate(LocalDateTime.now())
        .transactionType(TransactionType.WITHDRAWAL)
        .userId(userId)
        .build();

    pointRepository.save(newPoint);

    final BigDecimal pointAmount = getPointAmount(userId);

    return PointPayResponse.builder()
        .pointAmount(pointAmount)
        .userId(userId)
        .orderedPrice(totalPrice)
        .build();
  }

  @Transactional(readOnly = true)
  public PointRetrieveResponse retrievePoint(Long userId){

    final BigDecimal pointAmount = getPointAmount(userId);

    return PointRetrieveResponse.builder()
        .pointAmount(pointAmount)
        .userId(userId)
        .build();
  }

  private BigDecimal getPointAmount(Long userId) {
    List<PointEntity> points = pointRepository.findAllByUserIdAndDeletedAtIsNull(userId);

    final BigDecimal depositPoint = points.stream()
        .filter(pointEntity -> TransactionType.DEPOSIT.equals(pointEntity.getTransactionType()))
        .map(PointEntity::getPoint)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    final BigDecimal withDrawPoint = points.stream()
        .filter(pointEntity -> TransactionType.WITHDRAWAL.equals(pointEntity.getTransactionType()))
        .map(PointEntity::getPoint)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (depositPoint.compareTo(withDrawPoint) < 0) {
      throw new IllegalStateException(ErrorMessage.POINT_ERROR.getMessage());
    }

    return depositPoint.subtract(withDrawPoint);
  }

}
