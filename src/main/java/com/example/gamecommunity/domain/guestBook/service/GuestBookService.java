package com.example.gamecommunity.domain.guestBook.service;

import com.example.gamecommunity.domain.guestBook.dto.CreateGuestBookDto;
import com.example.gamecommunity.domain.guestBook.dto.CreateGuestBookDto.Response;
import com.example.gamecommunity.domain.guestBook.entity.GuestBook;
import com.example.gamecommunity.domain.guestBook.repository.GuestBookRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestBookService {

  private final UserService userService;

  private final GuestBookRepository guestBookRepository;


  public Response createComment(
          Long toUserId,
          CreateGuestBookDto.Request createGuestBookDto,
          String fromUserEmail
  ) {

    User toUser = userService.findById(toUserId);
    User fromUser = userService.findByEmail(fromUserEmail);

    GuestBook guestBook = createGuestBookDto.toEntity(createGuestBookDto.content(), toUser, fromUser);

    GuestBook savedGuestBook = guestBookRepository.save(guestBook);

    return CreateGuestBookDto.Response.from(savedGuestBook);
  }
}
