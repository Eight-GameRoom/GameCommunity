package com.example.gamecommunity.domain.post.controller;

import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.dto.PostResponseDto;
import com.example.gamecommunity.domain.post.service.PostService;
import com.example.gamecommunity.domain.user.controller.UserController;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.config.SecurityConfig.AuthenticationHelper;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import com.example.gamecommunity.global.response.ApiResponse;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import java.io.IOException;
import java.net.BindException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  private final AuthenticationHelper authenticationHelper;

  // 게시글 작성
  @PostMapping
  public ResponseEntity<?> createPost(
      @RequestPart(value = "requestDto") PostRequestDto requestDto,
      @RequestPart(value = "file", required = false) MultipartFile file,
      @RequestParam(required = false) GameType gameType,
      @RequestParam(required = false) GameName gameName,
      @RequestParam BoardName boardName,
      @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

    User loginUser = authenticationHelper.checkAuthentication(userDetails);

    postService.createPost(requestDto, gameType, gameName, boardName, file, loginUser);
    return ResponseEntity.ok(ApiResponse.ok("게시글 작성 성공", null));
  }

  // 게시글 단건 조회
  @GetMapping("/{postId}")
  public ResponseEntity<?> getPost(@PathVariable Long postId) {

    PostResponseDto responseDto = postService.getPost(postId);
    return ResponseEntity.ok(ApiResponse.ok(postId + "번 게시글 조회 성공", responseDto));
  }

  // 게시글 페이징 조회
  @GetMapping
  public ResponseEntity<?> getPosts(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam("sortKey") String sortKey,
      @RequestParam("isAsc") boolean isAsc,
      @RequestParam(name = "type", required = false) GameType type,
      @RequestParam(name = "game", required = false) GameName game,
      @RequestParam(name = "board") BoardName board) {

    Page<PostResponseDto> responseDtoPage = postService.getPosts(
        page - 1, size, sortKey, isAsc, type, game, board);

    return ResponseEntity.ok(ApiResponse.ok("게시글 페이징 조회 성공", responseDtoPage));
  }

  // 게시글 수정
  @PatchMapping("/{postId}")
  public ResponseEntity<?> updatePost(
      @PathVariable Long postId,
      @RequestPart(value = "requestDto") PostRequestDto requestDto,
      @RequestPart(value = "file", required = false) MultipartFile file,
      @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

    User loginUser = authenticationHelper.checkAuthentication(userDetails);

    postService.updatePost(postId, requestDto, file, loginUser);
    return ResponseEntity.ok(ApiResponse.ok("게시글 수정 성공", null));
  }

  // 게시글 삭제
  @DeleteMapping("/{postId}")
  public ResponseEntity<?> deletePost(
      @PathVariable Long postId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    User loginUser = authenticationHelper.checkAuthentication(userDetails);

    postService.deletePost(postId, loginUser);
    return ResponseEntity.ok(ApiResponse.ok("게시글 삭제 성공", null));
  }

}
