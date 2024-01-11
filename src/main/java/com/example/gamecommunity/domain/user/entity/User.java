package com.example.gamecommunity.domain.user.entity;



import com.example.gamecommunity.domain.teamUser.entity.TeamUser;
import com.example.gamecommunity.domain.post.entity.Post;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;
import reactor.core.scheduler.Scheduler.Worker;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false, unique = true)
  private String nickname;

  @Column(nullable = false)
  private String password;

  @Column()
  private String introduction;

  @Column()
  private Instant blockDate;

  @Column(nullable = false)
  private int ranking;

  @Column()
  private String profileUrl;


  @OneToMany(mappedBy = "user",cascade = CascadeType.ALL , orphanRemoval = true)
  private List<TeamUser> teamUsers;


  @OneToMany(mappedBy = "user")
  private List<Post> posts = new ArrayList<>();


  public void updatePassword(String password) {
    this.password = password;
  }
}
