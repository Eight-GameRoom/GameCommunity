package com.example.gamecommunity.domain.team.service;

import com.example.gamecommunity.domain.team.GameEnum;
import com.example.gamecommunity.domain.team.dto.TeamRequestDto;
import com.example.gamecommunity.domain.team.dto.TeamResponseDto;
import com.example.gamecommunity.domain.team.entity.Team;
import com.example.gamecommunity.domain.team.repository.TeamRepository;
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

  // 유저가 속한 그룹
  public List<TeamResponseDto> getTeam(UserDetailsImpl userDetails, Long userId) {
    User user = userDetails.getUser();
    List<TeamResponseDto> teamResponseDtos = teamRepository.findAllByUserId(user.getId())



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
