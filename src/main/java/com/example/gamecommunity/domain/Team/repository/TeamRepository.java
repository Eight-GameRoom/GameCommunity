package com.example.gamecommunity.domain.Team.repository;

import com.example.gamecommunity.domain.Team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Long, Team> {

}
