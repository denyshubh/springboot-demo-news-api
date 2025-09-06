package com.example.newsapi.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalExceptionHandlerValidationBranchTest {
	@Test
	void returnsDefaultMessagesWhenPresent() throws Exception {
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "obj");
		bindingResult.addError(new ObjectError("obj", "bad"));
		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
		ResponseEntity<Map<String, Object>> resp = handler.handleValidation(ex);
		assertEquals(400, resp.getStatusCode().value());
		assertEquals("validation_error", resp.getBody().get("error"));
		assertTrue(((String) resp.getBody().get("message")).contains("bad"));
	}
}