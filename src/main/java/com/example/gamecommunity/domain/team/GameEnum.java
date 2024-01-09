package com.example.gamecommunity.domain.team;

import lombok.Getter;

@Getter
public enum GameEnum {
  MINECRAFT("Minecraft"),
  FORTNITE("Fortnite"),
  PUBG("PlayerUnknown's Battlegrounds"),
  LEAGUE_OF_LEGENDS("League of Legends"),
  OVERWATCH("Overwatch");

  // 게임 이름을 반환하는 메서드
  private final String gameName;  // 게임 이름을 나타내는 변수

  // 생성자
  GameEnum(String gameName) {
    this.gameName = gameName;
  }

}
