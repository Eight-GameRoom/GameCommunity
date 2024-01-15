package com.example.gamecommunity.domain.comment.dto;

import com.example.gamecommunity.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

public record CommentRequestDto(
        String content
) {

    public CommentRequestDto(Comment comment) {
        this(comment.getContent());
    }

}
