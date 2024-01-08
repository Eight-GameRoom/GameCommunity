package com.example.gamecommunity.domain.admin.dto;

import lombok.Builder;

@Builder
public class AdminUserResponseDto {
  private String email;
  private String nickname;
  private String introduction;
  private int ranking;
  private String profileUrl;
}
