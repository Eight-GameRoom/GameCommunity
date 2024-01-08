package com.example.gamecommunity.domain.group.service;

import com.example.gamecommunity.domain.group.dto.GroupRequestDto;
import com.example.gamecommunity.domain.group.dto.GroupResponseDto;
import com.example.gamecommunity.domain.user.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GroupService {


  public List<GroupResponseDto> getGroup(User user, Long userId) {

  }

  public void createGroup(User user, GroupRequestDto groupRequestDto) {
  }

  public void deleteGroup(User user, Long groupId) {
  }

  public void updateGroup(User user, Long groupId, GroupRequestDto groupRequestDto) {
  }

  public void addUserToGroup(User user, Long groupId, Long userId) {
  }

  public void deleteUserFromGroup(User user, Long groupId, Long userId) {
  }
}
