package com.example.gamecommunity.domain.Team.service;

import com.example.gamecommunity.domain.Team.dto.TeamRequestDto;
import com.example.gamecommunity.domain.Team.dto.TeamResponseDto;
import com.example.gamecommunity.domain.user.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TeamService {


  public List<TeamResponseDto> getTeam(User user) {

    return null;
  }
  public List<TeamResponseDto> getTeam(User user, Long userId) {

    return null;
  }

  public void createTeam(User user, TeamRequestDto teamRequestDto) {
  }

  public void deleteTeam(User user, Long teamId) {
  }

  public void updateTeam(User user, Long teamId, TeamRequestDto teamRequestDto) {
  }

  public void addUserToTeam(User user, Long teamId, Long userId) {
  }

  public void deleteUserFromTeam(User user, Long teamId, Long userId) {
  }
}
