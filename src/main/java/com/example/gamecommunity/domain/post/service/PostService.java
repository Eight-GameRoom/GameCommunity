package com.example.gamecommunity.domain.post.service;

import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.dto.PostResponseDto;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.global.exception.post.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public void createPost(
      PostRequestDto requestDto, GameType gameType, GameName gameName, BoardName boardName) {

    Post post = new Post(requestDto, gameType, gameName, boardName);

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

    Page<Post> postList = postRepository.findByGameTypeAndGameNameAndBoardName(type, game, board, pageable);

    return postList.map(PostResponseDto::fromEntity);
  }

  @Transactional
  public void updatePost(Long postId, PostRequestDto requestDto) {

    Post post = getFindPost(postId);

    post.update(requestDto);
  }

  @Transactional
  public void deletePost(Long postId) {

    Post post = getFindPost(postId);

    postRepository.delete(post);
  }

  private Post getFindPost(Long postId) {
    return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
  }

}

