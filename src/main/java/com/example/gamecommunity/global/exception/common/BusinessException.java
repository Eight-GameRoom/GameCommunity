package com.example.gamecommunity.global.exception.common;

public class BusinessException extends RuntimeException{

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
  }

  public BusinessException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessage(), cause);
  }
}
