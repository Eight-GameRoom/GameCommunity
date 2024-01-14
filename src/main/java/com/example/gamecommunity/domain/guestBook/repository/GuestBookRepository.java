package com.example.gamecommunity.domain.guestBook.repository;

import com.example.gamecommunity.domain.guestBook.entity.GuestBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {
  List<GuestBook> findAllById(Long userId);

}
