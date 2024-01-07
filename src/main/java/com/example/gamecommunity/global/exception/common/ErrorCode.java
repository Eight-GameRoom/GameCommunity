package com.example.gamecommunity.global.exception.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
  // post
  POST_NOT_FOUND_EXCEPTION(404,"해당 게시글을 찾을 수 없습니다."),

  // 회원
  ALREADY_EXIST_USER_NAME_EXCEPTION(409, "이미 존재하는 이름입니다."),
  WRONG_PASSWORD_EXCEPTION(400, "틀린 비밀번호입니다."),
  FAILED_AUTHENTICATION_EXCEPTION(401, "인증에 실패하였습니다."),
  INVALID_PASSWORD_EXCEPTION(400,"비밀번호에 이름이 포함되면 안됩니다."),
  NOT_FOUND_USER_EXCEPTION(404,"해당 유저는 없습니다.");

  private final int status;

  private  final String message;

  ErrorCode(int status,String message){
    this.status = status;
    this.message = message;
  }

}
