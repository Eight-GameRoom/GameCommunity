package com.example.gamecommunity.domain.naver.controller;

import com.example.gamecommunity.domain.naver.dto.ItemDto;
import com.example.gamecommunity.domain.naver.service.NaverApiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
  public List<ItemDto> searchItems(@RequestParam String query)  {
    return naverApiService.searchNews(query);
  }

}
