package com.test.ordersystem.domain.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "order_items",
    indexes = {
        @Index(name = "idx_order_items_order_id", columnList = "order_id"),
        @Index(name = "idx_order_items_deleted_at", columnList = "deleted_at")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemEntity extends BaseEntity{

  @NonNull
  @Column(name = "order_id")
  private Long orderId;

  @NonNull
  @Column(name = "order_price")
  private BigDecimal orderPrice;

  @NonNull
  @Column(name = "menu_id")
  private Long menuId;

  @Builder
  public OrderItemEntity(@NonNull Long orderId, @NonNull BigDecimal orderPrice, @NonNull Long menuId) {
    this.orderId = orderId;
    this.orderPrice = orderPrice;
    this.menuId = menuId;
  }
}
