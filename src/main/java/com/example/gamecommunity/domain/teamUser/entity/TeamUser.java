package com.example.gamecommunity.domain.teamUser.entity;

import com.example.gamecommunity.domain.team.entity.Team;
import com.example.gamecommunity.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TeamUsers")
@NoArgsConstructor
public class TeamUser {

  @EmbeddedId
  TeamUserId id;


  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public TeamUser(Team team, User user){
    this.id= new TeamUserId(team.getId(), user.getId());
    this.team = team;
    this.user = user;

  }


}
