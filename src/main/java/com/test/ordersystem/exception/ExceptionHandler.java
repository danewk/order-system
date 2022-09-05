package com.test.ordersystem.exception;

import com.test.ordersystem.dto.ResponseDto;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

  /**
   *  사용자 입력값이 잘못되었을 경우 Exception 핸들링
   */
  @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ResponseDto<?>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.warn("", e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ResponseDto.badRequest(ErrorMessage.INVALID_INPUT_VALUE.getMessage()));
  }

  /**
   *  지원하지 않는 HTTP Method 요청시 Exception 핸들링
   */
  @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ResponseDto<?>> handleException(
      HttpRequestMethodNotSupportedException ex) {
    log.warn("", ex);

    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(ResponseDto.error(ex.getMessage()));
  }

  /**
   *  서버 내부에서 예상하지 못한 예외 발생시 Exception 핸들링
   */
  @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
  public ResponseEntity<ResponseDto<?>> handleException(Exception ex) {
    log.warn("", ex);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ResponseDto.error(ErrorMessage.UNKNOWN.getMessage()));
  }


  /**
   *  서버 내부에서 예상하지 못한 예외 발생시 Exception 핸들링
   */
  @org.springframework.web.bind.annotation.ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
  public ResponseEntity<ResponseDto<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
    log.warn("", ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ResponseDto.error(ex.getMessage()));
  }
}