package com.example.gamecommunity.domain.group.entity;

import com.example.gamecommunity.global.common.TimeStamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Groups")
public class Group extends TimeStamped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;
  @Column
  private String image;
  @Column
  private String introduction;
  @Column
  private String gameName;

  @OneToMany(mappedBy = "group")
  private List<GroupUser> GroupUsers  = new ArrayList<>();

}
