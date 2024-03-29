package com.example.gamecommunity.domain.post.service;

import static com.example.gamecommunity.domain.user.entity.UserRoleEnum.ADMIN;

import com.example.gamecommunity.domain.enums.board.BoardName;
import com.example.gamecommunity.domain.enums.game.name.GameName;
import com.example.gamecommunity.domain.enums.game.type.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.dto.PostResponseDto;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.config.SecurityConfig.AuthenticationHelper;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
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

  private final AuthenticationHelper authenticationHelper;

  @Transactional
  public void createPost(
      PostRequestDto requestDto, GameType gameType, GameName gameName, BoardName boardName,
      MultipartFile file, UserDetailsImpl userDetails) throws IOException {

    User loginUser = authenticationHelper.checkAuthentication(userDetails);

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

    Page<Post> postList = postRepository
        .findByGameTypeAndGameNameAndBoardName(type, game, board, pageable);

    return postList.map(PostResponseDto::fromEntity);
  }

  @Transactional
  public void updatePost(
      Long postId, PostRequestDto requestDto, MultipartFile file, UserDetailsImpl userDetails)
      throws IOException {

    Post post = getAuthenticationPost(postId, userDetails);

    String imageUrl = post.getPostImageUrl();

    // 파일이 존재하는 경우에만 이미지 업로드를 수행
    if (file != null && !file.isEmpty()) {
      imageUrl = postImageUploadService.uploadFile(file);
    }

    post.update(requestDto, imageUrl);
  }

  @Transactional
  public void deletePost(Long postId, UserDetailsImpl userDetails) {

    Post post = getAuthenticationPost(postId, userDetails);

    postRepository.delete(post);
  }

  // 게시글 가져오는 메서드
  public Post getFindPost(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND,
            ErrorCode.NOT_FOUND_POST_EXCEPTION));
  }

  // 인증된 게시글 가져오는 메서드
  private Post getAuthenticationPost(Long postId, UserDetailsImpl userDetails) {
    User loginUser = authenticationHelper.checkAuthentication(userDetails);

    Post post = getFindPost(postId);

    boolean isAdmin = loginUser.getRole().equals(ADMIN);

    // 로그인한 유저가 게시글 작성자나 관리자가 아니면 수정 불가
    if (!isAdmin && !loginUser.getNickname().equals(post.getPostAuthor())) {
      throw new BusinessException(HttpStatus.UNAUTHORIZED,
          ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }
    return post;
  }
  @Transactional
  public void deleteUserCascadePost(Long userId){

    postRepository.deleteUserCascadePost(userId);
  }

}

