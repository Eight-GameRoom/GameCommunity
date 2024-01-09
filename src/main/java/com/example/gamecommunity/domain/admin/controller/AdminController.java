package com.example.gamecommunity.domain.admin.controller;

import com.example.gamecommunity.domain.admin.dto.NoticeRequestDto;
import com.example.gamecommunity.domain.admin.dto.UserBlockRequestDto;
import com.example.gamecommunity.domain.admin.service.AdminService;
import com.example.gamecommunity.global.response.ApiResponse;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
  public ResponseEntity<ApiResponse> getUsers(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    var usersDto = adminService.getUsers();
    return ResponseEntity.ok(ApiResponse.ok("유저 정보 목록 조회 성공", usersDto));
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<ApiResponse> getUser(@PathVariable long userId) {
    var userDto = adminService.getUser(userId);
    return ResponseEntity.ok(ApiResponse.ok("유저 정보 조회 성공", userDto));
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<ApiResponse> deleteUser(@PathVariable long userId) {
    adminService.deleteUser(userId);
    return ResponseEntity.ok(ApiResponse.ok("유저 삭제 성공", null));
  }

  @PatchMapping("/users/block")
  public ResponseEntity<ApiResponse> setBlock(
      @RequestBody UserBlockRequestDto userBlockRequestDto) {
    adminService.setBlock(userBlockRequestDto);
    return ResponseEntity.ok(ApiResponse.ok("유저 차단 성공", null));
  }

  @GetMapping("/posts/report")
  public ResponseEntity<ApiResponse> getReportedPosts() {
    var postsDto = adminService.getReportedPosts();
    return ResponseEntity.ok(ApiResponse.ok("신고 게시물 목록 조회 성공", postsDto));
  }

  @GetMapping("/posts/report/{postId}")
  public ResponseEntity<ApiResponse> getReportedPost(@PathVariable long postId) {
    var postDto = adminService.getReportedPost(postId);
    return ResponseEntity.ok(ApiResponse.ok("신고 게시물 조회 성공", postDto));
  }

  @PostMapping("/notices")
  public ResponseEntity<ApiResponse> writeNotice(@RequestBody NoticeRequestDto noticeRequestDto) {
    adminService.writeNotice(noticeRequestDto);
    return ResponseEntity.ok(ApiResponse.ok("공지사항 작성 성공", null));
  }
}
