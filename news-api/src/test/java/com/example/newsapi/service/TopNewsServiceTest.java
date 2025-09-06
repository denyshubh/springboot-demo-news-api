package com.example.newsapi.service;

import com.example.newsapi.client.WorldNewsClient;
import com.example.newsapi.dto.TopNewsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopNewsServiceTest {
	private WorldNewsClient client;
	private TopNewsService service;

	@BeforeEach
	void setup() {
		client = Mockito.mock(WorldNewsClient.class);
		service = new TopNewsService(client);
	}

	@Test
	void rejectsInvalidCountry() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
				service.getTopNews("usa", "en", null, false, null)
		);
		assertTrue(ex.getMessage().contains("source-country"));
	}

	@Test
	void rejectsInvalidLanguage() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
				service.getTopNews("us", "eng", null, false, null)
		);
		assertTrue(ex.getMessage().contains("language"));
	}

	@Test
	void rejectsInvalidMaxPerCluster() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
				service.getTopNews("us", "en", null, false, 0)
		);
		assertTrue(ex.getMessage().contains("max-news-per-cluster"));
	}

	@Test
	void returnsBodyAndHeaders() {
		TopNewsResponse body = new TopNewsResponse(List.of(), "en", "us");
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-API-Quota-Request", "1");
		Mockito.when(client.fetchTopNews("us", "en", null, false, null))
				.thenReturn(new WorldNewsClient.TopNewsResult(body, headers));

		WorldNewsClient.TopNewsResult result = service.getTopNews("us", "en", null, false, null);
		assertNotNull(result.body());
		assertEquals("en", result.body().language());
		assertEquals("1", result.quotaHeaders().getFirst("X-API-Quota-Request"));
	}
}