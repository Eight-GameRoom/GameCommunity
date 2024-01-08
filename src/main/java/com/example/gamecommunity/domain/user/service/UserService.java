package com.example.gamecommunity.domain.user.service;

import static com.example.gamecommunity.global.exception.common.ErrorCode.ALREADY_EXIST_USER_EMAIL_EXCEPTION;
import static com.example.gamecommunity.global.exception.common.ErrorCode.FAILED_AUTHENTICATION_EXCEPTION;
import static com.example.gamecommunity.global.exception.common.ErrorCode.NOT_EQUALS_CONFIRM_PASSWORD;
import static com.example.gamecommunity.global.exception.common.ErrorCode.NOT_FOUND_USER_EXCEPTION;

import com.example.gamecommunity.domain.user.dto.LoginRequestDto;
import com.example.gamecommunity.domain.user.dto.PasswordChangeRequestDto;
import com.example.gamecommunity.domain.user.dto.SignupRequestDto;
import com.example.gamecommunity.domain.user.dto.TokenDto;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.domain.user.repository.UserRepository;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import com.example.gamecommunity.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private final JwtUtil jwtUtil;

  public void signup(SignupRequestDto requestDto) {
    emailCheck(requestDto.email());
    confirmPassword(requestDto);

    User user = requestDto.toEntity(passwordEncoder.encode(requestDto.password()),
        requestDto.imageName());

    userRepository.save(user);
  }

  public TokenDto login(LoginRequestDto requestDto) {

    String email = requestDto.email();
    String password = requestDto.password();

    User user = userRepository.findByEmail(email)
        .orElseThrow(
            () -> new BusinessException(HttpStatus.BAD_REQUEST, NOT_FOUND_USER_EXCEPTION));


    Authentication authentication = createAuthentication(password, user);
    setAuthentication(authentication);

    TokenDto tokenDto = TokenDto.of(jwtUtil.createAccessToken(email),
        jwtUtil.createRefreshToken(email));

    return tokenDto;
  }

  @Transactional
  public void updatePassword(PasswordChangeRequestDto requestDto, Long userId) {
    String password = passwordEncoder.encode(requestDto.newPassword());

    User user = userRepository.findById(userId).orElseThrow(() ->
        new BusinessException(HttpStatus.NOT_FOUND,NOT_FOUND_USER_EXCEPTION)
    );

    //로그인중 유저 패스워드랑 request에 담긴 변경전 패스워드랑 같은지 체크
    if (!(passwordEncoder.matches(requestDto.nowPassword(), user.getPassword()))) {
      throw new BusinessException(HttpStatus.BAD_REQUEST,FAILED_AUTHENTICATION_EXCEPTION );
    }

    //변경할 비번,비번 확인 같은지 체크
    else if (!(requestDto.newPassword().equals(requestDto.checkPassword()))) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, NOT_EQUALS_CONFIRM_PASSWORD);
    }

    user.updatePassword(password);
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


  private Authentication createAuthentication(String password, User user) {
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, FAILED_AUTHENTICATION_EXCEPTION);
    }

    UserDetailsImpl userDetails = new UserDetailsImpl(user);
    return new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(),
        userDetails.getAuthorities());
  }

  private void setAuthentication(Authentication authentication) {
    SecurityContext context = SecurityContextHolder.getContext();
    context.setAuthentication(authentication);
  }



}
