package com.example.gamecommunity.domain.post.repository;

import com.example.gamecommunity.domain.enums.board.BoardName;
import com.example.gamecommunity.domain.enums.game.name.GameName;
import com.example.gamecommunity.domain.enums.game.type.GameType;
import com.example.gamecommunity.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {


  Page<Post> findByGameTypeAndGameNameAndBoardName(
      GameType type, GameName game, BoardName board, Pageable pageable);

  @Modifying
  @Query(value = "delete from Post as p where p.user.id = :userId")
  void deleteUserCascadePost(Long userId);
}