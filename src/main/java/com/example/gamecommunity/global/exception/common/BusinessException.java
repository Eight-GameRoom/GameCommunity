package com.example.gamecommunity.global.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException{
  private HttpStatus status;

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
  }

  public BusinessException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessage(), cause);
  }
}