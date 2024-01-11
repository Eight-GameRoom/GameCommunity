package com.example.gamecommunity.domain.team.dto;

import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.team.GameEnum;
import com.example.gamecommunity.domain.team.entity.Team;
import lombok.Getter;


public record TeamRequestDto(
    String name,
    String image,
    String introduction,
    GameName gameName
) {
  public TeamRequestDto(Team team) {
    this(team.getName(),
        team.getImage(),
        team.getIntroduction(),
        team.getGameName());
  }

}