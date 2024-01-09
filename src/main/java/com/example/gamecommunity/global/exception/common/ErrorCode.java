package com.example.gamecommunity.global.exception.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

  // post
  POST_NOT_FOUND_EXCEPTION("해당 게시글을 찾을 수 없습니다."),

  // user
  ALREADY_EXIST_USER_EMAIL_EXCEPTION( "이미 존재하는 이메일 입니다."),
  NOT_EQUALS_CONFIRM_PASSWORD("비밀번호 확인이 일치하지 않습니다.."),
  FAILED_AUTHENTICATION_EXCEPTION("인증에 실패하였습니다."),
  NOT_FOUND_USER_EXCEPTION("해당 유저는 없습니다."),

  // team
  NOT_FOUND_TEAM_EXCEPTION("해당 팀이 존재하지 않습니다."),
  NOT_EQUALS_TEAM_ADMIN("팀의 관리자가 아닙니다."),
  NOT_FOUND_TEAM_USER("팀에 유저가 속해있지 않습니다.");


  private final String message;

  ErrorCode (String message) {
    this.message = message;
  }

}