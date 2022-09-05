package com.test.ordersystem.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.Getter;

public enum ErrorMessage {

  //common
  INVALID_INPUT_VALUE("잘못된 인자 입니다."),
  UNKNOWN("알 수 없는 오류가 발생했습니다. 고객센터로 문의해주세요."),

  //메세지 정의
  NOT_EXIST_ORDER("주문데이터가 존재하지 않습니다."),
  NOT_EXIST_MENU("주문하신 메뉴들 중 일부가 메뉴에 존재하지 않습니다."),
  POINT_ERROR("입금포인트보다 출금포인트가 더 많습니다. 오류입니다."),
  NOT_ENOUGH_POINT("잔여 포인트가 부족합니다. 충전 후 결제 부탁드립니다.");



  @Getter
  private final String message; // 사용자에게 노출될 메세지

  ErrorMessage(String message) {
    this.message = message;
  }
}