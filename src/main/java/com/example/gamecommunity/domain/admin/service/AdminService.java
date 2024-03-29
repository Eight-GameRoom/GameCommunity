package com.example.gamecommunity.domain.admin.service;

import com.example.gamecommunity.domain.admin.dto.AdminUserResponseDto;
import com.example.gamecommunity.domain.admin.dto.UserBlockRequestDto;
import com.example.gamecommunity.domain.enums.board.BoardName;
import com.example.gamecommunity.domain.enums.game.name.GameName;
import com.example.gamecommunity.domain.enums.game.type.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.dto.PostResponseDto;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.user.entity.UserRoleEnum;
import com.example.gamecommunity.domain.user.repository.UserRepository;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import jakarta.transaction.Transactional;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final UserRepository userRepository;
  private final PostRepository postRepository;

  public List<AdminUserResponseDto> getUsers(UserDetailsImpl userDetails) {
    if (userDetails.getUser().getRole() != UserRoleEnum.ADMIN) {
      throw new BusinessException(HttpStatus.BAD_REQUEST,
          ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }

    return userRepository.findAll().stream().map(AdminUserResponseDto::new).toList();
  }

  public AdminUserResponseDto getUser(UserDetailsImpl userDetails, long userId) {
    if (userDetails.getUser().getRole() != UserRoleEnum.ADMIN) {
      throw new BusinessException(HttpStatus.BAD_REQUEST,
          ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }

    var user = userRepository.findById(userId).orElseThrow(
        () -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_EXCEPTION)
    );

    return new AdminUserResponseDto(user);
  }

  public void deleteUser(UserDetailsImpl userDetails, long userId) {
    if (userDetails.getUser().getRole() != UserRoleEnum.ADMIN) {
      throw new BusinessException(HttpStatus.BAD_REQUEST,
          ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }

    userRepository.findById(userId).orElseThrow(
        () -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_EXCEPTION)
    );

    userRepository.deleteById(userId);
  }

  @Transactional
  public void setBlock(UserDetailsImpl userDetails, UserBlockRequestDto userBlockRequestDto) {
    if (userDetails.getUser().getRole() != UserRoleEnum.ADMIN) {
      throw new BusinessException(HttpStatus.BAD_REQUEST,
          ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }

    var userId = userBlockRequestDto.userId();
    var blockDate = userBlockRequestDto.blockDate();

    var user = userRepository.findById(userId).orElseThrow(
        () -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_EXCEPTION)
    );

    user.setBlockDate(blockDate.toInstant(ZoneOffset.UTC));
  }

  public List<PostResponseDto> getReportedPosts(UserDetailsImpl userDetails) {
    if (userDetails.getUser().getRole() != UserRoleEnum.ADMIN) {
      throw new BusinessException(HttpStatus.BAD_REQUEST,
          ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }

    return postRepository.findAll().stream()
        .filter(p -> p.getReport() > 0)
        .map(PostResponseDto::fromEntity).toList();
  }

  public PostResponseDto getReportedPost(UserDetailsImpl userDetails, long postId) {
    if (userDetails.getUser().getRole() != UserRoleEnum.ADMIN) {
      throw new BusinessException(HttpStatus.BAD_REQUEST,
          ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }

    var post = postRepository.findById(postId).orElseThrow(
        () -> new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_POST_EXCEPTION)
    );

    return PostResponseDto.fromEntity(post);
  }

  @Transactional
  public void writeNotice(
      UserDetailsImpl userDetails,
      PostRequestDto requestDto,
      GameType gameType,
      GameName gameName
  ) {
    if (userDetails.getUser().getRole() != UserRoleEnum.ADMIN) {
      throw new BusinessException(HttpStatus.BAD_REQUEST,
          ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION);
    }

    Post post = new Post(requestDto, gameType, gameName, BoardName.NOTICE_BOARD, "",
        userDetails.getUser());
    postRepository.save(post);
  }
}
