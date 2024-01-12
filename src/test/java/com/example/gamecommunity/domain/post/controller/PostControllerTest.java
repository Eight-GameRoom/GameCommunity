package com.example.gamecommunity.domain.post.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@DisplayName("게시글 컨트롤러 테스트")
@WebMvcTest(PostController.class)
class PostControllerTest extends ControllerTest implements PostTest {

  @MockBean
  private PostService postService;

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
}
