package com.example.gamecommunity.domain.team.dto;

import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.team.entity.Team;

public record TeamResponseDto(
    String teamName,
    String teamImage,
    String teamIntroduction,
    GameName gameName
) {

  public TeamResponseDto(Team team) {
    this(team.getTeamName(),
        team.getTeamImage(),
        team.getTeamIntroduction(),
        team.getGameName());
  }

}
