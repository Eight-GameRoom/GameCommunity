package com.example.gamecommunity.domain.admin.controller;

import com.example.gamecommunity.domain.admin.dto.NoticeRequestDto;
import com.example.gamecommunity.domain.admin.dto.UserBlockRequestDto;
import com.example.gamecommunity.domain.admin.service.AdminService;
import com.example.gamecommunity.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  private AdminService adminService;

  @GetMapping("/users")
  public ResponseEntity<ApiResponse> getUsers(
//      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    var users = adminService.getUsers();
    return ResponseEntity.ok(ApiResponse.ok("유저 정보 목록", users));
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<ApiResponse> getUser(@PathVariable long userId) {
    return ResponseEntity.ok(ApiResponse.ok("", null));
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<ApiResponse> deleteUser(@PathVariable long userId) {
    return ResponseEntity.ok(ApiResponse.ok("", null));
  }

  @PatchMapping("/users/block")
  public ResponseEntity<ApiResponse> setBlock(
      @RequestBody UserBlockRequestDto userBlockRequestDto) {
    return ResponseEntity.ok(ApiResponse.ok("", null));
  }

  @GetMapping("/posts/report")
  public ResponseEntity<ApiResponse> getReportedPosts() {
    return ResponseEntity.ok(ApiResponse.ok("", null));
  }

  @GetMapping("/posts/report/{postId}")
  public ResponseEntity<ApiResponse> getReportedPost(@PathVariable long postId) {
    return ResponseEntity.ok(ApiResponse.ok("", null));
  }

  @PostMapping("/notices")
  public ResponseEntity<ApiResponse> writeNotice(@RequestBody NoticeRequestDto noticeRequestDto) {
    return ResponseEntity.ok(ApiResponse.ok("", null));
  }
}
