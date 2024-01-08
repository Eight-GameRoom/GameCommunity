package com.example.gamecommunity.domain.post.controller;

import com.example.gamecommunity.domain.post.service.PostReportService;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/report")
@RequiredArgsConstructor
public class PostReportController {

  private final PostReportService postReportService;

  // 신고하기
  @PostMapping
  public ResponseEntity<?> addReport(@PathVariable Long postId) {

      postReportService.addReport(postId);
      return ResponseEntity.ok(ApiResponse.ok("게시글 신고 성공", null));
  }

}


