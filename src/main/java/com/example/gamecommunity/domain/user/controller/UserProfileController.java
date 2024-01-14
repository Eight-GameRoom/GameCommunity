package com.example.gamecommunity.domain.user.controller;

import com.example.gamecommunity.domain.user.dto.UserProfileDto.Response;
import com.example.gamecommunity.domain.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/profile")
public class UserProfileController {

  private final UserProfileService userProfileService;

  @GetMapping("/{userId}")
  public ResponseEntity<Response> getProfile(
          @PathVariable Long userId
  ) {
    return ResponseEntity.ok().body(userProfileService.getProfile(userId));
  }

}
