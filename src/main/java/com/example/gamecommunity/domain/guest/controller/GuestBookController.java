package com.example.gamecommunity.domain.guest.controller;

import com.example.gamecommunity.domain.guest.dto.CreateGuestBookDto;
import com.example.gamecommunity.domain.guest.dto.ModifyGuestBookDto;
import com.example.gamecommunity.domain.guest.service.GuestBookService;
import com.example.gamecommunity.global.response.ApiResponse;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

  // 방명록 댓글 작성
  @PostMapping("/{toUserId}/guestbooks")
  public ResponseEntity<ApiResponse> createComment(
          @PathVariable Long toUserId,
          @RequestBody @Valid CreateGuestBookDto.Request createGuestBookDto,
          @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    guestBookService.createComment(toUserId, createGuestBookDto, userDetails);

    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("방명록 작성 성공", null));
  }

  // 방명록 댓글 수정
  @PatchMapping("/{toUserId}/guestbooks/{guestbookId}")
  public ResponseEntity<ApiResponse> modifyComment(
          @PathVariable Long guestbookId,
          @RequestBody @Valid ModifyGuestBookDto modifyGuestBookDto
  ) {
    guestBookService.modifyComment(guestbookId, modifyGuestBookDto.content());

    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("방명록 수정 성공", null));
  }

  // 방명록 댓글 삭제
  @DeleteMapping("/{toUserId}/guestbooks/{guestbookId}")
  public ResponseEntity<ApiResponse> deleteComment(
          @PathVariable Long guestbookId
  ) {
    guestBookService.deleteComment(guestbookId);

    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("방명록 삭제 성공", null));
  }


}
