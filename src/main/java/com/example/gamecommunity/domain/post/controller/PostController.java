package com.example.gamecommunity.domain.post.controller;

import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.dto.PostResponseDto;
import com.example.gamecommunity.domain.post.service.PostService;
import com.example.gamecommunity.global.common.CommonResponseDto;
import com.example.gamecommunity.global.exception.common.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  // 게시글 작성
  @PostMapping
  public ResponseEntity<?> createPost(
      @RequestBody PostRequestDto requestDto,
      @RequestParam GameType gameType,
      @RequestParam(required = false) GameName gameName,
      @RequestParam BoardName boardName) {

    postService.createPost(requestDto, gameType, gameName, boardName);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CommonResponseDto("게시글 생성 성공"));
  }

  // 게시글 단건 조회
  @GetMapping("/{postId}")
  public ResponseEntity<?> getPost(@PathVariable Long postId) {

    try {
      PostResponseDto responseDto = postService.getPost(postId);
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(new CommonResponseDto("게시글 단건 조회 성공", responseDto));
    } catch (BusinessException e) {
      return ResponseEntity.status(e.getStatus())
          .body(new CommonResponseDto(e.getMessage()));
    }
  }

  // 게시글 페이징 조회
  @GetMapping
  public ResponseEntity<?> getPosts(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortKey") String sortKey,
      @RequestParam("isAsc") boolean isAsc) {

    Page<PostResponseDto> responseDtoPage = postService.getPosts(
        page - 1, size, sortKey, isAsc);

    CommonResponseDto responseDto = CommonResponseDto.builder()
        .message("게시글 페이징 조회 성공")
        .data(responseDtoPage)
        .build();

    return ResponseEntity.ok(responseDto);
  }

}
