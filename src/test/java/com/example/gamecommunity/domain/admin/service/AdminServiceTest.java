package com.example.gamecommunity.domain.admin.service;

import com.example.gamecommunity.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private AdminService adminService;

  @Test
  void getUsers() {
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