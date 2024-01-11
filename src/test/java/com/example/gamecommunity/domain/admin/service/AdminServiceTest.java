package com.example.gamecommunity.domain.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.gamecommunity.domain.admin.dto.UserBlockRequestDto;
import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.domain.user.repository.UserRepository;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private PostRepository postRepository;
  @InjectMocks
  private AdminService adminService;

  private User user;
  private Post post;
  @BeforeEach
  void makeUser() {
    user = User.builder()
        .id(1L)
        .email("test@test.com")
        .password("1234")
        .nickname("test")
        .build();
  }

  @BeforeEach
  void makePost() {
    post = Post.builder()
        .postId(1L)
        .postAuthor("test")
        .postContent("test")
        .postTitle("test")
        .boardName(BoardName.NOTICE_BOARD)
        .gameName(GameName.BRAWL_STARS)
        .gameType(GameType.PC_GAME)
        .postImageUrl("")
        .user(user)
        .build();
  }

  @Test
  void getUsers() {
    // given
    List<User> users = new ArrayList<>();
    users.add(user);
    users.add(user);
    users.add(user);
    when(userRepository.findAll()).thenReturn(users);

    // when
    var ret = adminService.getUsers();

    // then
    assertEquals(ret.size(), users.size());
  }

  @Test
  void getUser() {
    // given
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    // when
    var ret = adminService.getUser(user.getId());

    // then
    assertEquals(ret.email(), user.getEmail());
  }

  @Test
  void deleteUser() {

  }

  @Test
  void setBlock() {
    // given
    LocalDateTime now = LocalDateTime.now();
    Instant instant = now.toInstant(ZoneOffset.UTC);
    user.setBlockDate(instant);
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    UserBlockRequestDto userBlockRequestDto = new UserBlockRequestDto(user.getId(), now);

    // when
    adminService.setBlock(userBlockRequestDto);
    var ret = adminService.getUser(user.getId());

    // then
    assertEquals(ret.blockDate(), instant);
  }

  @Test
  void getReportedPosts() {
    // given
    int report_cnt = 11;

    List<Post> posts = new ArrayList<>();
    posts.add(post);
    post.setReport(report_cnt);
    posts.add(post);
    posts.add(post);
    when(postRepository.findAll()).thenReturn(posts);

    // when
    var ret = adminService.getReportedPosts();

    // then
    assertEquals(ret.get(0).report(), report_cnt);
  }

  @Test
  void getReportedPost() {
    // given
    int report_cnt = 12;
    post.setReport(report_cnt);
    when(postRepository.findById(1L)).thenReturn(Optional.of(post));

    // when
    var ret = adminService.getReportedPost(1L);

    // then
    assertEquals(ret.report(), report_cnt);
  }

  @Test
  void writeNotice() {
    // given
    PostRequestDto postRequestDto = new PostRequestDto(post.getPostTitle(), post.getPostContent());
    UserDetailsImpl userDetails = new UserDetailsImpl(user);
    when(postRepository.save(post)).thenReturn(post);

    // when
    adminService.writeNotice(postRequestDto,post.getGameType(),post.getGameName(),userDetails);

    // then
  }
}