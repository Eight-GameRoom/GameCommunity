package com.example.gamecommunity.domain.guestBook.service;

import com.example.gamecommunity.domain.guestBook.dto.CreateGuestBookDto;
import com.example.gamecommunity.domain.guestBook.dto.CreateGuestBookDto.Response;
import com.example.gamecommunity.domain.guestBook.entity.GuestBook;
import com.example.gamecommunity.domain.guestBook.repository.GuestBookRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.domain.user.service.UserService;
import com.example.gamecommunity.global.exception.guestbook.NotFoundGuestBookException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  public void modifyComment(Long guestbookId, String content) {

    GuestBook guestBook = guestBookRepository.findById(guestbookId).orElseThrow(
            NotFoundGuestBookException::new);

    guestBook.modifyComment(content);

  }

  @Transactional
  public void deleteComment(Long guestbookId) {

    GuestBook guestBook = guestBookRepository.findById(guestbookId).orElseThrow(
            NotFoundGuestBookException::new);

    guestBookRepository.delete(guestBook);
  }





}
