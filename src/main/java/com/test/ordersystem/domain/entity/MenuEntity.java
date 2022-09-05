package com.test.ordersystem.domain.entity;

import com.test.ordersystem.dto.menu.MenuResponse;
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
@Table(name = "menus",
    indexes = {
        @Index(name = "idx_menus_id", columnList = "id"),
        @Index(name = "idx_menus_deleted_at", columnList = "deleted_at")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuEntity extends BaseEntity {

  @NonNull
  @Column(name = "name")
  private String name; //제목

  @NonNull
  @Column(name = "price")
  private BigDecimal price; //가격

  @Builder
  public MenuEntity(@NonNull String name, @NonNull BigDecimal price) {
    this.name = name;
    this.price = price;
  }
  
  public static MenuResponse toDto(MenuEntity menuEntity){
    return MenuResponse.builder()
        .id(menuEntity.getId())
        .name(menuEntity.getName())
        .price(menuEntity.getPrice())
        .build();
  }
}
