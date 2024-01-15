package com.example.gamecommunity.domain.user.dto;

import org.springframework.format.annotation.DateTimeFormat;

public record ModifyProfileDto(

        String introduction,
        String profile_url
) {

}
