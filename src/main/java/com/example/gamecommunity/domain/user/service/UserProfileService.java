package com.example.gamecommunity.domain.user.service;

import com.example.gamecommunity.domain.user.dto.UserProfileDto;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
      throw new BusinessException(HttpStatus.UNAUTHORIZED, ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }
    user.modifyProfile(introduction, profile_url);
  }


}
