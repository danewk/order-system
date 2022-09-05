package com.test.ordersystem.dto.point;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PointChargeRequest {

  private Long userId;
  private BigDecimal pointAmount;

}
