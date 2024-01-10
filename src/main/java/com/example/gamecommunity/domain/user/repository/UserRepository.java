package com.example.gamecommunity.domain.user.repository;

import com.example.gamecommunity.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

  Optional<User> findByEmail(String Email);

  Optional<User> findByNickname(String nickname);
}
