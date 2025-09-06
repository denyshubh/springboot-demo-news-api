package com.example.newsapi.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestClientConfigTest {
	@Test
	void createsBuilderAndClient() {
		RestClientConfig config = new RestClientConfig();
		RestClient.Builder builder = config.restClientBuilder();
		WorldNewsProperties props = new WorldNewsProperties();
		props.setBaseUrl("https://test");
		RestClient client = config.worldNewsRestClient(builder, props);
		assertNotNull(builder);
		assertNotNull(client);
	}
}