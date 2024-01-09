package com.example.gamecommunity.domain.Team.service;

import com.example.gamecommunity.domain.Team.dto.TeamRequestDto;
import com.example.gamecommunity.domain.Team.dto.TeamResponseDto;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TeamService {


  public List<TeamResponseDto> getTeam(UserDetailsImpl userDetails) {

    return null;
  }
  public List<TeamResponseDto> getTeam(UserDetailsImpl userDetails, Long userId) {

    return null;
  }

  public void createTeam(UserDetailsImpl userDetails, TeamRequestDto teamRequestDto) {
  }

  public void deleteTeam(UserDetailsImpl userDetails, Long teamId) {
  }

  public void updateTeam(UserDetailsImpl userDetails, Long teamId, TeamRequestDto teamRequestDto) {
  }

  public void addUserToTeam(UserDetailsImpl userDetails, Long teamId, Long userId) {
  }

  public void deleteUserFromTeam(UserDetailsImpl userDetails, Long teamId, Long userId) {
  }
}
