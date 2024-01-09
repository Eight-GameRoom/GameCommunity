package com.example.gamecommunity.domain.team.dto;

import com.example.gamecommunity.domain.team.entity.Team;
import lombok.Getter;


public record TeamRequestDto(
    String name,
    String image,
    String introduction,
    String gameName
) {
  public TeamRequestDto(Team team) {
    this(team.getName(),
        team.getImage(),
        team.getIntroduction(),
        team.getGameName());
  }

}
