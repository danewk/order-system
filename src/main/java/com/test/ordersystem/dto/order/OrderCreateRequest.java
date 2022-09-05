package com.test.ordersystem.dto.order;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class OrderCreateRequest {

  private Long userId;
  private Long menuId;

}
