package com.example.gamecommunity.domain.comment.controller;

import com.example.gamecommunity.domain.comment.dto.CommentRequestDto;
import com.example.gamecommunity.domain.comment.dto.CommentResponseDto;
import com.example.gamecommunity.domain.comment.service.CommentService;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.response.ApiResponse;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long postId,
      @RequestBody CommentRequestDto commentRequestDto
  ) {
    User user = userDetails.getUser();
    CommentResponseDto commentResponseDto = commentService.createComment(user, postId,
        commentRequestDto);
    return ResponseEntity.ok(ApiResponse.ok("댓글 생성 성공", null));
  }

  @PutMapping("/{commentId}")
  public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long commentId,
      @RequestBody CommentRequestDto commentRequestDto
  ) {
    User user = userDetails.getUser();
    CommentResponseDto commentResponseDto = commentService.updateComment(user, commentId,
        commentRequestDto);

    return ResponseEntity.ok(ApiResponse.ok("댓글 수정 성공", null));
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<ApiResponse<Void>> deleteComment(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long commentId
  ) {
    User user = userDetails.getUser();
    commentService.deleteComment(user, commentId);

    return ResponseEntity.ok(ApiResponse.ok("댓글 삭제 성공", null));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<CommentResponseDto>>> getComments(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortBy") String sortBy,
      @RequestParam("isAsc") boolean isAsc,
      @PathVariable Long postId) {
    Page<CommentResponseDto> commentResponseDtos = commentService.getComments(page-1, size,sortBy,isAsc,postId);

    return ResponseEntity.ok(ApiResponse.ok("댓글 조회 성공", commentResponseDtos));
  }
}
