package com.example.newsapi.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {
	@Test
	void handlesIllegalArgument() {
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		ResponseEntity<Map<String, Object>> resp = handler.handleIllegalArgument(new IllegalArgumentException("bad"));
		assertEquals(400, resp.getStatusCode().value());
		assertEquals("validation_error", resp.getBody().get("error"));
	}

	@Test
	void handlesGeneric() {
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		ResponseEntity<Map<String, Object>> resp = handler.handleGeneric(new RuntimeException("oops"));
		assertEquals(500, resp.getStatusCode().value());
		assertEquals("internal_error", resp.getBody().get("error"));
	}
}