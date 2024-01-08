package com.example.gamecommunity.domain.user.controller;

import com.example.gamecommunity.domain.user.dto.LoginRequestDto;
import com.example.gamecommunity.domain.user.dto.PasswordChangeRequestDto;
import com.example.gamecommunity.domain.user.dto.SignupRequestDto;
import com.example.gamecommunity.domain.user.dto.TokenDto;
import com.example.gamecommunity.domain.user.service.UserService;
import com.example.gamecommunity.global.response.ApiResponse;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import com.example.gamecommunity.global.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  //로그인
  @PostMapping("/login")
  public ResponseEntity<ApiResponse> login(
      @Valid @RequestBody LoginRequestDto requestDto,
      HttpServletResponse response) {

    TokenDto tokenDto = userService.login(requestDto);

    jwtUtil.setTokenResponse(tokenDto, response);

    return ResponseEntity.ok(ApiResponse.ok("로그인 성공.", null));
  }

  // 비밀번호 변경
  @PutMapping("/password")
  public ResponseEntity<ApiResponse> updatePassword(
      @Valid @RequestBody PasswordChangeRequestDto requestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails){

    userService.updatePassword(requestDto, userDetails.getUser().getId());

    return ResponseEntity.ok(ApiResponse.ok("비밀번호 변경 성공.", null));
  }


}
