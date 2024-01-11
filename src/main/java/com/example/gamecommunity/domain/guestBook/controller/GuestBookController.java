package com.example.gamecommunity.domain.guestBook.controller;

import com.example.gamecommunity.domain.guestBook.dto.CreateGuestBookDto;
import com.example.gamecommunity.domain.guestBook.service.GuestBookService;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class GuestBookController {

  private final GuestBookService guestBookService;

  @PostMapping("/{toUserId}/guestbooks")
  public ResponseEntity<CreateGuestBookDto.Response> createComment(
          @PathVariable Long toUserId,
          @RequestBody @Valid CreateGuestBookDto.Request createGuestBookDto,
          @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    CreateGuestBookDto.Response responseDto = guestBookService.createComment(toUserId, createGuestBookDto, userDetails.getUsername());

    return ResponseEntity.ok(responseDto);
  }


}
