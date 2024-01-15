package com.example.gamecommunity.domain.comment.dto;

public record CommentRequestDto(
        String content

    public CommentRequestDto(Comment comment) {
        this(comment.getContent());
    }
}
