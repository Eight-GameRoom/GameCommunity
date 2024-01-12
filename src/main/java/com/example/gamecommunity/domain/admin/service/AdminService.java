package com.example.gamecommunity.domain.admin.service;

import static java.util.stream.Collectors.toList;

import com.example.gamecommunity.domain.admin.dto.AdminUserResponseDto;
import com.example.gamecommunity.domain.admin.dto.NoticeRequestDto;
import com.example.gamecommunity.domain.admin.dto.UserBlockRequestDto;
import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.dto.PostResponseDto;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.user.repository.UserRepository;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final UserRepository userRepository;
  private final PostRepository postRepository;

  public List<AdminUserResponseDto> getUsers() {
    return userRepository.findAll().stream().map(AdminUserResponseDto::new).toList();
  }

  public AdminUserResponseDto getUser(long userId) {
    var user = userRepository.findById(userId).orElseThrow(
        () -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_EXCEPTION)
    );

    return new AdminUserResponseDto(user);
  }

  public void deleteUser(long userId) {
    userRepository.deleteById(userId);
  }

  @Transactional
  public void setBlock(UserBlockRequestDto userBlockRequestDto) {
    var userId = userBlockRequestDto.userId();
    var blockDate = userBlockRequestDto.blockDate();

    var user = userRepository.findById(userId).orElseThrow(
        () -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_EXCEPTION)
    );

    user.setBlockDate(blockDate.toInstant(ZoneOffset.UTC));
  }

  public List<PostResponseDto> getReportedPosts() {
    return postRepository.findAll().stream()
        .filter(p -> p.getReport() > 0)
        .map(PostResponseDto::fromEntity).toList();
  }

  public PostResponseDto getReportedPost(long postId) {
    var post = postRepository.findById(postId).orElseThrow(
        () -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_POST_EXCEPTION)
    );

    return PostResponseDto.fromEntity(post);
  }

  @Transactional
  public void writeNotice(
      PostRequestDto requestDto,
      GameType gameType,
      GameName gameName,
      UserDetailsImpl userDetails
  ) {
    Post post = new Post(requestDto, gameType, gameName, BoardName.NOTICE_BOARD, "",
        userDetails.getUser());
    postRepository.save(post);
  }
}
