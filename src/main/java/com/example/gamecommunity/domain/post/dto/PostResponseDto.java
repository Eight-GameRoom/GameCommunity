package com.example.gamecommunity.domain.post.dto;

import com.example.gamecommunity.domain.enums.board.BoardName;
import com.example.gamecommunity.domain.enums.game.name.GameName;
import com.example.gamecommunity.domain.enums.game.type.GameType;
import com.example.gamecommunity.domain.post.entity.Post;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PostResponseDto(

    Long postId,
    String postTitle,
    String postContent,
    String postAuthor,
    String postImageUrl,
    GameType gameType,
    GameName gameName,
    BoardName boardName,
    Integer report,
    Integer postLike,
    Integer postUnlike,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt) {

  public static PostResponseDto fromEntity(Post post) {
    return new PostResponseDto(
        post.getPostId(),
        post.getPostTitle(),
        post.getPostContent(),
        post.getPostAuthor(),
        post.getPostImageUrl(),
        post.getGameType(),
        post.getGameName(),
        post.getBoardName(),
        post.getReport(),
        post.getPostLike(),
        post.getPostUnlike(),
        post.getCreatedAt(),
        post.getModifiedAt()
    );
  }
}
