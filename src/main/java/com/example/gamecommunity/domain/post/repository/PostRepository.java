package com.example.gamecommunity.domain.post.repository;

import com.example.gamecommunity.domain.enums.boardName.BoardName;
import com.example.gamecommunity.domain.enums.gameName.GameName;
import com.example.gamecommunity.domain.enums.gameType.GameType;
import com.example.gamecommunity.domain.post.entity.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {


  Page<Post> findByGameTypeAndGameNameAndBoardName(
      GameType type, GameName game, BoardName board, Pageable pageable);

  Optional<Post> findByPostId(Long postId);
}