package com.example.newsapi.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerWebClientTest {

    @Test
    void handlesWebClientResponseException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        WebClientResponseException ex = new WebClientResponseException(402, "Payment Required", null, "quota".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        ResponseEntity<Map<String, Object>> resp = handler.handleWebClientResponseException(ex);
        assertEquals(HttpStatus.PAYMENT_REQUIRED, resp.getStatusCode());
        assertEquals(402, resp.getBody().get("status"));
    }
}

