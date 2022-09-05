package com.test.ordersystem.dto.point;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PointRetrieveResponse {

  private BigDecimal pointAmount;
  private Long userId;
}
