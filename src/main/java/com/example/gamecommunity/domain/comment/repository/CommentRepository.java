package com.example.gamecommunity.domain.comment.repository;

import com.example.gamecommunity.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllById(Long postId);
}
