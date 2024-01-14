package com.example.gamecommunity.domain.guest.service;

import com.example.gamecommunity.domain.guest.dto.CreateGuestBookDto;
import com.example.gamecommunity.domain.guest.entity.GuestBook;
import com.example.gamecommunity.domain.guest.repository.GuestBookRepository;
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
  public void modifyComment(Long guestbookId, String content, UserDetailsImpl userDetails) {

    GuestBook guestBook = guestBookRepository.findById(guestbookId).orElseThrow(
            NotFoundGuestBookException::new);

    if (!guestBook.getFromUser().getId().equals(userDetails.getId())) {
      throw new IllegalArgumentException("자신이 작성한 방명록 댓글만 수정 가능합니다.");
    }

    guestBook.modifyComment(content);

  }

  @Transactional
  public void deleteComment(Long guestbookId, UserDetailsImpl userDetails) {

    GuestBook guestBook = guestBookRepository.findById(guestbookId).orElseThrow(
            NotFoundGuestBookException::new);

    if (!guestBook.getFromUser().getId().equals(userDetails.getId())) {
      throw new IllegalArgumentException("자신이 작성한 방명록 댓글만 삭제 가능합니다.");
    }

    guestBookRepository.delete(guestBook);
  }





}
