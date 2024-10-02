package com.hotel.manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleEnumConversionException(HttpMessageNotReadableException ex) {
		if (ex.getCause() instanceof InvalidFormatException) {
			InvalidFormatException invalidFormatException = (InvalidFormatException) ex.getCause();
			if (invalidFormatException.getTargetType().isEnum()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Invalid value for enum UserType. Accepted values are: RECEPTIONIST, MANAGER, CLIENT");
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request payload");
	}

	@ExceptionHandler(UserAlreadyExistisException.class)
	public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistisException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
}
