package com.example.gamecommunity.domain.admin.dto;

import com.example.gamecommunity.domain.user.entity.User;
import lombok.Builder;

public record AdminUserResponseDto(
    String email,
    String nickname,
    String introduction,
    int ranking,
    String profileUrl
) {
  public AdminUserResponseDto(User user) {
    this(
        user.getEmail(),
        user.getNickname(),
        user.getIntroduction(),
        user.getRanking(),
        user.getProfileUrl()
    );
  }
}
