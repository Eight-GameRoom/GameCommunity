package com.example.gamecommunity.domain.team.repository;

import com.example.gamecommunity.domain.team.entity.Team;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

  List<Team> findAllByGameName();

  List<Team> findAllByUserId(Long id);
}
