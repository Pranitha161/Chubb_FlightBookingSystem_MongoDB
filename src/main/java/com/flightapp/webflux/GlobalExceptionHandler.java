package com.flightapp.webflux;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import reactor.core.publisher.Mono;

public class GlobalExceptionHandler {
	@ExceptionHandler(ConstraintViolationException.class)
	public Mono<Map<String, String>> handleValidationException(ConstraintViolationException exception) {
	    Map<String, String> errors = new HashMap<>();
	    exception.getConstraintViolations().forEach(error -> {
	        String fieldName = error.getPropertyPath().toString();
	        String errorMessage = error.getMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return Mono.just(errors);
	}

}
