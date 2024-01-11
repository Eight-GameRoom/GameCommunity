package com.example.gamecommunity.domain.post.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.entity.PostReport;
import com.example.gamecommunity.domain.post.repository.PostReportRepository;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.test.PostTest;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("게시글 신고 서비스 테스트")
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PostReportServiceTest implements PostTest {


  @InjectMocks
  private PostReportService postReportService;
  @Mock
  private PostReportRepository postReportRepository;
  @Mock
  private PostRepository postRepository;
  @Mock
  private PostService postService;

  // islike가 true : 좋아요, false : 싫어요

  @Test
  @DisplayName("게시글 신고 - 성공")
  void addReportTestSuccess() {

    // given
    Long postId = TEST_POST_ID;
    Post post = TEST_POST;
    User loginUser = TEST_ANOTHER_USER;

    given(postService.getFindPost(postId)).willReturn(post);

    given(postReportRepository.existsByUserAndPost(loginUser, post)).willReturn(false);

    // when
    postReportService.addReport(postId, loginUser);

    // then
    verify(postReportRepository, times(1)).save(any(PostReport.class));
    verify(postReportRepository, times(1)).existsByUserAndPost(any(User.class), any(Post.class));
    assertEquals(TEST_REPORT + 1, post.getPostLike());
  }

  @Test
  @DisplayName("게시글 신고 - 실패(신고 내역이 존재)")
  void addReportTestFailureDuplicatedLike() {

    // given
    Long postId = TEST_POST_ID;
    Post post = TEST_POST;
    User loginUser = TEST_ANOTHER_USER;

    given(postService.getFindPost(postId)).willReturn(post);
    // 내역이 이미 존재하도록 설정
    given(postReportRepository.existsByUserAndPost(loginUser, post)).willReturn(true);

    // when, then
    BusinessException ex = assertThrows(BusinessException.class, () -> {
      postReportService.addReport(postId, loginUser);
    });

    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    assertEquals(ErrorCode.DUPLICATED_REPORT_EXCEPTION.getMessage(), ex.getMessage());
  }
}