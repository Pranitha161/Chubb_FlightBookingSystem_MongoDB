package com.flightapp.webflux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import jakarta.validation.Path;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import reactor.core.publisher.Mono;

class GlobalExceptionHandlerTest {
	@Test
	void testHandleValidationException() {
		ConstraintViolation<?> violation=mock(ConstraintViolation.class);
		Path path=mock(Path.class);
		when(path.toString()).thenReturn("email");
		when(violation.getPropertyPath()).thenReturn( path);
		when(violation.getMessage()).thenReturn("must be a valid email");
		ConstraintViolationException exception =
                new ConstraintViolationException(Set.of(violation));
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Mono<Map<String, String>> result = handler.handleValidationException(exception);
        Map<String, String> errors = result.block();
        assertNotNull(errors);
        assertEquals("must be a valid email", errors.get("email"));
	}
}
