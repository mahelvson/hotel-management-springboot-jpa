package com.hotel.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidUserTypeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public InvalidUserTypeException(String message) {
        super(message);
    }
}