package com.example.gamecommunity.domain.post.service;

import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public void createPost(
      PostRequestDto requestDto, GameType gameType, GameName gameName, BoardName boardName) {

    Post post = new Post(requestDto, gameType, gameName, boardName);

    postRepository.save(post);
  }
}

