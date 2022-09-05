package com.test.ordersystem.domain.entity;

import com.test.ordersystem.domain.repository.PopularMenuRepository;
import com.test.ordersystem.dto.menu.PopularMenuResponse;
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
@Table(name = "popular_menu",
    indexes = {
        @Index(name = "idx_popular_menu_deleted_at", columnList = "deleted_at")}
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularMenuEntity extends BaseEntity{

  @NonNull
  @Column(name = "menu_id")
  private Long menuId;

  @NonNull
  @Column(name = "count")
  private Long count;

  @Builder
  public PopularMenuEntity(@NonNull Long menuId, @NonNull Long count) {
    this.menuId = menuId;
    this.count = count;
  }

  public static PopularMenuResponse from(PopularMenuEntity popularMenuEntity){
    return PopularMenuResponse.builder()
        .menuId(popularMenuEntity.getMenuId())
        .count(popularMenuEntity.getCount())
        .build();
  }
}
