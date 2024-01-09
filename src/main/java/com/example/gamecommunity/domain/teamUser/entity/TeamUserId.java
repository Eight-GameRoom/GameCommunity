package com.example.gamecommunity.domain.teamUser.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class TeamUserId implements Serializable {

  @Column(name ="team_id")
  private Long teamId;

  @Column(name = "user_id")
  private Long userId;
}
