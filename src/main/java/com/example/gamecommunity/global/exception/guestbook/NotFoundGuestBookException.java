package com.example.gamecommunity.global.exception.guestbook;

import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundGuestBookException extends BusinessException {

  public NotFoundGuestBookException() {
    super(HttpStatus.valueOf(400), ErrorCode.NOT_FOUND_GUESTBOOK_EXCEPTION);

  }

}
