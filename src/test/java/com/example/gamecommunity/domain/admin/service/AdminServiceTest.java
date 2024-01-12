package com.example.gamecommunity.domain.admin.service;

import static com.example.gamecommunity.domain.test.PostTest.TEST_GAME_NAME;
import static com.example.gamecommunity.domain.test.PostTest.TEST_GAME_TYPE;
import static com.example.gamecommunity.domain.test.PostTest.TEST_POST_CONTENT;
import static com.example.gamecommunity.domain.test.PostTest.TEST_POST_TITLE;
import static com.example.gamecommunity.domain.test.PostTest.TEST_USER;
import static com.example.gamecommunity.domain.test.UserTest.TEST_USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.gamecommunity.domain.admin.dto.UserBlockRequestDto;
import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.domain.user.repository.UserRepository;
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
import org.springframework.web.multipart.MultipartFile;

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
    given(userRepository.findAll()).willReturn(users);

    // when
    var ret = adminService.getUsers();

    // then
    assertEquals(ret.size(), users.size());
  }

  @Test
  void getUser() {
    // given
    given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

    // when
    var ret = adminService.getUser(user.getId());

    // then
    assertEquals(ret.email(), user.getEmail());
  }

  @Test
  void deleteUser() {
    // given
    given(userRepository.findById(TEST_USER_ID)).willReturn(Optional.of(TEST_USER));

    adminService.deleteUser(TEST_USER_ID);

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
    given(postRepository.findAll()).willReturn(posts);

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
    given(postRepository.findById(1L)).willReturn(Optional.of(post));

    // when
    var ret = adminService.getReportedPost(1L);

    // then
    assertEquals(ret.report(), report_cnt);
  }

  @Test
  void writeNotice() {
    // given
    PostRequestDto requestDto = new PostRequestDto(TEST_POST_TITLE, TEST_POST_CONTENT);
    MultipartFile file = mock(MultipartFile.class);
    User loginUser = TEST_USER;

    // when
    adminService.writeNotice(requestDto, TEST_GAME_TYPE, TEST_GAME_NAME, loginUser);

    // Then
    verify(postRepository, times(1)).save(any(Post.class));
  }
}