package com.example.gamecommunity.domain.post.controller;

import com.example.gamecommunity.domain.post.service.PostLikeService;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.config.SecurityConfig.AuthenticationHelper;
import com.example.gamecommunity.global.response.ApiResponse;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/like")
@RequiredArgsConstructor
public class PostLikeController {

  private final PostLikeService postLikeService;

  private final AuthenticationHelper authenticationHelper;

  // 좋아요 싫어요 하기
  @PostMapping
  public ResponseEntity<?> addLike(
      @PathVariable Long postId,
      @RequestParam Boolean isLike,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    User loginUser = authenticationHelper.checkAuthentication(userDetails);

    postLikeService.addLike(postId, isLike,loginUser);
    String message;

    if (isLike) {
      message = "좋아요";
    } else {
      message = "싫어요";
    }

    return ResponseEntity.ok(ApiResponse.ok(message + " 성공", null));
  }

  // 좋아요 싫어요 취소하기
  @DeleteMapping
  public ResponseEntity<?> cancelLike(
      @PathVariable Long postId,
      @RequestParam Boolean isLike,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    User loginUser = userDetails.getUser();

    postLikeService.cancelLike(postId, isLike,loginUser);
    String message;

    if (isLike) {
      message = "좋아요";
    } else {
      message = "싫어요";
    }

    return ResponseEntity.ok(ApiResponse.ok(message + " 취소 성공", null));
  }
}
