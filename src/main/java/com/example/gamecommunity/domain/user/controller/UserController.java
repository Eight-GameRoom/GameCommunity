package com.example.gamecommunity.domain.user.controller;

import com.example.gamecommunity.domain.user.dto.SignupRequestDto;
import com.example.gamecommunity.domain.user.service.UserService;
import com.example.gamecommunity.global.response.ApiResponse;
import com.example.gamecommunity.global.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final JwtUtil jwtUtil;

  // 회원가입
  @PostMapping("/signup")
  public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignupRequestDto requestDto) {
    userService.signup(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("회원가입 성공.", null));
  }



}
