package com.example.gamecommunity.domain.admin.dto;

import lombok.Builder;

@Builder
public record AdminUserResponseDto(
    String email,
    String nickname,
    String introduction,
    int ranking,
    String profileUrl
) {

}
