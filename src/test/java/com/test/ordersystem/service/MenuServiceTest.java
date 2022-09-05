package com.test.ordersystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.test.ordersystem.domain.entity.MenuEntity;
import com.test.ordersystem.domain.repository.MenuRepository;
import com.test.ordersystem.dto.menu.MenuResponse;
import com.test.ordersystem.service.menu.MenuService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MenuServiceTest {

  @Autowired
  MenuService menuService;

  @Autowired
  MenuRepository menuRepository;

  @Test
  @DisplayName("생성 후 조회 테스트")
  @Transactional
  void 생성후_조회테스트(){
    MenuEntity menuEntity1 = new MenuEntity("아이스아메리카노", BigDecimal.valueOf(2000));
    MenuEntity menuEntity2 = new MenuEntity("카페라떼", BigDecimal.valueOf(3000));
    MenuEntity menuEntity3 = new MenuEntity("콜드브루", BigDecimal.valueOf(4000));

    menuRepository.saveAll(
        List.of(menuEntity1, menuEntity2, menuEntity3)
    );

    List<MenuResponse> menus = menuService.getMenus();

    assertEquals(3, menus.size());

  }

}