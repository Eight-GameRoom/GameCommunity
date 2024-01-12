package com.example.gamecommunity.domain.comment.entity;

import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.auditing.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Post post;

    public Comment(User user, Post post, String content){
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public void update(String content){
        this.content = content;
    }
}
