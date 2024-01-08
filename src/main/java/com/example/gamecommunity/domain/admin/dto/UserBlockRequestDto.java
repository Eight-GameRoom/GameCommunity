package com.example.gamecommunity.domain.admin.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
public class UserBlockRequestDto {
  private long userId;
  private DateTime blockDate;

}
