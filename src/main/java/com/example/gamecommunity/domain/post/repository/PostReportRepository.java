package com.example.gamecommunity.domain.post.repository;

import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.entity.PostReport;
import com.example.gamecommunity.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {

  boolean existsByUserAndPost(User loginUser, Post post);
  @Modifying
  @Query(value = "delete from PostReport as pr where pr.user.id = :userId")
  void deleteUserCascadePostReport(Long userId);
}
