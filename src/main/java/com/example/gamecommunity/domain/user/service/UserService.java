package com.example.gamecommunity.domain.user.service;

import static com.example.gamecommunity.global.exception.common.ErrorCode.*;


import com.example.gamecommunity.domain.user.dto.LoginRequestDto;
import com.example.gamecommunity.domain.user.dto.PasswordChangeRequestDto;
import com.example.gamecommunity.domain.user.dto.SignupRequestDto;
import com.example.gamecommunity.domain.user.dto.TokenDto;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.domain.user.repository.UserRepository;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import com.example.gamecommunity.global.util.JwtUtil;
import com.example.gamecommunity.global.util.RandomNumber;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
  private final JavaMailSender mailSender;

  private final JwtUtil jwtUtil;

  @Value("${spring.mail.username}")
  private String senderEmail;

  public void signup(SignupRequestDto requestDto) {
    emailCheck(requestDto.email());
    confirmPassword(requestDto);

    User user = requestDto.toEntity(passwordEncoder.encode(requestDto.password()),
        requestDto.imageName());

    userRepository.save(user);
  }

  public void emailSend(String toEmail) {
    //JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
    MimeMessage message = mailSender.createMimeMessage();
    String authNumber = RandomNumber.createNumber();
    String content = "인증 번호:"+authNumber;

    try {
      // true를 전달하여 multipart 형식의 메시지를 지원, "utf-8"을 전달하여 문자 인코딩을 설정
      MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");

      helper.setFrom(this.senderEmail);//이메일의 발신자 주소 설정
      helper.setTo(toEmail);//이메일의 수신자 주소 설정
      helper.setSubject("게임 커뮤니티 회원 가입 인증 이메일");//이메일의 제목을 설정
      helper.setText(content,true);//이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
      mailSender.send(message);
    } catch (MessagingException e) {//이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
      throw  new BusinessException(HttpStatus.BAD_REQUEST,FAILED_EMAIL_SEND_EXCEPTION);
    }
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
      throw new BusinessException(HttpStatus.BAD_REQUEST,FAILED_AUTHENTICATION_EXCEPTION);
    }

    //변경할 비번,비번 확인 같은지 체크
    else if (!(requestDto.newPassword().equals(requestDto.checkPassword()))) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, NOT_EQUALS_CONFIRM_PASSWORD_EXCEPTION);
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
      throw new BusinessException(HttpStatus.BAD_REQUEST, NOT_EQUALS_CONFIRM_PASSWORD_EXCEPTION);
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
