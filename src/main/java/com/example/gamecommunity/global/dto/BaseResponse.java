package com.example.gamecommunity.global.dto;



import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BaseResponse<T> {
    private final String msg;
    private final T data;

    public static <T> BaseResponse<T> of(String msg, T data) {
        return new BaseResponse<>(msg, data);
    }


}
