package com.example.gamecommunity.domain.guest.repository;

import com.example.gamecommunity.domain.guest.entity.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {

}
