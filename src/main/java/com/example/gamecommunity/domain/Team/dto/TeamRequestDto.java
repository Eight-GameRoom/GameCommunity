package com.example.gamecommunity.domain.Team.dto;

import com.example.gamecommunity.domain.Team.entity.Team;

public record TeamRequestDto(
    String name,
    String image,
    String introduction,
    String gameName
) {

}
