package com.example.gamecommunity.domain.post.entity;

import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.global.audting.TimeStamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post extends TimeStamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;

  @Column(nullable = false)
  private String postTitle;

  @Column(nullable = false)
  private String postContent;

  @Column
  private String postImageUrl;

  @Column
  private String postAuthor;

  @Column
  @Enumerated(EnumType.STRING)
  private GameType gameType;

  @Column
  @Enumerated(EnumType.STRING)
  private GameName gameName;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private BoardName boardName;

  @Column(nullable = false)
  private Integer report;

  @Column(nullable = false)
  private Integer postLike;

  @Column(nullable = false)
  private Integer postUnlike;

  public Post(PostRequestDto requestDto, GameType gameType, GameName gameName,
      BoardName boardName) {
    this.postId = getPostId();
    this.postTitle = requestDto.postTitle();
    this.postContent = requestDto.postContent();
    this.postImageUrl = requestDto.postImageUrl();
    this.postAuthor = "username";
    this.gameType = gameType;
    this.gameName = gameName;
    this.boardName = boardName;
    this.report = 0;
    this.postLike = 0;
    this.postUnlike = 0;
  }

  public void update(PostRequestDto requestDto) {
    this.postTitle = requestDto.postTitle();
    this.postContent = requestDto.postContent();
  }
}
