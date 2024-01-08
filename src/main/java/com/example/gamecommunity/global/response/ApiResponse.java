package com.example.gamecommunity.global.response;

import lombok.Getter;
import org.springframework.web.ErrorResponse;

@Getter
public class ApiResponse< T > {
	private String message;
	private ResponseBody data;

	public ApiResponse( String message, ResponseBody data) {
		this.message = message;
		this.data = data;
	}

	public static < T > ApiResponse< T > ok( String message, T data ) {
		return new ApiResponse< T >(
				message,
				new ResponseBody( data )
		);
	}

	public static < T > ApiResponse< T > fail( String message, ErrorResponse errorResponse ) {
		return new ApiResponse< T >(
				message,
				new ResponseBody( errorResponse )
		);
	}
}
