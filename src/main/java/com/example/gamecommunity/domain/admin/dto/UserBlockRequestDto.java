package com.example.gamecommunity.domain.admin.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

public record UserBlockRequestDto(

    long userId,
    DateTime blockDate

) {

}
