package com.example.newsapi.client;

import com.example.newsapi.config.WorldNewsProperties;
import com.example.newsapi.dto.TopNewsResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WorldNewsClientImplTest {
	private MockWebServer server;

	@BeforeEach
	void setUp() throws IOException {
		server = new MockWebServer();
		server.start();
	}

	@AfterEach
	void tearDown() throws IOException {
		server.shutdown();
	}

	@Test
	void buildsUriWithAllParamsAndMapsHeaders() throws InterruptedException {
		String json = "{\n  \"top_news\": [], \n  \"language\": \"en\", \n  \"country\": \"us\"\n}";
		MockResponse response = new MockResponse()
				.setResponseCode(200)
				.setHeader("Content-Type", "application/json")
				.setHeader("X-API-Quota-Request", "1")
				.setHeader("X-API-Quota-Used", "2")
				.setHeader("X-API-Quota-Left", "3")
				.setBody(json);
		server.enqueue(response);

		RestClient restClient = RestClient.builder().baseUrl(server.url("/").toString()).build();
		WorldNewsProperties props = new WorldNewsProperties();
		props.setApiKey("abc");
		WorldNewsClientImpl client = new WorldNewsClientImpl(restClient, props);

		TopNewsResponse result = client.fetchTopNews("us", "en", LocalDate.of(2024,5,29), true, 2).body();
		assertNotNull(result);
		assertEquals("en", result.language());
		assertEquals("us", result.country());

		String path = server.takeRequest().getPath();
		assertNotNull(path);
		URI uri = URI.create("http://host" + path);
		Map<String, String> params = Arrays.stream(uri.getQuery().split("&"))
				.map(kv -> kv.split("=", 2))
				.collect(Collectors.toMap(a -> URLDecoder.decode(a[0], StandardCharsets.UTF_8), a -> URLDecoder.decode(a[1], StandardCharsets.UTF_8)));
		assertEquals("us", params.get("source-country"));
		assertEquals("en", params.get("language"));
		assertEquals("true", params.get("headlines-only"));
		assertEquals("2", params.get("max-news-per-cluster"));
		assertEquals("abc", params.get("api-key"));
		assertEquals("2024-05-29", params.get("date"));
	}

	@Test
	void omitsOptionalParamsWhenNull() throws InterruptedException {
		String json = "{\n  \"top_news\": [], \n  \"language\": \"en\", \n  \"country\": \"us\"\n}";
		server.enqueue(new MockResponse().setResponseCode(200).setHeader("Content-Type", "application/json").setBody(json));

		RestClient restClient = RestClient.builder().baseUrl(server.url("/").toString()).build();
		WorldNewsProperties props = new WorldNewsProperties();
		props.setApiKey("abc");
		WorldNewsClientImpl client = new WorldNewsClientImpl(restClient, props);

		TopNewsResponse result = client.fetchTopNews("us", "en", null, false, null).body();
		assertNotNull(result);

		String path = server.takeRequest().getPath();
		URI uri = URI.create("http://host" + path);
		Map<String, String> params = Arrays.stream(uri.getQuery().split("&"))
				.map(kv -> kv.split("=", 2))
				.collect(Collectors.toMap(a -> URLDecoder.decode(a[0], StandardCharsets.UTF_8), a -> URLDecoder.decode(a[1], StandardCharsets.UTF_8)));
		assertEquals("us", params.get("source-country"));
		assertEquals("en", params.get("language"));
		assertEquals("false", params.get("headlines-only"));
		assertEquals("abc", params.get("api-key"));
	}
}