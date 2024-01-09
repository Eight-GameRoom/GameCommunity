package com.example.gamecommunity.domain.group.controller;

import com.example.gamecommunity.domain.admin.dto.NoticeRequestDto;
import com.example.gamecommunity.domain.admin.dto.UserBlockRequestDto;
import com.example.gamecommunity.domain.admin.service.AdminService;
import com.example.gamecommunity.domain.group.dto.GroupRequestDto;
import com.example.gamecommunity.domain.group.dto.GroupResponseDto;
import com.example.gamecommunity.domain.group.service.GroupService;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.response.ApiResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groups")
@AllArgsConstructor
public class GroupController {

  private final GroupService groupService;

//  @GetMapping
//  public ResponseEntity<ApiResponse<List<GroupResponseDto>>> getGroup(User user) {
//    List<GroupResponseDto> groupResponseDtoList = GroupService.getGroup(user);
//    return ResponseEntity.ok(ApiResponse.ok("그룹 목록 조회 성공", groupResponseDtoList));
//  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<ApiResponse<List<GroupResponseDto>>> getGroup(User user, @PathVariable Long userId) {
    List<GroupResponseDto> groupResponseDtoList = groupService.getGroup(user,userId);
    return ResponseEntity.ok(ApiResponse.ok("유저가 속한 그룹 목록 조회 성공", groupResponseDtoList));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Void>> createGroup(User user, @RequestBody GroupRequestDto groupRequestDto) {
    groupService.createGroup(user, groupRequestDto);
    return ResponseEntity.ok(ApiResponse.ok("그룹 생성 성공", null));
  }

  @DeleteMapping("/{groupId}")
  public ResponseEntity<ApiResponse<Void>> deleteGroup(User user, @PathVariable Long groupId){
    groupService.deleteGroup(user, groupId);
    return ResponseEntity.ok(ApiResponse.ok("그룹 삭제 성공", null));
  }

  @PatchMapping("/{groupId}")
  public ResponseEntity<ApiResponse<Void>> updateGroup(User user, @PathVariable Long groupId, @RequestBody GroupRequestDto groupRequestDto) {
    groupService.updateGroup(user, groupId, groupRequestDto);
    return ResponseEntity.ok(ApiResponse.ok("그룹 수정 성공", null));
  }

  @PostMapping("/groups/{groupId}/users/{userId}")
  public ResponseEntity<ApiResponse<Void>> addUserToGroup(User user,@PathVariable Long groupId, @PathVariable Long userId) {
    groupService.addUserToGroup(user, groupId,userId);
    return ResponseEntity.ok(ApiResponse.ok("그룹에 유저 추가 성공", null));
  }

  @DeleteMapping("/groups/{groupId}/users/{userId}")
  public ResponseEntity<ApiResponse<Void>> DeleteUserFromGroup(User user,@PathVariable Long groupId, @PathVariable Long userId) {
    groupService.deleteUserFromGroup(user, groupId,userId);
    return ResponseEntity.ok(ApiResponse.ok("그룹에 유저 삭제 성공", null));
  }
}
