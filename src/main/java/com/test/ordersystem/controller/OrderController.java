package com.test.ordersystem.controller;

import com.test.ordersystem.dto.ResponseDto;
import com.test.ordersystem.dto.order.OrderCreateRequest;
import com.test.ordersystem.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/order")
  public ResponseDto<?> createOrder(@RequestBody OrderCreateRequest request){
    return ResponseDto.ok(orderService.create(request));
  }

}
