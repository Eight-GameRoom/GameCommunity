package com.example.gamecommunity.domain.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.gamecommunity.domain.post.repository.PostRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
//@SpringBootTest(classes = AdminService.class)
@DataJpaTest
class AdminServiceTest {

  @Autowired
  private UserRepository userRepository;
//  @MockBean
//  private PostRepository postRepository;
  @Autowired
  private AdminService adminService;

  @Test
  void getUsers() {
    // given
    User user = User.builder()
        .email("test@test.com")
        .password("1234")
        .nickname("test")
        .build();
    userRepository.save(user);

    // when
    var users = adminService.getUsers();
    var email = users.get(0).email();

    // then
    assertEquals(email, "test@test.com");
  }

  @Test
  void getUser() {
  }

  @Test
  void deleteUser() {
  }

  @Test
  void setBlock() {
  }

  @Test
  void getReportedPosts() {
  }

  @Test
  void getReportedPost() {
  }

  @Test
  void writeNotice() {
  }
}