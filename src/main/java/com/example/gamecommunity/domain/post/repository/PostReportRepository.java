package com.example.gamecommunity.domain.post.repository;

import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.entity.PostReport;
import com.example.gamecommunity.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {

  boolean existsByUserAndPost(User loginUser, Post post);

}
