package com.example.gamecommunity.domain.admin.controller;

import static com.example.gamecommunity.domain.test.UserTest.TEST_ADMIN_USER;
import static com.example.gamecommunity.domain.test.UserTest.TEST_USER;
import static com.example.gamecommunity.domain.test.UserTest.TEST_USER_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.gamecommunity.domain.admin.dto.AdminUserResponseDto;
import com.example.gamecommunity.domain.admin.service.AdminService;
import com.example.gamecommunity.domain.post.dto.PostResponseDto;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = AdminController.class)
class AdminControllerTest {

  @MockBean
  private AdminService adminService;

  @Autowired
  private MockMvc mockMvc;

  private UserDetailsImpl userDetails;
  @BeforeEach
  void makeAdminUser() {
    userDetails = new UserDetailsImpl(TEST_ADMIN_USER);
  }

  @Test
  @WithMockUser(username = "username")
  void getUsers() throws Exception {
    // given
    List<AdminUserResponseDto> list = new ArrayList<>();
    list.add(new AdminUserResponseDto(TEST_USER));
    BDDMockito.given(adminService.getUsers(userDetails)).willReturn(list);

    // when
    var ret = mockMvc.perform(get("/api/admin/users")
        .contentType(MediaType.APPLICATION_JSON)
    );

    // then
    ret.andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "username")
  void getUser() throws Exception {
    // given
    BDDMockito.given(adminService.getUser(userDetails, TEST_USER_ID))
        .willReturn(new AdminUserResponseDto(TEST_USER));

    // when
    var ret = mockMvc.perform(get("/api/admin/users/{userId}", TEST_USER_ID)
        .contentType(MediaType.APPLICATION_JSON));

    // then
    ret.andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "username")
  void deleteUser() throws Exception {
    //BDDMockito.given(adminService.deleteUser(TEST_USER_ID))

    var ret = mockMvc.perform(delete("/api/admin/users/{userId}", 1)
        .contentType(MediaType.APPLICATION_JSON)
    );

//    ret.andExpect(status().isOk())
//        .andDo(print());
  }

  @Test
  void setBlock() {
    //BDDMockito.given(adminService.setBlock(new UserBlockRequestDto(TEST_USER_ID, LocalDateTime.now()))).willReturn(null);
  }

  @Test
  @WithMockUser(username = "username")
  void getReportedPosts() throws Exception {
    List<PostResponseDto> list = new ArrayList<>();
    BDDMockito.given(adminService.getReportedPosts(userDetails)).willReturn(list);

    var ret = mockMvc.perform(get("/api/admin/posts/report")
        .contentType(MediaType.APPLICATION_JSON));

    ret.andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void getReportedPost() {
  }

  @Test
  void writeNotice() {
  }
}