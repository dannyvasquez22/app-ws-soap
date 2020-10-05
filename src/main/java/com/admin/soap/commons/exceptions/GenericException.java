package com.admin.soap.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "Error Generico Interno")
@Getter
@AllArgsConstructor
public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String statusCode;
	private final String message;
}
