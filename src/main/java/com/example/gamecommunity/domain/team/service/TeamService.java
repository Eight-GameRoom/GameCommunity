package com.example.gamecommunity.domain.team.service;

import com.example.gamecommunity.domain.team.GameEnum;
import com.example.gamecommunity.domain.team.dto.TeamRequestDto;
import com.example.gamecommunity.domain.team.dto.TeamResponseDto;
import com.example.gamecommunity.domain.team.entity.Team;
import com.example.gamecommunity.domain.team.repository.TeamRepository;
import com.example.gamecommunity.domain.teamUser.entity.TeamUser;
import com.example.gamecommunity.domain.teamUser.repository.TeamUserRepository;
import com.example.gamecommunity.domain.user.entity.User;
import com.example.gamecommunity.domain.user.repository.UserRepository;
import com.example.gamecommunity.global.exception.common.BusinessException;
import com.example.gamecommunity.global.exception.common.ErrorCode;
import com.example.gamecommunity.global.exception.common.GlobalExceptionHandler;
import com.example.gamecommunity.global.security.userdetails.UserDetailsImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;
  private final TeamUserRepository teamUserRepository;
  private final UserRepository userRepository;


  public void createTeam(UserDetailsImpl userDetails, TeamRequestDto teamRequestDto) {
    User user = userDetails.getUser();
    Team team = new Team(user.getId(), teamRequestDto);
    teamRepository.save(team);
  }

  // 게임별로 가져오기
  public Map<String, List<TeamResponseDto>> getTeam() {
    Map<String, List<TeamResponseDto>> teamMap = new HashMap<>();
    for (GameEnum game : GameEnum.values()) {
      List<TeamResponseDto> teamResponseDtos = teamRepository.findAllByGameName()
          .stream()
          .map(TeamResponseDto::new)
          .toList();
      teamMap.put(game.getGameName(), teamResponseDtos);
    }
    return teamMap;
  }

  // 유저 본인이 속한 그룹
  public List<TeamResponseDto> getTeam(UserDetailsImpl userDetails) {
    User user = userDetails.getUser();
    List<TeamResponseDto> teamResponseDtos = teamUserRepository.findAllByUserId(user.getId())
        .stream() // TeamUser Entity가 리스트로 나옴
        .map(Team -> new TeamResponseDto(Team.getTeam()))
        .toList();

    return teamResponseDtos;
  }


  public void deleteTeam(UserDetailsImpl userDetails, Long teamId) {

    User user = userDetails.getUser();

    Team team = teamRepository.findByTeamId(teamId).orElseThrow( () ->
         new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_TEAM_EXCEPTION));

    if(user.getId().equals(team.getAdminId())){
      throw new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EQUALS_TEAM_ADMIN);
    }

    teamRepository.delete(team);
  }

  @Transactional
  public void updateTeam(UserDetailsImpl userDetails, Long teamId, TeamRequestDto teamRequestDto) {
    User user = userDetails.getUser();

    Team team = teamRepository.findByTeamId(teamId).orElseThrow( () ->
        new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_TEAM_EXCEPTION));

    if(user.getId().equals(team.getAdminId())){
      throw new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EQUALS_TEAM_ADMIN);
    }

    team.update(teamRequestDto);
  }

  public void addUserToTeam(UserDetailsImpl userDetails, Long teamId, Long userId) {
    User user = userDetails.getUser();

    Team team = teamRepository.findByTeamId(teamId).orElseThrow( () ->
        new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_TEAM_EXCEPTION));

    if(user.getId().equals(team.getAdminId())){
      throw new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EQUALS_TEAM_ADMIN);
    }

    User invitedUser = userRepository.findById(userId).orElseThrow(() ->
        new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_EXCEPTION));

    TeamUser teamUser = new TeamUser(team,invitedUser);

    teamUserRepository.save(teamUser);
  }

  public void deleteUserFromTeam(UserDetailsImpl userDetails, Long teamId, Long userId) {

    User user = userDetails.getUser();

    Team team = teamRepository.findByTeamId(teamId).orElseThrow( () ->
        new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_TEAM_EXCEPTION));

    if(user.getId().equals(team.getAdminId())){
      throw new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EQUALS_TEAM_ADMIN);
    }

    User deletedUser = userRepository.findById(userId).orElseThrow(() ->
        new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_EXCEPTION));



    TeamUser teamUser = teamUserRepository.findByTeamAndUser(team, deletedUser).orElseThrow(() ->
        new BusinessException(HttpStatus.NOT_FOUND,ErrorCode.NOT_FOUND_TEAM_USER));

    teamUserRepository.delete(teamUser);
  }
}
