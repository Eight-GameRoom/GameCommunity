package com.example.gamecommunity.domain.test;

import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.team.entity.Team;
import com.example.gamecommunity.domain.user.entity.User;

public interface TeamTest extends UserTest{
  String ANOTHER_PREFIX = "another-";
  Long TEST_TEAM_ID = 1L;
  Long TEST_ANOTHER_TEAM_ID = 2L;
  String TEST_TEAM_NAME = "name1";
  String TEST_TEAM_IMAGE = "image url1";
  String TEST_TEAM_INTRODUCTION = "intro1";
  String TEST_ANOTHER_TEAM_NAME = "name1";
  String TEST_ANOTHER_TEAM_IMAGE = "image url1";
  String TEST_ANOTHER_TEAM_INTRODUCTION = "intro1";
  Long TEST_TEAM_ADMIN_ID =1L;
  GameName TEST_ANOTHER_GAME_NAME = GameName.VALORANT;
  GameName TEST_GAME_NAME = GameName.LEAGUE_OF_LEGEND;
  User TEST_USER = UserTest.TEST_USER;

  Team TEST_TEAM = Team.builder()
      .teamId(TEST_TEAM_ID)
      .teamName(TEST_TEAM_NAME)
      .teamIntroduction(TEST_TEAM_INTRODUCTION)
      .teamImage(TEST_TEAM_IMAGE)
      .teamAdminId(TEST_TEAM_ADMIN_ID)
      .build();

  Team ANOTHER_TEST_TEAM = Team.builder()
      .teamId(TEST_ANOTHER_TEAM_ID)
      .teamName(TEST_ANOTHER_TEAM_NAME)
      .teamIntroduction(TEST_ANOTHER_TEAM_INTRODUCTION)
      .teamImage(TEST_ANOTHER_TEAM_IMAGE)
      .teamAdminId(TEST_TEAM_ADMIN_ID)
      .build();



}
