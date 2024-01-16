package com.example.gamecommunity.domain.team.user.service;

import com.example.gamecommunity.domain.team.user.repository.TeamUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamUserService {
  private final TeamUserRepository teamUserRepository;

  @Transactional
  public void deleteByTeamIdList(List<Long> teamIdList){

    teamUserRepository.deleteByTeamIdList(teamIdList);

  }
}
