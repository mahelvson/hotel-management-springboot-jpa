package com.hotel.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class HotelAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public HotelAlreadyExistsException(String message) {
		super(message);
	}
}
