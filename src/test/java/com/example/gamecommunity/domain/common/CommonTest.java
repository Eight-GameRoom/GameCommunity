package com.example.gamecommunity.domain.common;

import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.user.entity.User;

public interface CommonTest {

  String ANOTHER_PREFIX = "another-";

  // post
  Long TEST_POST_ID = 1L;
  Long TEST_ANOTHER_POST_ID = 2L;
  String TEST_POST_TITLE = "title";
  String TEST_POST_CONTENT = "content";
  String TEST_POST_IMAGE_URL = "url";
  String TEST_POST_AUTHOR = "username";
  GameType GAME_TYPE = GameType.PC_GAME;
  GameName GAME_NAME = GameName.LEAGUE_OF_LEGEND;
  BoardName BOARD_NAME = BoardName.FREE_BOARD;
  Integer REPORT = 0;
  Integer POST_LIKE = 0;
  Integer POST_Unlike = 0;

  Post TEST_POST = Post.builder()
      .postId(TEST_POST_ID)
      .postTitle(TEST_POST_TITLE)
      .postContent(TEST_POST_CONTENT)
      .postImageUrl(TEST_POST_IMAGE_URL)
      .postAuthor(TEST_POST_AUTHOR)
      .gameType(GAME_TYPE)
      .gameName(GAME_NAME)
      .boardName(BOARD_NAME)
      .report(REPORT)
      .postLike(POST_LIKE)
      .postUnlike(POST_Unlike)
      .build();

  Post TEST_ANOTHER_POST = Post.builder()
      .postId(TEST_ANOTHER_POST_ID)
      .postTitle(ANOTHER_PREFIX + TEST_POST_TITLE)
      .postContent(ANOTHER_PREFIX + TEST_POST_CONTENT)
      .postImageUrl(ANOTHER_PREFIX + TEST_POST_IMAGE_URL)
      .postAuthor(ANOTHER_PREFIX + TEST_POST_AUTHOR)
      .gameType(GAME_TYPE)
      .gameName(GAME_NAME)
      .boardName(BOARD_NAME)
      .report(REPORT)
      .postLike(POST_LIKE)
      .postUnlike(POST_Unlike)
      .build();
}