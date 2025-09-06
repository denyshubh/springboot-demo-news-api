package com.example.newsapi.web;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @Test
    void handlesConstraintViolation() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<Map<String, Object>> resp = handler.handleValidation(new ConstraintViolationException("bad", null));
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals(400, resp.getBody().get("status"));
    }
}

