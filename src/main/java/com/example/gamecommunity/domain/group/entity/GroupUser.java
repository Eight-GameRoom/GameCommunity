package com.example.gamecommunity.domain.group.entity;

import com.example.gamecommunity.domain.user.entity.User;

import com.example.gamecommunity.global.auditing.TimeStamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Cleanup;

@Entity
@Table(name = "GroupUsers")
public class GroupUser extends TimeStamped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String role;

//  @ManyToOne
//  @JoinColumn(name = "user_id", nullable = false)
//
//  @ManyToOne
//  @JoinColumn(name = "group_id", nullable = false)



}
