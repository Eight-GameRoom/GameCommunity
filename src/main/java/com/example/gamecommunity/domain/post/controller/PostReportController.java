package com.example.gamecommunity.domain.post.controller;

import com.example.gamecommunity.domain.post.service.PostReportService;
import com.example.gamecommunity.global.common.CommonResponseDto;
import com.example.gamecommunity.global.exception.common.BusinessException;
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

    try {
      postReportService.addReport(postId);
      return ResponseEntity.ok()
          .body(new CommonResponseDto("게시글 신고 성공"));
    } catch (BusinessException e) {
      return ResponseEntity.status(e.getStatus())
          .body(new CommonResponseDto(e.getMessage()));
    }
  }

}


