package com.hotel.manager.exceptions;

public class ConfirmedBookingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public ConfirmedBookingException(String message) {
        super(message);
    }
}