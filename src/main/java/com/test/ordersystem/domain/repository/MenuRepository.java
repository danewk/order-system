package com.test.ordersystem.domain.repository;

import com.test.ordersystem.domain.entity.MenuEntity;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

  List<MenuEntity> findAllByDeletedAtIsNull();

  @Query("select m from MenuEntity m where m.deletedAt is null and m.id =:id")
  Optional<MenuEntity> findAllWithMenuId(Long id);
}
