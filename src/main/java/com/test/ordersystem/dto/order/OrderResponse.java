package com.test.ordersystem.dto.order;

import com.test.ordersystem.domain.OrderStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderResponse {

  private Long orderId;
  private BigDecimal orderedPrice;
  private BigDecimal remainingPoints;
  private OrderStatus orderStatus;

}
