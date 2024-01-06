package com.example.gamecommunity.global.exception.post;

import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;

public class PostNotFoundException extends BusinessException {

  public PostNotFoundException() {
    super(ErrorCode.POST_NOT_FOUND_EXCEPTION);
  }
}
