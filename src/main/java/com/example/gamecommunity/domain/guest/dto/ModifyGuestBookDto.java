package com.example.gamecommunity.domain.guest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ModifyGuestBookDto (
  @NotBlank @Size(max = 5000) String content
  ) {

}
