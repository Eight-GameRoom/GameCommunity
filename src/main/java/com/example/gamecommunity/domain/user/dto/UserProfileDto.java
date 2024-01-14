package com.example.gamecommunity.domain.user.dto;

import com.example.gamecommunity.domain.guest.entity.GuestBook;
import com.example.gamecommunity.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record UserProfileDto (

          String nickname,
          String introduction,
          String profile_url,
          List<GuestBookDto> guestBookList

  ) {

    public static UserProfileDto from(User user) {

      return UserProfileDto.builder()
              .nickname(user.getNickname())
              .introduction(user.getIntroduction())
              .profile_url(user.getProfileUrl())
              .guestBookList(user.getGuestBookList().stream().map(GuestBookDto::from).toList())
              .build();
    }

    @Builder
    private record GuestBookDto(Long id, String content, String nickname, LocalDateTime createdAt) {

      public static GuestBookDto from(GuestBook guestBook) {

        return GuestBookDto.builder()
                .id(guestBook.getId())
                .content(guestBook.getContent())
                .nickname(guestBook.getFromUser().getNickname())
                .createdAt(guestBook.getCreatedAt())
                .build();
      }
    }


  }
