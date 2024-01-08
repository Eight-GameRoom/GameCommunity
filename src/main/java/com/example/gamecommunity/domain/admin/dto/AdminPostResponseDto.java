package com.example.gamecommunity.domain.admin.dto;

import lombok.Builder;

@Builder
public class AdminPostResponseDto {
  private String postTitle;
  private String postContent;
}
