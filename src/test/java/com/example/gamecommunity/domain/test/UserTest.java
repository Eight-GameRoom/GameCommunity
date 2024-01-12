package com.example.gamecommunity.domain.test;

import com.example.gamecommunity.domain.user.entity.User;
import java.time.Instant;
import java.time.LocalDateTime;
import org.joda.time.DateTime;

public interface UserTest {
  String ANOTHER_PREFIX = "another-";
  Long TEST_USER_ID = 1L;
  Long TEST_ANOTHER_USER_ID = 2L;
  String TEST_EMAIL = "user@email.com";
  String TEST_PASSWORD = "password";
  String TEST_NICKNAME = "nickname";
  String TEST_INTRODUCTION = "intro";
  Instant TEST_BLOCK_DATE = Instant.now();
  int TEST_RANKING = 0;
  String TEST_PROFILE_URL = "url";

  User TEST_USER = User.builder()
      .id(TEST_USER_ID)
      .email(TEST_EMAIL)
      .password(TEST_PASSWORD)
      .nickname(TEST_NICKNAME)
      .introduction(TEST_INTRODUCTION)
      .blockDate(TEST_BLOCK_DATE)
      .ranking(TEST_RANKING)
      .profileUrl(TEST_PROFILE_URL)
      .build();

  User TEST_ANOTHER_USER = User.builder()
      .id(TEST_ANOTHER_USER_ID)
      .email(ANOTHER_PREFIX + TEST_EMAIL)
      .password(ANOTHER_PREFIX +TEST_PASSWORD)
      .nickname(ANOTHER_PREFIX +TEST_NICKNAME)
      .introduction(ANOTHER_PREFIX +TEST_INTRODUCTION)
      .blockDate(TEST_BLOCK_DATE)
      .ranking(TEST_RANKING)
      .profileUrl(ANOTHER_PREFIX +TEST_PROFILE_URL)
      .build();
}