package com.example.gamecommunity.domain.user.dto;

import com.example.gamecommunity.domain.guestBook.entity.GuestBook;
import java.util.List;
import lombok.Builder;

public class UserProfileDto {

  @Builder
  public record Response(
          String nickname,
          String introduction,
          String profile_url,
          List<GuestBook> guestBookList

  ) {

  }



}
