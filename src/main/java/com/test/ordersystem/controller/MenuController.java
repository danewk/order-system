package com.test.ordersystem.controller;

import com.test.ordersystem.dto.ResponseDto;
import com.test.ordersystem.dto.menu.PopularMenuResponse;
import com.test.ordersystem.service.menu.MenuService;
import com.test.ordersystem.service.menu.PopularMenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class MenuController {

  private final MenuService menuService;
  private final PopularMenuService popularMenuService;

  @GetMapping("/menus")
  public ResponseDto<?> getMenus() {
    return ResponseDto.ok(menuService.getMenus());
  }


  @GetMapping("/menus/top3")
  public List<PopularMenuResponse> getTop3Menus() {
    return popularMenuService.top3Menu();
  }

}
