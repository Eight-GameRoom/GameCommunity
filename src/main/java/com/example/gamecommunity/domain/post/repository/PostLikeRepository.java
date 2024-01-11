package com.example.gamecommunity.domain.post.repository;

import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.entity.PostLike;
import com.example.gamecommunity.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

  boolean existsByUserAndPost(User loginUser, Post post);

  PostLike findByUserAndIslikeAndPost(User loginUser, Boolean isLike, Post post);
}
