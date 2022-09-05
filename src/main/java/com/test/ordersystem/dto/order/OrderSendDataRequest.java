package com.test.ordersystem.dto.order;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderSendDataRequest {

  private Long userId;
  private Long menuId;
  private BigDecimal orderedPrice;

}
