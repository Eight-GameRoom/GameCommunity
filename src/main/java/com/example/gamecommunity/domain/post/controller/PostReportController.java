package com.example.gamecommunity.domain.post.controller;

import com.example.gamecommunity.domain.post.service.PostReportService;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.config.SecurityConfig.AuthenticationHelper;
import com.example.gamecommunity.global.response.ApiResponse;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/report")
@RequiredArgsConstructor
public class PostReportController {

  private final PostReportService postReportService;

  private final AuthenticationHelper authenticationHelper;

  // 신고하기
  @PostMapping
  public ResponseEntity<?> addReport(
      @PathVariable Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    User loginUser = authenticationHelper.checkAuthentication(userDetails);

    postReportService.addReport(postId, loginUser);
    return ResponseEntity.ok(ApiResponse.ok("게시글 신고 성공", null));
  }

}


