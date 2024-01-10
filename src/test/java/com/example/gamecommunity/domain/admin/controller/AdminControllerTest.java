package com.example.gamecommunity.domain.admin.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.gamecommunity.domain.admin.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminControllerTest {

  @Autowired
  AdminService adminService;

  @Test
  void getUsers() {
    adminService.getUsers();
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