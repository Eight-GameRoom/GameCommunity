package com.example.gamecommunity.domain.post.service;

import static com.example.gamecommunity.global.exception.common.ErrorCode.DUPLICATED_LIKE_EXCEPTION;
import static com.example.gamecommunity.global.exception.common.ErrorCode.SELF_LIKE_EXCEPTION;

import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.entity.PostLike;
import com.example.gamecommunity.domain.post.repository.PostLikeRepository;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.exception.common.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

  private final PostLikeRepository postLikeRepository;

  private final PostService postService;

  private final PostRepository postRepository;

  public void addLike(Long postId, Boolean isLike, User loginUser) {

    Post post = postService.getFindPost(postId);

    // 현재 로그인한 유저와 게시글의 생성자가 같다면 좋아요 또는 싫어요 불가능
    if (loginUser.getId().equals(post.getUser().getId())) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, SELF_LIKE_EXCEPTION);
    }

    // 좋아요 또는 싫어요 내역이 있으면 불가능
    if (postLikeRepository.existsByUserAndPost(loginUser, post)) {
      throw new BusinessException(HttpStatus.BAD_REQUEST, DUPLICATED_LIKE_EXCEPTION);
    }

    PostLike postLike = PostLike.fromUserAndPost(loginUser, isLike, post);

    postLikeRepository.save(postLike);

    // 좋아요 또는 싫어요 수 증가
    updatePostLikeCount(post, isLike);

    postRepository.save(post);
  }
  private void updatePostLikeCount(Post post, Boolean isLike) {
    if (isLike) {
      post.setPostLike(post.getPostLike() + 1);
    } else {
      post.setPostUnlike(post.getPostUnlike() + 1);
    }
  }
}
