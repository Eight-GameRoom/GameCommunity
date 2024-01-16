package com.example.gamecommunity.domain.post.repository;

import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.entity.PostLike;
import com.example.gamecommunity.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

  boolean existsByUserAndPost(User loginUser, Post post);

  PostLike findByUserAndIslikeAndPost(User loginUser, Boolean isLike, Post post);

  @Modifying
  @Query(value = "delete from PostLike as pl where pl.user.id = :userId")
  void deleteUserCascadePostLike(@Param("userId")  Long userId);
}
