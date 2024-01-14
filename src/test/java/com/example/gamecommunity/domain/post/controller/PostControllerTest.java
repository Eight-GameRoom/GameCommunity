package com.example.gamecommunity.domain.post.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.gamecommunity.domain.enums.board.BoardName;
import com.example.gamecommunity.domain.enums.game.name.GameName;
import com.example.gamecommunity.domain.enums.game.type.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.post.service.PostService;
import com.example.gamecommunity.domain.test.ControllerTest;
import com.example.gamecommunity.domain.test.PostTest;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@DisplayName("게시글 컨트롤러 테스트")
@WebMvcTest(PostController.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class PostControllerTest extends ControllerTest implements PostTest {

  @MockBean
  private PostService postService;

  @MockBean
  private PostRepository postRepository;

  @DisplayName("게시글 생성 - 성공")
  @Test
  @WithMockUser(authorities = {"USER"})
  void createPostSuccess() throws Exception {

    // given
    MockMultipartFile file = new MockMultipartFile(
        "file",
        "test-image.jpg",
        "image/jpeg",
        "uploadFile".getBytes(StandardCharsets.UTF_8));

    PostRequestDto requestDto = TEST_REQUEST_DTO;
    MockMultipartFile request = new MockMultipartFile(
        "requestDto", null, "application/json",
        objectMapper.writeValueAsString(requestDto).getBytes(StandardCharsets.UTF_8));

    // when
    var action = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/posts")
        .file(file)
        .file(request)
        .param("gameType", "PC_GAME")
        .param("gameName", "VALORANT")
        .param("boardName", "FREE_BOARD")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .accept(MediaType.APPLICATION_JSON));

    // then
    action.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print());
    verify(postService, times(1)).createPost(
        any(PostRequestDto.class), eq(GameType.PC_GAME),
        eq(GameName.VALORANT), eq(BoardName.FREE_BOARD),
        eq(file), any(UserDetailsImpl.class));
  }

  // 댓글까지 보여줘야하므로 일단 미완성
  @Nested
  @DisplayName("게시글 단건 조회")
  class getPost {

    @DisplayName("성공")
    @Test
    @WithMockUser(authorities = {"USER"})
    void getPostSuccess() throws Exception {

      // given
      given(postService.getPost(eq(TEST_POST_ID))).willReturn(TEST_RESPONSE_DTO);

      // when
      var action = mockMvc.perform(get("/api/posts/{postId}", TEST_POST_ID)
          .accept(MediaType.APPLICATION_JSON));

      // then
      action
          .andExpect(status().isOk())
          .andExpect(jsonPath("$['data']['postTitle']").value(TEST_POST_TITLE))
          .andExpect(jsonPath("$['data']['postContent']").value(TEST_POST_CONTENT))
          .andDo(print());
    }

    @DisplayName("실패 - 게시글을 찾을 수 없음")
    @Test
    @WithMockUser(authorities = {"USER"})
    void getPostFailure() throws Exception {

      // given
      given(postService.getPost(eq(TEST_POST_ID))).willThrow(
          new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_POST_EXCEPTION));

      // when
      var action = mockMvc.perform(get("/api/posts/{postId}", TEST_POST_ID)
          .accept(MediaType.APPLICATION_JSON));

      // then
      action
          .andExpect(status().isNotFound()).andDo(print());
    }
  }

  // 댓글까지 보여줘야하므로 일단 미완성
  @Test
  @DisplayName("게시글 페이징 조회")
  @WithMockUser(authorities = {"USER"})
  void getPosts() throws Exception {

    // given
    int page = 1;
    int size = 10;
    String sortKey = "createdAt";
    boolean isAsc = true;
    GameType type = TEST_GAME_TYPE;
    GameName game = TEST_GAME_NAME;
    BoardName board = TEST_BOARD_NAME;

    // when
    var action = mockMvc.perform(get("/api/posts")
        .param("page", String.valueOf(page))
        .param("size", String.valueOf(size))
        .param("sortKey", sortKey)
        .param("isAsc", String.valueOf(isAsc))
        .param("type", type.name())
        .param("game", game.name())
        .param("board", board.name())
        .accept(MediaType.APPLICATION_JSON));

    // then
    action.andExpect(status().isOk()).andDo(print());
  }

/*  @Nested
  @DisplayName("게시글 수정")
  class updatePost {

    @DisplayName("성공")
    @Test
    @WithMockUser(authorities = {"USER"})
    @Disabled
    void updatePostSuccess() throws Exception {

      // given
      MockMultipartFile file = new MockMultipartFile(
          "file",
          "test-image.jpg",
          "image/jpeg",
          "uploadFile".getBytes(StandardCharsets.UTF_8));

      PostRequestDto requestDto = new PostRequestDto(TEST_ANOTHER_POST.getPostTitle(), TEST_ANOTHER_POST.getPostContent());
      MockMultipartFile request = new MockMultipartFile(
          "requestDto", null, "application/json",
          objectMapper.writeValueAsString(requestDto).getBytes(StandardCharsets.UTF_8));

      given(postService.updatePost(TEST_POST_ID, requestDto, file, new UserDetailsImpl(PostTest.TEST_USER)))
          .willReturn(null);

      // when
      var action = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/posts/{postId}")
          .file(file)
          .file(request)
          .contentType(MediaType.MULTIPART_FORM_DATA)
          .accept(MediaType.APPLICATION_JSON));

      // then
      action.andExpect(status().isOk()).andDo(print());

    }

    @DisplayName("실패 - 자신의 글만 수정")
    @Test
    @WithMockUser(authorities = {"USER"})
    void updatePostFailure() throws Exception {

      // given
      MockMultipartFile file = new MockMultipartFile(
          "file",
          "test-image.jpg",
          "image/jpeg",
          "uploadFile".getBytes(StandardCharsets.UTF_8));

      PostRequestDto requestDto = new PostRequestDto(TEST_ANOTHER_POST.getPostTitle(), TEST_ANOTHER_POST.getPostContent());
      MockMultipartFile request = new MockMultipartFile(
          "requestDto", null, "application/json",
          objectMapper.writeValueAsString(requestDto).getBytes(StandardCharsets.UTF_8));

      given(postService.updatePost(TEST_POST_ID, requestDto, file, new UserDetailsImpl(PostTest.TEST_USER)))
          .willThrow(new BusinessException(HttpStatus.UNAUTHORIZED, ErrorCode.AUTHENTICATION_MISMATCH_EXCEPTION));

      // when
      var action = mockMvc.perform(multipart("/api/posts/{postId}", TEST_POST_ID)
          .file(file)
          .file(request)
          .contentType(MediaType.MULTIPART_FORM_DATA)
          .accept(MediaType.APPLICATION_JSON));

      // then
      action.andExpect(status().isUnauthorized()).andDo(print());
    }
  }*/

  @Nested
  @DisplayName("게시글 삭제")
  class deletePost {

    @DisplayName("성공")
    @Test
    @WithMockUser(authorities = {"USER"})
    void deletePostSuccess() throws Exception {

      // given

      // when

      // then

    }

    @DisplayName("실패 - 자신의 글만 삭제")
    @Test
    @WithMockUser(authorities = {"USER"})
    void deletePostFailure() throws Exception {

      // given

      // when

      // then

    }
  }
}
