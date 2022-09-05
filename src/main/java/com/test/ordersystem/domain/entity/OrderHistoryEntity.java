
package com.test.ordersystem.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_history",
    indexes = {
        @Index(name = "idx_order_history_created_at", columnList = "created_at"),
        @Index(name = "idx_order_history_deleted_at", columnList = "deleted_at")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistoryEntity extends BaseEntity {

  @Column(name = "menu_id")
  private Long menuId;

  @Column(name = "count_id")
  private Long count;

  @Column(name = "user_id")
  private Long userId;

  @Builder
  public OrderHistoryEntity(Long menuId, Long count, Long userId) {
    this.menuId = menuId;
    this.count = count;
    this.userId = userId;
  }
}
