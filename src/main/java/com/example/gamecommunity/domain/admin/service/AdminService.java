package com.example.gamecommunity.domain.admin.service;

import com.example.gamecommunity.domain.admin.dto.AdminPostResponseDto;
import com.example.gamecommunity.domain.admin.dto.AdminUserResponseDto;
import com.example.gamecommunity.domain.admin.dto.NoticeRequestDto;
import com.example.gamecommunity.domain.admin.dto.UserBlockRequestDto;
import com.example.gamecommunity.domain.user.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

  public List<AdminUserResponseDto> getUsers() {
    return null;
  }

  public AdminUserResponseDto getUser(long userId) {
    return null;
  }

  public void deleteUser(long userId) {

  }

  public void setBlock(UserBlockRequestDto userBlockRequestDto) {

  }

  public List<AdminPostResponseDto> getReportedPosts() {
    return null;
  }

  public AdminPostResponseDto getReportedPost(long postId) {
    return null;
  }

  public void writeNotice(NoticeRequestDto noticeRequestDto) {

  }
}
