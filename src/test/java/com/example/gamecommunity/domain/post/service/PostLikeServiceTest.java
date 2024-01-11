package com.example.gamecommunity.domain.post.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.entity.PostLike;
import com.example.gamecommunity.domain.post.repository.PostLikeRepository;
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

@DisplayName("게시글 좋아요 서비스 테스트")
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PostLikeServiceTest implements PostTest {

  @InjectMocks
  private PostLikeService postLikeService;
  @Mock
  private PostLikeRepository postLikeRepository;
  @Mock
  private PostRepository postRepository;
  @Mock
  private PostService postService;

  // islike가 true : 좋아요, false : 싫어요

  @Test
  @DisplayName("게시글 좋아요 또는 싫어요 - 성공")
  void addLikeTestSuccess() {

    // given
    Long postId = TEST_POST_ID;
    Post post = TEST_POST;
    User loginUser = TEST_ANOTHER_USER;
    Boolean islike = true;

    given(postService.getFindPost(postId)).willReturn(post);

    given(postLikeRepository.existsByUserAndPost(loginUser, post)).willReturn(false);

    // when
    postLikeService.addLike(postId, islike, loginUser);

    // then
    verify(postLikeRepository, times(1)).save(any(PostLike.class));
    verify(postRepository, times(1)).save(post);
    assertEquals(TEST_POST_LIKE + 1, post.getPostLike());
  }

  @Test
  @DisplayName("게시글 좋아요 또는 싫어요 - 실패(자신의 글)")
  void addLikeTestFailureSelfLike() {

    // given
    Long postId = TEST_POST_ID;
    Post post = TEST_POST;
    User loginUser = TEST_USER;
    Boolean islike = true;

    given(postService.getFindPost(postId)).willReturn(post);

    // when, then
    BusinessException ex = assertThrows(BusinessException.class, () -> {
      postLikeService.addLike(postId, islike, loginUser);
    });

    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    assertEquals(ErrorCode.SELF_LIKE_EXCEPTION.getMessage(), ex.getMessage());
  }

  @Test
  @DisplayName("게시글 좋아요 또는 싫어요 - 실패(좋아요 또는 싫어요 내역이 존재)")
  void addLikeTestFailureDuplicatedLike() {

    // given
    Long postId = TEST_POST_ID;
    Post post = TEST_POST;
    User loginUser = TEST_ANOTHER_USER;
    Boolean islike = true;

    given(postService.getFindPost(postId)).willReturn(post);
    // 내역이 이미 존재하도록 설정
    given(postLikeRepository.existsByUserAndPost(loginUser, post)).willReturn(true);

    // when, then
    BusinessException ex = assertThrows(BusinessException.class, () -> {
      postLikeService.addLike(postId, islike, loginUser);
    });

    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    assertEquals(ErrorCode.DUPLICATED_LIKE_EXCEPTION.getMessage(), ex.getMessage());
  }

  @Test
  @DisplayName("게시글 좋아요 또는 싫어요 취소 - 성공")
  void cancelLikeTestSuccess() {

    // given
    Long postId = TEST_POST_ID;
    Post post = TEST_ANOTHER_POST;
    User loginUser = TEST_USER;
    Boolean islike = true;

    PostLike postLike = new PostLike(1L, islike, post, loginUser);

    given(postService.getFindPost(postId)).willReturn(post);
    given(postLikeRepository.findByUserAndIslikeAndPost(loginUser, islike, post))
        .willReturn(postLike);

    // when
    assertDoesNotThrow(() -> postLikeService.cancelLike(postId, islike, loginUser));

    // then
    verify(postLikeRepository, times(1)).delete(postLike);
    verify(postRepository, times(1)).save(post);
    assertEquals(TEST_POST_LIKE - 1, post.getPostLike());
  }

}