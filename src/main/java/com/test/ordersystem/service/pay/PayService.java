package com.test.ordersystem.service.pay;

import com.test.ordersystem.domain.entity.OrderItemEntity;
import com.test.ordersystem.dto.point.PointPayResponse;
import com.test.ordersystem.dto.point.PointRetrieveResponse;
import com.test.ordersystem.exception.ErrorMessage;
import com.test.ordersystem.service.point.PointService;
import com.test.ordersystem.service.order.OrderItemService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayService {

  private final OrderItemService orderItemService;
  private final PointService pointService;

  @Transactional

  public PointPayResponse pay(Long orderId, Long userId) {
    OrderItemEntity orderItem = orderItemService.findOrderItem(orderId);

    //지불해야할 금액
    BigDecimal orderPrice = orderItem.getOrderPrice();

    //현재 나의 포인트
    final BigDecimal points = getPoints(userId);

    if(orderPrice.compareTo(points) > 0){
      throw new IllegalStateException(ErrorMessage.NOT_ENOUGH_POINT.getMessage());
    }

    return pointService.pay(userId, orderPrice);

  }

  private BigDecimal getPoints(Long userId) {
    final PointRetrieveResponse pointRetrieveResponse = pointService.retrievePoint(userId);

    if(pointRetrieveResponse.getPointAmount().compareTo(BigDecimal.ZERO) == 0){
      throw new IllegalStateException(ErrorMessage.NOT_ENOUGH_POINT.getMessage());
    }
    return pointRetrieveResponse.getPointAmount();

  }
}
