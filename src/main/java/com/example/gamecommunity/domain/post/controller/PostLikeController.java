package com.example.gamecommunity.domain.post.controller;

import com.example.gamecommunity.domain.post.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/like")
@RequiredArgsConstructor
public class PostLikeController {

  private final PostLikeService postLikeService;


}
