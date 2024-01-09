package com.example.gamecommunity.domain.Team.repository;

import com.example.gamecommunity.domain.Team.entity.Team;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

  List<Team> findAllByGameName();
}
