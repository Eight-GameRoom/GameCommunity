package com.example.gamecommunity.domain.Team.entity;

import com.example.gamecommunity.domain.Team.dto.TeamRequestDto;
import com.example.gamecommunity.global.auditing.TimeStamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
@Table(name = "Teams")
public class Team extends TimeStamped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private Long adminId;
  @Column
  private String name;
  @Column
  private String image;
  @Column
  private String introduction;
  @Column
  private String gameName;


  @OneToMany(mappedBy = "team")
  private List<TeamUser> teamUsers = new ArrayList<>();

  public Team(Long adminId, TeamRequestDto teamRequestDto){
    this.adminId = adminId;
    this.name = teamRequestDto.getName();
    this.image = teamRequestDto.getImage();
    this.introduction = teamRequestDto.getIntroduction();
    this.gameName = teamRequestDto.getGameName();
  }

}
