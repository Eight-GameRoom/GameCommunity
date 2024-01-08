package com.example.gamecommunity.domain.user.service;

import static com.example.gamecommunity.global.exception.common.ErrorCode.*;

import com.example.gamecommunity.domain.user.dto.SignupRequestDto;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.domain.user.repository.UserRepository;
import com.example.gamecommunity.global.exception.common.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void signup(SignupRequestDto requestDto) {
    emailCheck(requestDto.email());
    confirmPassword(requestDto);

    User user = requestDto.toEntity(passwordEncoder.encode(requestDto.password()),
        requestDto.imageName());

    userRepository.save(user);
  }

  public void emailCheck(String email) {
    if (userRepository.findByEmail(email).isPresent()) {
      throw new BusinessException(HttpStatus.CONFLICT, ALREADY_EXIST_USER_EMAIL_EXCEPTION);
    }
  }

  public void confirmPassword(SignupRequestDto requestDto) {
    if (!(requestDto.password().equals(requestDto.checkPassword()))) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, NOT_EQUALS_CONFIRM_PASSWORD);
    }
  }

}
