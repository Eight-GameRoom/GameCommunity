package com.example.gamecommunity.domain.user.service;

import com.example.gamecommunity.domain.guestBook.repository.GuestBookRepository;
import com.example.gamecommunity.domain.user.dto.UserProfileDto;
import com.example.gamecommunity.domain.user.dto.UserProfileDto.Response;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.domain.user.repository.UserRepository;
import com.example.gamecommunity.global.exception.user.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

  private final UserRepository userRepository;
  private final GuestBookRepository guestBookRepository;

  @Transactional
  public Response getProfile(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
    return UserProfileDto.Response.builder()
            .nickname(user.getNickname())
            .introduction(user.getIntroduction())
            .profile_url(user.getProfileUrl())
            .guestBookList(guestBookRepository.findAllById(userId))
            .build();
  }

}
