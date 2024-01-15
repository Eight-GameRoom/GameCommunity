package com.example.gamecommunity.domain.team.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeamUserId implements Serializable {


  @Column
  private Long teamId;


  @Column
  private Long userId;
}
