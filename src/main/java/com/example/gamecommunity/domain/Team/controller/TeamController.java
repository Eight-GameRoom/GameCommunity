package com.example.gamecommunity.domain.Team.controller;

import com.example.gamecommunity.domain.Team.dto.TeamRequestDto;
import com.example.gamecommunity.domain.Team.dto.TeamResponseDto;
import com.example.gamecommunity.domain.Team.service.TeamService;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.response.ApiResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teams")
@AllArgsConstructor
public class TeamController {

  private final TeamService teamService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<TeamResponseDto>>> getTeam(User user) {
    List<TeamResponseDto> teamResponseDtoList = teamService.getTeam(user);
    return ResponseEntity.ok(ApiResponse.ok("그룹 목록 조회 성공", teamResponseDtoList));
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<ApiResponse<List<TeamResponseDto>>> getTeam(User user, @PathVariable Long userId) {
    List<TeamResponseDto> teamResponseDtoList = teamService.getTeam(user,userId);
    return ResponseEntity.ok(ApiResponse.ok("유저가 속한 그룹 목록 조회 성공", teamResponseDtoList));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<Void>> createTeam(User user, @RequestBody TeamRequestDto teamRequestDto) {
    teamService.createTeam(user, teamRequestDto);
    return ResponseEntity.ok(ApiResponse.ok("그룹 생성 성공", null));
  }

  @DeleteMapping("/{teamId}")
  public ResponseEntity<ApiResponse<Void>> deleteTeam(User user, @PathVariable Long teamId){
    teamService.deleteTeam(user, teamId);
    return ResponseEntity.ok(ApiResponse.ok("그룹 삭제 성공", null));
  }

  @PatchMapping("/{teamId}")
  public ResponseEntity<ApiResponse<Void>> updateTeam(User user, @PathVariable Long teamId, @RequestBody TeamRequestDto teamRequestDto) {
    teamService.updateTeam(user, teamId, teamRequestDto);
    return ResponseEntity.ok(ApiResponse.ok("그룹 수정 성공", null));
  }

  @PostMapping("/Teams/{teamId}/users/{userId}")
  public ResponseEntity<ApiResponse<Void>> addUserToTeam(User user,@PathVariable Long teamId, @PathVariable Long userId) {
    teamService.addUserToTeam(user, teamId,userId);
    return ResponseEntity.ok(ApiResponse.ok("그룹에 유저 추가 성공", null));
  }

  @DeleteMapping("/Teams/{TeamId}/users/{userId}")
  public ResponseEntity<ApiResponse<Void>> DeleteUserFromTeam(User user,@PathVariable Long teamId, @PathVariable Long userId) {
    teamService.deleteUserFromTeam(user, teamId,userId);
    return ResponseEntity.ok(ApiResponse.ok("그룹에 유저 삭제 성공", null));
  }
}