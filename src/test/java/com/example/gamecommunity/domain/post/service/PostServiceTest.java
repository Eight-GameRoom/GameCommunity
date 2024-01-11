package com.example.gamecommunity.domain.post.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.example.gamecommunity.domain.common.CommonTest;
import com.example.gamecommunity.domain.post.dto.PostRequestDto;
import com.example.gamecommunity.domain.post.entity.Post;
import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.user.entity.User;
import java.io.IOException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@DisplayName("게시글 서비스 테스트")
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

  @InjectMocks
  private PostService postService;
  @Mock
  private PostRepository postRepository;
  @Mock
  private PostImageUploadService postImageUploadService;

  @Nested
  @DisplayName("게시글 작성 테스트")
  class CreatePostTest implements CommonTest {

    @Test
    @DisplayName("게시글 작성 - 성공")
    void createPostTestSuccess() throws IOException {

      // given
      PostRequestDto requestDto = new PostRequestDto(TEST_POST_TITLE, TEST_POST_CONTENT);
      MultipartFile file = mock(MultipartFile.class);
      User loginUser = mock(User.class);

      given(postImageUploadService.uploadFile(file)).willReturn(TEST_POST_IMAGE_URL);

      // when
      postService.createPost(requestDto, GAME_TYPE, GAME_NAME, BOARD_NAME, file, loginUser);

      // Then
      verify(postImageUploadService, times(1)).uploadFile(file);
      verify(postRepository, times(1)).save(any(Post.class));
    }
  }
}