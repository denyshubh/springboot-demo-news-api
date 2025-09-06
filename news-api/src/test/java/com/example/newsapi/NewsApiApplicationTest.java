package com.example.newsapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NewsApiApplicationTest {
	@Test
	void mainStarts() {
		assertDoesNotThrow(() -> NewsApiApplication.main(new String[]{}));
	}
}