package com.example.gamecommunity.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CommonResponseDto {

  private String message;
  private Object data;

  public CommonResponseDto(String message) {
    this.message = message;
  }

  public CommonResponseDto(String message, Object data) {
    this.message = message;
    this.data = data;
  }
}

