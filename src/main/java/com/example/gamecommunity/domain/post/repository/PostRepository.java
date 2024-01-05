package com.example.gamecommunity.domain.post.repository;

import com.example.gamecommunity.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
