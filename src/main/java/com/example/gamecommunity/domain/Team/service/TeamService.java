package com.example.gamecommunity.domain.Team.service;

import com.example.gamecommunity.domain.Team.GameEnum;
import com.example.gamecommunity.domain.Team.dto.TeamRequestDto;
import com.example.gamecommunity.domain.Team.dto.TeamResponseDto;
import com.example.gamecommunity.domain.Team.entity.Team;
import com.example.gamecommunity.domain.Team.repository.TeamRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;


  public void createTeam(UserDetailsImpl userDetails, TeamRequestDto teamRequestDto) {
    User user = userDetails.getUser();
    Team team = new Team(user.getId(),teamRequestDto);
    teamRepository.save(team);
  }

  // 게임별로 가져오기
  public Map<String,List<TeamResponseDto>> getTeam(UserDetailsImpl userDetails) {
    Map<String, List<TeamResponseDto>> teamMap = new HashMap<>();
    for (GameEnum game : GameEnum.values()) {
      List<TeamResponseDto> teamResponseDtos = teamRepository.findAllByGameName()
          .stream()
          .map(TeamResponseDto::new)
          .collect(Collectors.toList());
      teamMap.put(game.getGameName(), teamResponseDtos);
    }
    return teamMap;

  }

  public List<TeamResponseDto> getTeam(UserDetailsImpl userDetails, Long userId) {
    return null;
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
