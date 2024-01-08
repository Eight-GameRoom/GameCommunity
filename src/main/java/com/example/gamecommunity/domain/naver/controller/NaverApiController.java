package com.example.gamecommunity.domain.naver.controller;

import com.example.gamecommunity.domain.naver.dto.ItemDto;
import com.example.gamecommunity.domain.naver.service.NaverApiService;
import com.example.gamecommunity.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NaverApiController {

  private final NaverApiService naverApiService;

  @GetMapping("/search")
  public ResponseEntity<?> searchItems(@RequestParam String query)  {
    List<ItemDto> newsList = naverApiService.searchNews(query);
    return ResponseEntity.ok(ApiResponse.ok(query + " 검색 성공", newsList));
  }

}
