package com.test.ordersystem.dto.point;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PointChargeResponse {
  private BigDecimal pointAmount;
  private BigDecimal chargeAmount;
  private Long userId;
}
