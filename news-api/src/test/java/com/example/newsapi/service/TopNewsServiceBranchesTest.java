package com.example.newsapi.service;

import com.example.newsapi.client.WorldNewsClient;
import com.example.newsapi.dto.TopNewsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopNewsServiceBranchesTest {
	private WorldNewsClient client;
	private TopNewsService service;

	@BeforeEach
	void setup() {
		client = Mockito.mock(WorldNewsClient.class);
		service = new TopNewsService(client);
	}

	@Test
	void rejectsEmptyCountry() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
				service.getTopNews("", "en", null, false, null)
		);
		assertTrue(ex.getMessage().contains("source-country"));
	}

	@Test
	void rejectsEmptyLanguage() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
				service.getTopNews("us", "", null, false, null)
		);
		assertTrue(ex.getMessage().contains("language"));
	}

	@Test
	void acceptsPositiveMaxPerCluster() {
		TopNewsResponse body = new TopNewsResponse(List.of(), "en", "us");
		Mockito.when(client.fetchTopNews("us", "en", null, false, 1))
				.thenReturn(new WorldNewsClient.TopNewsResult(body, new HttpHeaders()));
		assertDoesNotThrow(() -> service.getTopNews("us", "en", null, false, 1));
	}
}