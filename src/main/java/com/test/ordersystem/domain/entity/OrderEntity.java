package com.test.ordersystem.domain.entity;

import com.test.ordersystem.domain.OrderStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity extends BaseEntity{

  @NonNull
  @Column(name = "user_id")
  private Long userId;

  @NonNull
  @Column(name = "order_date")
  private LocalDateTime orderDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus status; //주문상태 [ORDER, CANCEL]

  @Builder
  public OrderEntity(@NonNull Long userId, @NonNull LocalDateTime orderDate, OrderStatus status) {
    this.userId = userId;
    this.orderDate = orderDate;
    this.status = status;
  }

  public void setStatus(OrderStatus orderStatus){
    status = orderStatus;
  }



}
