package com.test.ordersystem.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{

  @NonNull
  @Column(name = "user_name")
  private String userName; //제목

  @NonNull
  @Column(name = "point_id")
  private Long pointId;

  @NonNull
  @Column(name = "order_id")
  private Long order_id;

  @Builder
  public User(@NonNull String userName, @NonNull Long pointId, @NonNull Long order_id) {
    this.userName = userName;
    this.pointId = pointId;
    this.order_id = order_id;
  }
}
