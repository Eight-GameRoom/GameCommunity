package com.example.gamecommunity.global.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  // post
  POST_NOT_FOUND_EXCEPTION(HttpStatus.UNAUTHORIZED, "해당 게시글을 찾을 수 없습니다.");


  private final String message;
  private final HttpStatus status;

  ErrorCode (HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

}
