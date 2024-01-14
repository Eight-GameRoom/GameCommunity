package com.example.gamecommunity.domain.post.repository;

import com.example.gamecommunity.domain.enums.board.BoardName;
import com.example.gamecommunity.domain.enums.game.name.GameName;
import com.example.gamecommunity.domain.enums.game.type.GameType;
import com.example.gamecommunity.domain.post.entity.ApiPostUseTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiPostUseTimeRepository extends JpaRepository<ApiPostUseTime, Long> {

  Optional<ApiPostUseTime> findByGameTypeAndGameNameAndBoardName(
      GameType type, GameName game, BoardName board);

}
