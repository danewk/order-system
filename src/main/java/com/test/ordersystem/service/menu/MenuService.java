package com.test.ordersystem.service.menu;

import com.test.ordersystem.domain.entity.MenuEntity;
import com.test.ordersystem.domain.repository.MenuRepository;
import com.test.ordersystem.dto.menu.MenuResponse;
import com.test.ordersystem.service.order.OrderHistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MenuService {

  private final MenuRepository menuRepository;
  private final OrderHistoryService menuCountService;

  @Transactional(readOnly = true)
  public List<MenuResponse> getMenus() {
    List<MenuEntity> menus = menuRepository.findAllByDeletedAtIsNull();

    return menus.stream()
        .map(MenuEntity::toDto)
        .toList();

  }

}
