package com.example.gamecommunity.global.exception.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

  // post
  POST_NOT_FOUND_EXCEPTION("해당 게시글을 찾을 수 없습니다.");


  private final String message;

  ErrorCode (String message) {
    this.message = message;
  }

}