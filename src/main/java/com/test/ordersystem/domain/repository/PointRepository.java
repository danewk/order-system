package com.test.ordersystem.domain.repository;

import com.test.ordersystem.domain.entity.PointEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<PointEntity, Long> {

  List<PointEntity> findAllByUserIdAndDeletedAtIsNull(Long userId);
}
