package com.example.gamecommunity.domain.comment.repository;

import com.example.gamecommunity.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllById(Long postId);

    @Modifying
    @Query(value = "delete from Comment as c where c.user.id = :userId")
    void deleteUserCascadeComment(Long userId);
}
