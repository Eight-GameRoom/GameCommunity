package com.example.gamecommunity.domain.team.repository;

import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.team.entity.Team;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

  List<Team> findAllByGameName(GameName gameName);

  Team findByTeamName(String teamName);

  Optional<Team> findByTeamId(Long teamId);
}
