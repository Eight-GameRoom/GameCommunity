package com.example.gamecommunity.global.exception.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

  // post
  NOT_FOUND_POST_EXCEPTION("해당 게시글을 찾을 수 없습니다."),
  SELF_LIKE_EXCEPTION("자신의 글에는 좋아요를 누를 수 없습니다."),
  DUPLICATED_LIKE_EXCEPTION("이미 좋아요 또는 싫어요 내역이 있습니다."),
  NOT_FOUND_LIKE_EXCEPTION("좋아요 내역이 없습니다."),
  DUPLICATED_REPORT_EXCEPTION("이미 신고한 내역이 있습니다."),

  // user
  ALREADY_EXIST_USER_EMAIL_EXCEPTION("이미 존재하는 이메일 입니다."),
  NOT_EQUALS_CONFIRM_PASSWORD("비밀번호 확인이 일치하지 않습니다.."),
  FAILED_AUTHENTICATION_EXCEPTION("인증에 실패하였습니다."),
  NOT_FOUND_USER_EXCEPTION("해당 유저는 없습니다."),
  AUTHENTICATION_MISMATCH_EXCEPTION("수정 및 삭제 권한이 없습니다.");

  private final String message;

  ErrorCode(String message) {
    this.message = message;
  }

}
