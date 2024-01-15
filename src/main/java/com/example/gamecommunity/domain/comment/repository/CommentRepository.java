package com.example.gamecommunity.domain.comment.repository;

import com.example.gamecommunity.domain.comment.entity.Comment;
import com.example.gamecommunity.domain.post.entity.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPost(Post post, Pageable pageable);

    Optional<Comment> findByCommentId(Long commentId);
}
