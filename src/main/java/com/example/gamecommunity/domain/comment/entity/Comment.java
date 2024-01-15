package com.example.gamecommunity.domain.comment.entity;

import com.example.gamecommunity.domain.comment.dto.CommentRequestDto;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.auditing.TimeStamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
@Builder
@AllArgsConstructor
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Comment(User user, Post post, CommentRequestDto requestDto){
        this.user = user;
        this.post = post;
        this.content = requestDto.content();
    }

    public void update(CommentRequestDto requestDto){
        this.content = requestDto.content();
    }
}
