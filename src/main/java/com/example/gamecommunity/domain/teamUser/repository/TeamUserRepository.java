package com.example.gamecommunity.domain.teamUser.repository;

import com.example.gamecommunity.domain.team.entity.Team;
import com.example.gamecommunity.domain.teamUser.entity.TeamUser;
import com.example.gamecommunity.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {
  List<TeamUser> findAllByUserId(Long userId);

  Optional<TeamUser> findByTeamAndUser(Team team, User deletedUser);
}
