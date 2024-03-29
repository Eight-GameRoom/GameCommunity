package com.example.gamecommunity.domain.guest.service;

import com.example.gamecommunity.domain.guest.dto.CreateGuestBookDto;
import com.example.gamecommunity.domain.guest.entity.GuestBook;
import com.example.gamecommunity.domain.guest.repository.GuestBookRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.domain.user.service.UserService;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import com.example.gamecommunity.global.exception.guestbook.NotFoundGuestBookException;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
  public void modifyComment(Long guestbookId, String content, UserDetailsImpl userDetails) {

    GuestBook guestBook = guestBookRepository.findById(guestbookId).orElseThrow(
            NotFoundGuestBookException::new);

    if (!guestBook.getFromUser().getId().equals(userDetails.getId())) {
      throw new BusinessException(HttpStatus.UNAUTHORIZED, ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }

    guestBook.modifyComment(content);

  }

  @Transactional
  public void deleteComment(Long guestbookId, UserDetailsImpl userDetails) {

    GuestBook guestBook = guestBookRepository.findById(guestbookId).orElseThrow(
            NotFoundGuestBookException::new);

    if (!guestBook.getFromUser().getId().equals(userDetails.getId())) {
      throw new BusinessException(HttpStatus.UNAUTHORIZED, ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }

    guestBookRepository.delete(guestBook);
  }





}
