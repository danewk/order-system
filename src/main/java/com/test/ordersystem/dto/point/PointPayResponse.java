package com.test.ordersystem.dto.point;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PointPayResponse {

  private BigDecimal pointAmount;
  private Long userId;
  private BigDecimal orderedPrice;
}
