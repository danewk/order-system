package com.test.ordersystem.domain.repository;

import com.test.ordersystem.domain.entity.PopularMenuEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopularMenuRepository extends JpaRepository<PopularMenuEntity, Long> {

  List<PopularMenuEntity> findAllByDeletedAtIsNull();
}
