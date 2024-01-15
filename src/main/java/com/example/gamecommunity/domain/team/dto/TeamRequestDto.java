package com.example.gamecommunity.domain.team.dto;

import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.team.entity.Team;


public record TeamRequestDto(
    String name,
    String image,
    String introduction,
    GameName gameName
) {
  public TeamRequestDto(Team team) {
    this(team.getTeamName(),
        team.getTeamImage(),
        team.getTeamIntroduction(),
        team.getGameName());
  }

}
