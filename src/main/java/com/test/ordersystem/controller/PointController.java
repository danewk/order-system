package com.test.ordersystem.controller;

import com.test.ordersystem.dto.point.PointChargeRequest;
import com.test.ordersystem.dto.ResponseDto;
import com.test.ordersystem.service.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class PointController {

  private final PointService pointService;

  @PostMapping("/point")
  public ResponseDto<?> chargePoint(@RequestBody PointChargeRequest request) {
    return ResponseDto.ok(pointService.charge(request));
  }

}
