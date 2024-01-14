package com.example.gamecommunity.domain.admin.service;

import static com.example.gamecommunity.domain.test.PostTest.TEST_GAME_NAME;
import static com.example.gamecommunity.domain.test.PostTest.TEST_GAME_TYPE;
import static com.example.gamecommunity.domain.test.PostTest.TEST_POST_CONTENT;
import static com.example.gamecommunity.domain.test.PostTest.TEST_POST_TITLE;
import static com.example.gamecommunity.domain.test.PostTest.TEST_USER;
import static com.example.gamecommunity.domain.test.UserTest.TEST_ADMIN_USER;
import static com.example.gamecommunity.domain.test.UserTest.TEST_USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.gamecommunity.domain.admin.dto.UserBlockRequestDto;
import com.example.gamecommunity.domain.enums.board.BoardName;
import com.example.gamecommunity.domain.enums.game.name.GameName;
import com.example.gamecommunity.domain.enums.game.type.GameType;
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
  private UserDetailsImpl userDetails;

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
  void makeAdminUser() {
    userDetails = new UserDetailsImpl(TEST_ADMIN_USER);
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
    given(userRepository.findAll()).willReturn(users);

    // when
    var ret = adminService.getUsers(userDetails);

    // then
    assertEquals(ret.size(), users.size());
  }

  @Test
  void getUser() {
    // given
    given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

    // when
    var ret = adminService.getUser(userDetails, user.getId());

    // then
    assertEquals(ret.email(), user.getEmail());
  }

  @Test
  void deleteUser() {
    // given
    given(userRepository.findById(TEST_USER_ID)).willReturn(Optional.of(TEST_USER));

    adminService.deleteUser(userDetails, TEST_USER_ID);

    verify(userRepository, times(1)).deleteById(TEST_USER_ID);
  }

  @Test
  void setBlock() {
    // given
    LocalDateTime now = LocalDateTime.now();
    Instant instant = now.toInstant(ZoneOffset.UTC);
    user.setBlockDate(instant);
    given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

    UserBlockRequestDto userBlockRequestDto = new UserBlockRequestDto(user.getId(), now);

    // when
    adminService.setBlock(userDetails, userBlockRequestDto);
    var ret = adminService.getUser(userDetails, user.getId());

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
    given(postRepository.findAll()).willReturn(posts);

    // when
    var ret = adminService.getReportedPosts(userDetails);

    // then
    assertEquals(ret.get(0).report(), report_cnt);
  }

  @Test
  void getReportedPost() {
    // given
    int report_cnt = 12;
    post.setReport(report_cnt);
    given(postRepository.findById(1L)).willReturn(Optional.of(post));

    // when
    var ret = adminService.getReportedPost(userDetails, 1L);

    // then
    assertEquals(ret.report(), report_cnt);
  }

  @Test
  void writeNotice() {
    // given
    PostRequestDto requestDto = new PostRequestDto(TEST_POST_TITLE, TEST_POST_CONTENT);

    // when
    adminService.writeNotice(userDetails, requestDto, TEST_GAME_TYPE, TEST_GAME_NAME);

    // Then
    verify(postRepository, times(1)).save(any(Post.class));
  }
}