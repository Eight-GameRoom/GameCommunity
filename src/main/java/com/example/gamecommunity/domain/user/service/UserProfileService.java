package com.example.gamecommunity.domain.user.service;

import com.example.gamecommunity.domain.user.dto.UserProfileDto;
import com.example.gamecommunity.domain.user.entity.User;
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
  public void modifyProfile(Long userId, String introduction, String profile_url) {

    User user = userService.findById(userId);

    user.modifyProfile(introduction, profile_url);
  }

}
