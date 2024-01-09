package com.example.gamecommunity.domain.naver.dto;

import org.json.JSONObject;

public record ItemDto (
    String title,
    String link,
    String description
) {

  public static ItemDto fromJson(JSONObject itemJson) {
    return new ItemDto(
        itemJson.getString("title"),
        itemJson.getString("link"),
        itemJson.getString("description")
    );
  }

}
