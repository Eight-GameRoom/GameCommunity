package com.example.gamecommunity.domain.admin.controller;

import com.example.gamecommunity.domain.admin.dto.NoticeRequestDto;
import com.example.gamecommunity.domain.admin.dto.UserBlockRequestDto;
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

  @GetMapping("/users")
  public ResponseEntity<String> getUsers(
//      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    return ResponseEntity.ok("");
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<String> getUser(@PathVariable long userId) {
    return ResponseEntity.ok("");
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable long userId) {
    return ResponseEntity.ok("");
  }

  @PatchMapping("/users/block")
  public ResponseEntity<String> setBlock(@RequestBody UserBlockRequestDto userBlockRequestDto) {
    return ResponseEntity.ok("");
  }

  @GetMapping("/posts/report")
  public ResponseEntity<String> getReportedPosts() {
    return ResponseEntity.ok("");
  }

  @GetMapping("/posts/report/{postId}")
  public ResponseEntity<String> getReportedPost(@PathVariable long postId) {
    return ResponseEntity.ok("");
  }

  @PostMapping("/notices")
  public ResponseEntity<String> writeNotice(@RequestBody NoticeRequestDto noticeRequestDto) {
    return ResponseEntity.ok("");
  }
}
