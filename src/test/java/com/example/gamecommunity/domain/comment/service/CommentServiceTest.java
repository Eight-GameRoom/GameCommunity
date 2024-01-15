package com.example.gamecommunity.domain.comment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.gamecommunity.domain.comment.dto.CommentRequestDto;
import com.example.gamecommunity.domain.comment.dto.CommentResponseDto;
import com.example.gamecommunity.domain.comment.repository.CommentRepository;
import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.dto.PostResponseDto;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.comment.entity.Comment;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.post.service.PostImageUploadService;
import com.example.gamecommunity.domain.post.service.PostService;
import com.example.gamecommunity.domain.test.CommentTest;
import com.example.gamecommunity.domain.test.PostTest;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@DisplayName("댓글 서비스 테스트")
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CommentServiceTest implements CommentTest {

  @InjectMocks
  private CommentService commentService;
  @Mock
  private CommentRepository commentRepository;
  @Mock
  private PostRepository postRepository;

  @Test
  @DisplayName("댓글 작성 - 성공")
  void createCommentTestSuccess() {

    // given
    CommentRequestDto commentRequestDto1 = new CommentRequestDto(TEST_COMMENT_CONTENT);
    CommentRequestDto commentRequestDto2 = new CommentRequestDto(TEST_COMMENT_CONTENT);
    Post post = TEST_POST;
    Long postId = TEST_POST_ID;
    User loginUser = TEST_USER;

    given(postRepository.findById(postId)).willReturn(Optional.of(post));

    // when
    commentService.createComment(loginUser, post.getPostId(), commentRequestDto1);

    // Then
    verify(commentRepository, times(1)).save(any(Comment.class));
  }

  @Test
  @DisplayName("게시글 페이징 조회 - 성공")
  void getPostsTestSuccess() {

    // given
    CommentRequestDto commentRequestDto1 = new CommentRequestDto(TEST_COMMENT_CONTENT);
    CommentRequestDto commentRequestDto2 = new CommentRequestDto(TEST_COMMENT_CONTENT);
    Post post = TEST_POST;
    Long postId = TEST_POST_ID;
    User loginUser = TEST_USER;

    // given
    int page = 1;
    int size = 10;
    String sortKey = "createdAt";
    boolean isAsc = true;

    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortKey));

    List<Comment> comments = Arrays.asList(TEST_COMMENT, TEST_ANOTHER_COMMENT);

    Page<Comment> commentPage = new PageImpl<>(comments, pageable, comments.size());

    given(postRepository.findByPostId(postId)).willReturn(Optional.of(post));
    given(commentRepository.findAllByPost(post, pageable)).willReturn(commentPage);

    // when
    Page<CommentResponseDto> result = commentService.getComments(page, size, sortKey, isAsc,
        postId);

    // then
    assertEquals(commentPage.getTotalElements(), result.getTotalElements());
    assertEquals(commentPage.getContent().size(), result.getContent().size());
  }

  @Test
  @DisplayName("댓글 수정 - 성공")
  void updateCommentTestSuccess() {

    // given
    User loginUser = TEST_USER;
    CommentRequestDto commentRequestDto = new CommentRequestDto(TEST_ANOTHER_COMMENT_CONTENT);
    Long commentId = TEST_COMMENT_ID;
    Comment comment = TEST_COMMENT;

    given(commentRepository.findByCommentId(commentId)).willReturn(Optional.of(comment));

    // when
    commentService.updateComment(loginUser, commentId, commentRequestDto);

    // then
    assertEquals(TEST_ANOTHER_COMMENT_CONTENT, comment.getContent());
  }

//  @Test
//  @DisplayName("게시글 수정 - 실패(로그한 유저가 게시글 작성자가 아님")
//  void updatePostTestFailureNotAuth() throws IOException {
//
//    // given
//    Long postId = TEST_POST_ID;
//    Post post = TEST_POST;
//    User loginUser = TEST_ANOTHER_USER;
//    MultipartFile file = mock(MultipartFile.class);
//
//    PostRequestDto requestDto = new PostRequestDto(
//        TEST_ANOTHER_POST.getPostTitle(), TEST_ANOTHER_POST.getPostContent());
//
//    given(postRepository.findById(postId)).willReturn(Optional.of(post));
//
//    // when, then
//    BusinessException ex = assertThrows(BusinessException.class, () -> {
//      postService.updatePost(postId, requestDto, file, loginUser);
//    });
//
//    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
//    assertEquals(ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION.getMessage(), ex.getMessage());
//  }
//
//  @Test
//  @DisplayName("게시글 삭제 - 성공")
//  void deletePostTestSuccess() {
//
//    // given
//    Long postId = TEST_POST_ID;
//    Post post = TEST_POST;
//    User loginUser = TEST_USER;
//
//    given(postRepository.findById(postId)).willReturn(Optional.of(post));
//
//    // when
//    postService.deletePost(postId, loginUser);
//
//    // then
//    verify(postRepository, times(1)).delete(post);
//  }
//
//  @Test
//  @DisplayName("게시글 삭제 - 실패(로그한 유저가 게시글 작성자가 아님")
//  void deletePostTestFailureNotAuth() {
//
//    // given
//    Long postId = TEST_POST_ID;
//    Post post = TEST_POST;
//    User loginUser = TEST_ANOTHER_USER;
//
//    given(postRepository.findById(postId)).willReturn(Optional.of(post));
//
//    // when, then
//    BusinessException ex = assertThrows(BusinessException.class, () -> {
//      postService.deletePost(postId, loginUser);
//    });
//
//    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
//    assertEquals(ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION.getMessage(), ex.getMessage());
//  }

}