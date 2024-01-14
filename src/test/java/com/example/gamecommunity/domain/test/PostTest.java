package com.example.gamecommunity.domain.test;

import com.example.gamecommunity.domain.enums.board.BoardName;
import com.example.gamecommunity.domain.enums.game.name.GameName;
import com.example.gamecommunity.domain.enums.game.type.GameType;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.user.entity.User;

public interface PostTest extends UserTest {

  String ANOTHER_PREFIX = "another-";
  Long TEST_POST_ID = 1L;
  Long TEST_ANOTHER_POST_ID = 2L;
  String TEST_POST_TITLE = "title";
  String TEST_POST_CONTENT = "content";
  String TEST_POST_IMAGE_URL = "url";
  GameType TEST_GAME_TYPE = GameType.PC_GAME;
  GameName TEST_GAME_NAME = GameName.LEAGUE_OF_LEGEND;
  BoardName TEST_BOARD_NAME = BoardName.FREE_BOARD;
  Integer TEST_REPORT = 0;
  Integer TEST_POST_LIKE = 0;
  Integer TEST_POST_Unlike = 0;
  User TEST_USER = UserTest.TEST_USER;

  Post TEST_POST = Post.builder()
      .postId(TEST_POST_ID)
      .postTitle(TEST_POST_TITLE)
      .postContent(TEST_POST_CONTENT)
      .postImageUrl(TEST_POST_IMAGE_URL)
      .postAuthor(TEST_USER.getNickname())
      .gameType(TEST_GAME_TYPE)
      .gameName(TEST_GAME_NAME)
      .boardName(TEST_BOARD_NAME)
      .report(TEST_REPORT)
      .postLike(TEST_POST_LIKE)
      .postUnlike(TEST_POST_Unlike)
      .user(TEST_USER)
      .build();

  Post TEST_ANOTHER_POST = Post.builder()
      .postId(TEST_ANOTHER_POST_ID)
      .postTitle(ANOTHER_PREFIX + TEST_POST_TITLE)
      .postContent(ANOTHER_PREFIX + TEST_POST_CONTENT)
      .postImageUrl(ANOTHER_PREFIX + TEST_POST_IMAGE_URL)
      .postAuthor(ANOTHER_PREFIX + TEST_USER.getNickname())
      .gameType(TEST_GAME_TYPE)
      .gameName(TEST_GAME_NAME)
      .boardName(TEST_BOARD_NAME)
      .report(TEST_REPORT)
      .postLike(TEST_POST_LIKE)
      .postUnlike(TEST_POST_Unlike)
      .user(TEST_ANOTHER_USER)
      .build();
}