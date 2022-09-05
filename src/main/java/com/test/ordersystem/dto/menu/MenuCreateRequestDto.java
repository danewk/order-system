package com.test.ordersystem.dto.menu;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MenuCreateRequestDto {

  private String name;
  private BigDecimal price;

}
