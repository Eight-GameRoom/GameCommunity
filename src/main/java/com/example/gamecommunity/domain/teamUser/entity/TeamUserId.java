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


  @Column
  private Long teamId;


  @Column
  private Long userId;
}
