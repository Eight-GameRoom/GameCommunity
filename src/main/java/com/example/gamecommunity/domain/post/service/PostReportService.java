package com.example.gamecommunity.domain.post.service;

import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.entity.PostReport;
import com.example.gamecommunity.domain.post.repository.PostReportRepository;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostReportService {

  private final PostReportRepository postReportRepository;

  private final PostService postService;

  private final PostRepository postRepository;

  @Transactional
  public void addReport(Long postId, User loginUser) {

    Post post = postService.getFindPost(postId);

    // 현재 로그인한 아이디로 신고 기록이 있는 경우 예외발생
    if (postReportRepository.existsByUserAndPost(loginUser, post)) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATED_REPORT_EXCEPTION);
    }

    PostReport postReport = PostReport.fromUserAndPost(loginUser, post);

    postReportRepository.save(postReport);

    post.setReport(post.getReport() + 1);

    postRepository.save(post);
  }
  @Transactional
  public void deleteUserCascadePostReport(Long userId){

    postReportRepository.deleteUserCascadePostReport(userId);
  }
}
