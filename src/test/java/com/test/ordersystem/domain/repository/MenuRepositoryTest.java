package com.test.ordersystem.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.test.ordersystem.domain.entity.MenuEntity;
import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

class MenuRepositoryTest extends BaseRepositoryTest{

  @Autowired
  MenuRepository menuRepository;

  @Test
  @DisplayName("저장 테스트")
  public void saveTest(){
    MenuEntity menu = getMenuEntity();

    MenuEntity savedMenu = menuRepository.saveAndFlush(menu);

    Assertions.assertThat(menu.getId()).isEqualTo(savedMenu.getId());
  }

  @Test
  @DisplayName("삭제 테스트")
  @Transactional
  public void deleteTest(){
    MenuEntity menu = getMenuEntity();

    MenuEntity savedMenu = menuRepository.save(menu);

    savedMenu.delete();

    Assertions.assertThat(menu.getDeletedAt()).isNotNull();
  }

  private MenuEntity getMenuEntity() {
    MenuEntity menu = MenuEntity.builder()
        .price(BigDecimal.valueOf(3000L))
        .name("아이스아메리카노")
        .build();
    return menu;
  }

}

