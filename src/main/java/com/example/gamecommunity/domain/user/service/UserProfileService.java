package com.example.gamecommunity.domain.user.service;

import com.example.gamecommunity.domain.user.dto.UserProfileDto;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

  private final UserService userService;

  @Transactional
  public UserProfileDto getProfile(Long userId) {
    User user = userService.findById(userId);

    return UserProfileDto.from(user);
  }

  @Transactional
  public void modifyProfile(
          Long userId,
          String introduction,
          String profile_url,
          UserDetailsImpl userDetails) {

    User user = userService.findById(userId);
    if (!userId.equals(userDetails.getId())) {
      throw new IllegalArgumentException("자신의 프로필만 수정 가능합니다.");
    }
    user.modifyProfile(introduction, profile_url);
  }


}
