package com.example.gamecommunity.domain.Team.dto;

import com.example.gamecommunity.domain.Team.entity.Team;

public record TeamResponseDto(
    String name,
    String image,
    String introduction,
    String gameName
) {

  public TeamResponseDto(Team team) {
    this(team.getName(),
        team.getImage(),
        team.getIntroduction(),
        team.getGameName());
  }

  public TeamResponseDto(Long aLong) {
  }
}
