package com.test.ordersystem.service.menu;

import static org.junit.jupiter.api.Assertions.*;

import com.test.ordersystem.domain.entity.PopularMenuEntity;
import com.test.ordersystem.domain.repository.PopularMenuRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PopularMenuServiceTest {

  @Autowired
  PopularMenuService popularMenuService;

  @Autowired
  PopularMenuRepository popularMenuRepository;

  @Test
  @DisplayName("등록 후 삭제 테스트")
  @Transactional
  void deleteTest(){

    PopularMenuEntity popularMenuEntity1 = PopularMenuEntity.builder()
        .menuId(1L)
        .count(14000L)
        .build();
    PopularMenuEntity popularMenuEntity2 = PopularMenuEntity.builder()
        .menuId(1L)
        .count(14000L)
        .build();
    PopularMenuEntity popularMenuEntity3 = PopularMenuEntity.builder()
        .menuId(1L)
        .count(14000L)
        .build();

    List<PopularMenuEntity> popularMenuEntityList = List.of(popularMenuEntity1, popularMenuEntity2, popularMenuEntity3);

    popularMenuService.create(popularMenuEntityList);
    popularMenuService.deleteExistData();

    List<PopularMenuEntity> menuEntityList = popularMenuRepository.findAllByDeletedAtIsNull();

    assertEquals(menuEntityList.size(), 0);

  }


}