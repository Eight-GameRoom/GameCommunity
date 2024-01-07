package com.example.gamecommunity.domain.post.service;

import com.example.gamecommunity.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

  private final PostLikeRepository postLikeRepository;

}
