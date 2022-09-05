package com.test.ordersystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ResponseDto<T> {

  private boolean ok;

  @JsonIgnore
  private Integer status;

  private T data;

  private Error error;

  private ResponseDto(boolean ok, Integer status, @Nullable T data,
      @Nullable Error error) {
    super();
    this.ok = ok;
    this.status = status;
    this.data = data;
    this.error = error;
  }


  public static <T> ResponseDto<T> ok() {
    return new ResponseDto<>(true, HttpStatus.OK.value(), null, null);
  }

  public static <T> ResponseDto<T> ok(T data) {
    return new ResponseDto<>(true, HttpStatus.OK.value(), data, null);
  }

  public static <T> ResponseDto<T> badRequest(String message) {
    Error error = ResponseDto.Error.builder()
        .code(-1)
        .message(message)
        .build();

    return new ResponseDto<>(false, HttpStatus.BAD_REQUEST.value(), null, error);
  }

  public static <T> ResponseDto<T> unauthorized(String message) {
    Error error = ResponseDto.Error.builder()
        .code(-1)
        .message(message)
        .build();

    return new ResponseDto<>(false, HttpStatus.UNAUTHORIZED.value(), null, error);
  }

  public static <T> ResponseDto<T> notFound(String message) {
    Error error = ResponseDto.Error.builder()
        .code(-1)
        .message(message)
        .build();

    return new ResponseDto<>(false, HttpStatus.NOT_FOUND.value(), null, error);
  }

  public static <T> ResponseDto<T> error(String message) {
    Error error = ResponseDto.Error.builder()
        .code(-1)
        .message(message)
        .build();

    return new ResponseDto<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, error);
  }

  public static <T> ResponseDto<T> error(String message, int code) {
    Error error = ResponseDto.Error.builder()
        .code(code)
        .message(message)
        .build();

    return new ResponseDto<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), null, error);
  }


  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  @Getter
  public static class Error {

    private int code;

    private String message;

    private String type;

    @Builder
    public Error(int code, String message, String type) {
      super();
      this.code = code;
      this.type = type;
      this.message = message;
    }

  }

}