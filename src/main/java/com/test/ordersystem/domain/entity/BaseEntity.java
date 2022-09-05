package com.test.ordersystem.domain.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Getter
  private Long id;

  @CreatedDate
  @Column(
      name = "created_at",
      columnDefinition = "timestamp null default null"
  )
  @Getter
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(
      name = "updated_at",
      columnDefinition = "timestamp null default null"
  )
  @Getter
  private LocalDateTime updatedAt;

  @Column(
      name = "deleted_at",
      columnDefinition = "timestamp null default null"
  )
  @Getter
  private LocalDateTime deletedAt;

  protected BaseEntity() {
  }

  protected BaseEntity(Long id) {
    this.id = id;
  }

  protected void softDelete(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  protected boolean isSoftDeleted() {
    return null != this.deletedAt;
  }

  public void delete() {
    this.softDelete(LocalDateTime.now());
  }

  public boolean isDeleted() {
    return this.isSoftDeleted();
  }

  protected void undoDelete() {
    this.deletedAt = null;
  }

}
