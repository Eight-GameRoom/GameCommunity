package com.example.gamecommunity.domain.post.controller;

import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.service.PostService;
import com.example.gamecommunity.global.common.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  // 게시글 작성하기
  @PostMapping()
  public ResponseEntity<?> createPost(
      @RequestBody PostRequestDto requestDto,
      @RequestParam GameType gameType,
      @RequestParam(required = false) GameName gameName,
      @RequestParam BoardName boardName) {

    postService.createPost(requestDto, gameType, gameName, boardName);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CommonResponseDto("게시글 생성 성공"));
  }

}
