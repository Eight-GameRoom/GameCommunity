package com.example.gamecommunity.domain.test;

import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.user.entity.User;

public interface CommentTest extends PostTest, UserTest{
  String ANOTHER_PREFIX = "another-";
  Long TEST_COMMENT_ID = 1L;
  Long TEST_ANOTHER_COMMENT_ID = 2L;
  String TEST_COMMENT_TITLE = "title";
  String TEST_COMMENT_CONTENT = "content";
  String TEST_COMMENT_IMAGE_URL = "url";
  GameType TEST_GAME_TYPE = GameType.PC_GAME;
  GameName TEST_GAME_NAME = GameName.LEAGUE_OF_LEGEND;
  BoardName TEST_BOARD_NAME = BoardName.FREE_BOARD;
  Integer TEST_REPORT = 0;
  Integer TEST_COMMENT_LIKE = 0;
  Integer TEST_COMMENT_Unlike = 0;
  User TEST_USER = UserTest.TEST_USER;

  Post TEST_COMMENT = Post.builder()
      .postId(TEST_COMMENT_ID)
      .postTitle(TEST_COMMENT_TITLE)
      .postContent(TEST_COMMENT_CONTENT)
      .postImageUrl(TEST_COMMENT_IMAGE_URL)
      .postAuthor(TEST_USER.getNickname())
      .gameType(TEST_GAME_TYPE)
      .gameName(TEST_GAME_NAME)
      .boardName(TEST_BOARD_NAME)
      .report(TEST_REPORT)
      .postLike(TEST_COMMENT_LIKE)
      .postUnlike(TEST_COMMENT_Unlike)
      .user(TEST_USER)
      .build();

  Post TEST_ANOTHER_COMMENT = Post.builder()
      .postId(TEST_ANOTHER_COMMENT_ID)
      .postTitle(ANOTHER_PREFIX + TEST_COMMENT_TITLE)
      .postContent(ANOTHER_PREFIX + TEST_COMMENT_CONTENT)
      .postImageUrl(ANOTHER_PREFIX + TEST_COMMENT_IMAGE_URL)
      .postAuthor(ANOTHER_PREFIX + TEST_USER.getNickname())
      .gameType(TEST_GAME_TYPE)
      .gameName(TEST_GAME_NAME)
      .boardName(TEST_BOARD_NAME)
      .report(TEST_REPORT)
      .postLike(TEST_COMMENT_LIKE)
      .postUnlike(TEST_COMMENT_Unlike)
      .user(TEST_ANOTHER_USER)
      .build();
}
