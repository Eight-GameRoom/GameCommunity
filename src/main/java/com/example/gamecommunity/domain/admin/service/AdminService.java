package com.example.gamecommunity.domain.admin.service;

import com.example.gamecommunity.domain.admin.dto.AdminUserResponseDto;
import com.example.gamecommunity.domain.admin.dto.NoticeRequestDto;
import com.example.gamecommunity.domain.admin.dto.UserBlockRequestDto;
import com.example.gamecommunity.domain.post.dto.PostResponseDto;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.user.repository.UserRepository;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final UserRepository userRepository;
  //private final PostRepository postRepository;

  public List<AdminUserResponseDto> getUsers() {
    return userRepository.findAll().stream().map(u ->
        AdminUserResponseDto.builder()
            .email(u.getEmail())
            .introduction(u.getIntroduction())
            .ranking(u.getRanking())
            .nickname(u.getNickname())
            .profileUrl(u.getProfileUrl())
            .build()
    ).toList();
  }

  public AdminUserResponseDto getUser(long userId) {
    var u = userRepository.findById(userId).orElseThrow(
        () -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_EXCEPTION)
    );

    return AdminUserResponseDto.builder()
        .email(u.getEmail())
        .introduction(u.getIntroduction())
        .ranking(u.getRanking())
        .nickname(u.getNickname())
        .profileUrl(u.getProfileUrl())
        .build();
  }

  public void deleteUser(long userId) {
    userRepository.deleteById(userId);
  }

  @Transactional
  public void setBlock(UserBlockRequestDto userBlockRequestDto) {
    var userId = userBlockRequestDto.userId();
    var blockDate = userBlockRequestDto.blockDate();

    var u = userRepository.findById(userId).orElseThrow(
        () -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_EXCEPTION)
    );

    u.setBlockDate(blockDate);
  }

  public List<PostResponseDto> getReportedPosts() {
//    postRepository.findAll().stream().map(p->
//        AdminPostResponseDto.builder()
//            .postTitle(p.)
//    )
    return null;
  }

  public PostResponseDto getReportedPost(long postId) {
    return null;
  }

  @Transactional
  public void writeNotice(NoticeRequestDto noticeRequestDto) {
    Post post = null;//Post.builder()
    //postRepository.save(post);
  }
}
