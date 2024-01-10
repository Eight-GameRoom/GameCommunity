package com.example.gamecommunity.global.exception.user;

import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundUserException extends BusinessException {
  public NotFoundUserException() {
    super(HttpStatus.valueOf(400),ErrorCode.NOT_FOUND_USER_EXCEPTION);
  }

}
