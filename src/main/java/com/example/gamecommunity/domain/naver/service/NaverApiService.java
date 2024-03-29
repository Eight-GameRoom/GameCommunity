package com.example.gamecommunity.domain.naver.service;

import com.example.gamecommunity.domain.naver.dto.ItemDto;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j(topic = "NAVER API")
@Service
public class NaverApiService {

  private final RestTemplate restTemplate;

  public NaverApiService(RestTemplateBuilder builder) {
    this.restTemplate = builder.build();
  }

  public List<ItemDto> searchNews(String query) {
    // 요청 URL 만들기
    URI uri = UriComponentsBuilder
        .fromUriString("https://openapi.naver.com")
        .path("/v1/search/news.json")
        .queryParam("display", 10)
        .queryParam("query", query)
        .queryParam("sort", "date")
        .encode()
        .build()
        .toUri();
    log.info("uri = " + uri);

    RequestEntity<Void> requestEntity = RequestEntity
        .get(uri)
        .header("X-Naver-Client-Id", "C12vDtDYnZEktMyEyzEY")
        .header("X-Naver-Client-Secret", "XQOtdv26lQ")
        .build();

    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

    log.info("NAVER API Status Code : " + responseEntity.getStatusCode());

    return fromJSONtoItems(responseEntity.getBody());
  }

  public List<ItemDto> fromJSONtoItems(String responseEntity) {
    JSONObject jsonObject = new JSONObject(responseEntity);
    JSONArray items = jsonObject.getJSONArray("items");
    List<ItemDto> itemDtoList = new ArrayList<>();

    for (Object item : items) {
      itemDtoList.add(ItemDto.fromJson((JSONObject) item));
    }

    return itemDtoList;
  }
}