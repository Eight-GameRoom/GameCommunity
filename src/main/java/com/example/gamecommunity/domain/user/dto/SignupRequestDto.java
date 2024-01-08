package com.example.gamecommunity.domain.user.dto;

import com.example.gamecommunity.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
public record SignupRequestDto(

      @NotBlank(message = "이메일 공백일 수 없습니다.")
      String email,

      @NotBlank(message = "닉네임 공백일 수 없습니다.")
      String nickname,

      @NotBlank(message = "비밀번호 공백일 수 없습니다.")
      String password,

      @NotBlank(message = "비밀번호 확인 공백일 수 없습니다.")
      String checkPassword,

      String imageName,

      String introduction){

    public User toEntity(String passwordEncoder,String imageName){
      return User.builder()
          .email(email)
          .nickname(nickname)
          .password(passwordEncoder)
          .introduction(introduction)
          .profileUrl(imageName)
          .build();
    }


}
