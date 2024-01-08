package com.example.gamecommunity.domain.user.dto;

import com.example.gamecommunity.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequestDto(

      @NotBlank(message = "이메일 공백일 수 없습니다.")
      @Pattern(regexp ="^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "이메일 형식에 맞게 적어 주세요")
      String email,

      @NotBlank(message = "닉네임 공백일 수 없습니다.")
      @Size(min=4, max=10,message ="닉네임 4자 이상 10자 이하이어야 합니다")
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
