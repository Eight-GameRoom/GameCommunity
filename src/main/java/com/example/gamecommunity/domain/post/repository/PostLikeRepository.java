package com.example.gamecommunity.domain.post.repository;

import com.example.gamecommunity.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

}
