package com.example.gamecommunity.domain.team.controller;

import com.example.gamecommunity.domain.team.dto.TeamRequestDto;
import com.example.gamecommunity.domain.team.dto.TeamResponseDto;
import com.example.gamecommunity.domain.team.service.TeamService;
import com.example.gamecommunity.global.response.ApiResponse;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

  @PostMapping
  public ResponseEntity<ApiResponse<Void>> createTeam(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody TeamRequestDto teamRequestDto) {
    teamService.createTeam(userDetails, teamRequestDto);
    return ResponseEntity.ok(ApiResponse.ok("그룹 생성 성공", null));
  }
  @GetMapping
  public ResponseEntity<ApiResponse<Map<String,List<TeamResponseDto>>>> getTeam() {
    Map<String, List<TeamResponseDto>> teamMap = teamService.getTeam();
    return ResponseEntity.ok(ApiResponse.ok("게임 별로 속해 있는 그룹 목록 조회 성공", teamMap));
  }

  @GetMapping("/users")
  public ResponseEntity<ApiResponse<List<TeamResponseDto>>> getTeam(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    List<TeamResponseDto> teamResponseDtoList = teamService.getTeam(userDetails);
    return ResponseEntity.ok(ApiResponse.ok("유저가 속한 그룹 목록 조회 성공", teamResponseDtoList));
  }



  @DeleteMapping("/{teamId}")
  public ResponseEntity<ApiResponse<Void>> deleteTeam(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long teamId) {
    teamService.deleteTeam(userDetails, teamId);
    return ResponseEntity.ok(ApiResponse.ok("그룹 삭제 성공", null));
  }

  @PatchMapping("/{teamId}")
  public ResponseEntity<ApiResponse<Void>> updateTeam(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long teamId,
      @RequestBody TeamRequestDto teamRequestDto) {
    teamService.updateTeam(userDetails, teamId, teamRequestDto);
    return ResponseEntity.ok(ApiResponse.ok("그룹 수정 성공", null));
  }

  @PostMapping("/Teams/{teamId}/users/{userId}")
  public ResponseEntity<ApiResponse<Void>> addUserToTeam(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long teamId,
      @PathVariable Long userId) {
    teamService.addUserToTeam(userDetails, teamId, userId);
    return ResponseEntity.ok(ApiResponse.ok("그룹에 유저 추가 성공", null));
  }

  @DeleteMapping("/Teams/{TeamId}/users/{userId}")
  public ResponseEntity<ApiResponse<Void>> DeleteUserFromTeam(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long teamId,
      @PathVariable Long userId) {
    teamService.deleteUserFromTeam(userDetails, teamId, userId);
    return ResponseEntity.ok(ApiResponse.ok("그룹에 유저 삭제 성공", null));
  }
}
