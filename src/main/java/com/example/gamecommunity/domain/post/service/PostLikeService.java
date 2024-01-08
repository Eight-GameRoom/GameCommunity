package com.example.gamecommunity.domain.post.service;

import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

  private final PostLikeRepository postLikeRepository;

  private final PostService postService;

  public void addLike(Long postId) {

    Post post = postService.getFindPost(postId);

    // 현재 로그인한 유저와 포스트의 author가 같다면 좋아요 또는 싫어요 불가능
  }
}
