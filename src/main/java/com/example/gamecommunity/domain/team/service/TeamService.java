package com.example.gamecommunity.domain.team.service;

import com.example.gamecommunity.domain.enums.gameName.GameName;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


  public void createTeam(User user, TeamRequestDto teamRequestDto) {
    Team team = new Team(user.getId(), teamRequestDto);
    teamRepository.save(team);
    //addUserToTeam(user,team.getTeamId(), user.getId());
    addAdminUserToTeam(user,team);
  }

  // 게임별로 가져오기
  public Map<GameName, List<TeamResponseDto>> getTeamsByGame() {
    Map<GameName, List<TeamResponseDto>> teamMap = new HashMap<>();
    for (GameName game : GameName.values()) {
      List<TeamResponseDto> teamResponseDtos = teamRepository.findAllByGameName(game)
          .stream()
          .map(TeamResponseDto::new)
          .toList();
      teamMap.put(game, teamResponseDtos);
    }
    return teamMap;
  }

  // 유저 본인이 속한 그룹
  public List<TeamResponseDto> getTeamsByUser(User user) {
    List<TeamResponseDto> teamResponseDtos = teamUserRepository.findAllByUserId(user.getId())
        .stream() // TeamUser Entity가 리스트로 나옴
        .map(Team -> new TeamResponseDto(Team.getTeam()))
        .toList();

    return teamResponseDtos;
  }


  public void deleteTeam(User user, Long teamId) {

    Team team = teamRepository.findById(teamId).orElseThrow( () ->
         new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_TEAM_EXCEPTION));

    if(!user.getId().equals(team.getTeamAdminId())){
      throw new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EQUALS_TEAM_ADMIN);
    }

    teamRepository.delete(team);
  }

  @Transactional
  public void updateTeam(User user, Long teamId, TeamRequestDto teamRequestDto) {

    Team team = teamRepository.findById(teamId).orElseThrow( () ->
        new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_TEAM_EXCEPTION));

    if(!user.getId().equals(team.getTeamAdminId())){
      throw new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EQUALS_TEAM_ADMIN);
    }

    team.update(teamRequestDto);
  }

  public void addUserToTeam(User user, Long teamId, Long invitedUserId) {

    Team team = teamRepository.findByTeamId(teamId).orElseThrow( () ->
        new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_TEAM_EXCEPTION));

    if(!user.getId().equals(team.getTeamAdminId())){
      throw new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EQUALS_TEAM_ADMIN);
    }

    User invitedUser = userRepository.findById(invitedUserId).orElseThrow(() ->
        new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_EXCEPTION));

    TeamUser teamUser = new TeamUser(team,invitedUser);

    teamUserRepository.save(teamUser);
  }
  public void addAdminUserToTeam(User user, Team team) {
    TeamUser teamUser = new TeamUser(team,user);
    teamUserRepository.save(teamUser);
  }

  public void deleteUserFromTeam(User user, Long teamId, Long userId) {

    Team team = teamRepository.findById(teamId).orElseThrow( () ->
        new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_TEAM_EXCEPTION));

    if(!user.getId().equals(team.getTeamAdminId())){
      throw new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_EQUALS_TEAM_ADMIN);
    }

    User deletedUser = userRepository.findById(userId).orElseThrow(() ->
        new BusinessException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND_USER_EXCEPTION));



    TeamUser teamUser = teamUserRepository.findByTeamAndUser(team, deletedUser).orElseThrow(() ->
        new BusinessException(HttpStatus.NOT_FOUND,ErrorCode.NOT_FOUND_TEAM_USER));

    teamUserRepository.delete(teamUser);
  }
}
