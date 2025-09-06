package com.example.newsapi.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerMoreTest {
	@Test
	void handlesMethodArgumentNotValid() throws NoSuchMethodException {
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "obj");
		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
		ResponseEntity<Map<String, Object>> resp = handler.handleValidation(ex);
		assertEquals(400, resp.getStatusCode().value());
		assertEquals("validation_error", resp.getBody().get("error"));
	}
}