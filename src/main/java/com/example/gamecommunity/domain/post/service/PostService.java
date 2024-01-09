package com.example.gamecommunity.domain.post.service;

import static com.example.gamecommunity.global.exception.common.ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION;
import static com.example.gamecommunity.global.exception.common.ErrorCode.POST_NOT_FOUND_EXCEPTION;

import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.dto.PostResponseDto;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.exception.common.BusinessException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  private final PostImageUploadService postImageUploadService;

  public void createPost(
      PostRequestDto requestDto,
      GameType gameType,
      GameName gameName,
      BoardName boardName,
      MultipartFile file, User loginUser) throws IOException {

    String imageUrl = null;

    // 파일이 존재하는 경우에만 이미지 업로드를 수행
    if (file != null && !file.isEmpty()) {
      imageUrl = postImageUploadService.uploadFile(file);
    }

    // 게시글 생성
    Post post = new Post(
        requestDto, gameType, gameName, boardName, imageUrl, loginUser);

    postRepository.save(post);
  }

  public PostResponseDto getPost(Long postId) {

    Post post = getFindPost(postId);

    return PostResponseDto.fromEntity(post);
  }

  public Page<PostResponseDto> getPosts(
      int page, int size, String sortKey, boolean isAsc,
      GameType type, GameName game, BoardName board) {
    // 페이징 및 정렬처리
    Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sort = Sort.by(direction, sortKey);
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Post> postList = postRepository.findByGameTypeAndGameNameAndBoardName(type, game, board,
        pageable);

    return postList.map(PostResponseDto::fromEntity);
  }

  @Transactional
  public void updatePost(
      Long postId, PostRequestDto requestDto, MultipartFile file, User loginuser) throws IOException {

    Post post = getFindPost(postId);

    // 로그인한 유저와 게시글 작성자와 일치하는지 확인
    if (!loginuser.getId().equals(post.getPostId())) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, AUTHENTICATION_MISMATCH_EXCEPTION);
    }

    String imageUrl = post.getPostImageUrl();

    // 파일이 존재하는 경우에만 이미지 업로드를 수행
    if (file != null && !file.isEmpty()) {
      imageUrl = postImageUploadService.uploadFile(file);
    }

    post.update(requestDto, imageUrl);
  }

  @Transactional
  public void deletePost(Long postId) {

    Post post = getFindPost(postId);

    postRepository.delete(post);
  }

  public Post getFindPost(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new BusinessException(HttpStatus.BAD_REQUEST, POST_NOT_FOUND_EXCEPTION));
  }

}

