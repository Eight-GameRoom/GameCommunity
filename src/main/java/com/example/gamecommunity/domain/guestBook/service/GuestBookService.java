package com.example.gamecommunity.domain.guestBook.service;

import com.example.gamecommunity.domain.guestBook.dto.CreateGuestBookDto;
import com.example.gamecommunity.domain.guestBook.entity.GuestBook;
import com.example.gamecommunity.domain.guestBook.repository.GuestBookRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.domain.user.service.UserService;
import com.example.gamecommunity.global.exception.guestbook.NotFoundGuestBookException;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GuestBookService {

  private final UserService userService;

  private final GuestBookRepository guestBookRepository;


  public void createComment(
          Long toUserId,
          CreateGuestBookDto.Request createGuestBookDto,
          UserDetailsImpl userDetails
  ) {

    User toUser = userService.findById(toUserId);
    User fromUser = userDetails.getUser();

    GuestBook guestBook = createGuestBookDto.toEntity(createGuestBookDto.content(), toUser, fromUser);

    guestBookRepository.save(guestBook);

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
