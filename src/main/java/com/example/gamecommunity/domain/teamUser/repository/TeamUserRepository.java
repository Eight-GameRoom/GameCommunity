package com.example.gamecommunity.domain.teamUser.repository;

import com.example.gamecommunity.domain.teamUser.entity.TeamUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {
  List<TeamUser> findAllByUserId(Long userId);

}
