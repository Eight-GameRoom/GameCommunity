package com.example.gamecommunity.domain.post.service;

import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.repository.PostReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReportService {

  private final PostReportRepository postReportRepository;

  private final PostService postService;

  public void addReport(Long postId) {

    Post post = postService.getFindPost(postId);

    // 현재 로그인한 아이디로 신고 기록이 있는 경우 예외발생
  }
}
