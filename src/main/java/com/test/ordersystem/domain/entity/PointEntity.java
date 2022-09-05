package com.test.ordersystem.domain.entity;

import com.test.ordersystem.domain.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "points",
    indexes = {
        @Index(name = "idx_points_user_id", columnList = "user_id"),
        @Index(name = "idx_points_transaction_type", columnList = "transaction_type"),
        @Index(name = "idx_points_deleted_at", columnList = "deleted_at")
    })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointEntity extends BaseEntity {

  @NonNull
  @Column(name = "point")
  private BigDecimal point; //포인트

  @Column(name = "deposit_date", columnDefinition = "timestamp null default null")
  private LocalDateTime depositDate; //포인트 입금 시간

  @Column(name = "withdrawal_date", columnDefinition = "timestamp null default null")
  private LocalDateTime withdrawalDate; //포인트 출금 시간

  @Enumerated(EnumType.STRING)
  @Column(name = "transaction_type")
  private TransactionType transactionType;

  @Column(name = "user_id")
  private Long userId;

  @Builder
  public PointEntity(@NonNull BigDecimal point, LocalDateTime depositDate, LocalDateTime withdrawalDate, TransactionType transactionType, Long userId) {
    this.point = point;
    this.depositDate = depositDate;
    this.withdrawalDate = withdrawalDate;
    this.transactionType = transactionType;
    this.userId = userId;
  }
}
