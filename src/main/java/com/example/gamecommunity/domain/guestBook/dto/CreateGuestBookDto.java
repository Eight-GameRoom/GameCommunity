package com.example.gamecommunity.domain.guestBook.dto;

import com.example.gamecommunity.domain.guestBook.entity.GuestBook;
import com.example.gamecommunity.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;

public class CreateGuestBookDto {

  public record Request(
          @NotBlank @Size(max = 50) String content
  ) {

    public GuestBook toEntity(String content, User toUser, User fromUser) {
      return GuestBook.builder()
              .content(content)
              .toUser(toUser)
              .fromUser(fromUser)
              .build();
    }
  }

  @Builder
  public record Response(Long guestbookId,
                         String content,
                         String nickname,
                         LocalDateTime createdAt) {

    public static Response from(GuestBook guestBook) {
      return Response.builder()
              .guestbookId(guestBook.getId())
              .content(guestBook.getContent())
              .nickname(guestBook.getFromUser().getNickname())
              .createdAt(guestBook.getCreatedAt())
              .build();
    }

  }


}
