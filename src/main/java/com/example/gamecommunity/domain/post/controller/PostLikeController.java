package com.example.gamecommunity.domain.post.controller;

import com.example.gamecommunity.domain.post.service.PostLikeService;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

  // 좋아요 싫어요 하기
  @PostMapping
  public ResponseEntity<?> addLike(
      @PathVariable Long postId,
      @RequestParam Boolean isLike) {

    postLikeService.addLike(postId);
    return ResponseEntity.ok(ApiResponse.ok("좋아요 또는 싫어요 성공", null));
  }

}
