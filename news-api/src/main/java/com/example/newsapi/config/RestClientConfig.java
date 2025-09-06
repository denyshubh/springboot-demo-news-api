package com.example.newsapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
	@Bean
	public RestClient.Builder restClientBuilder() {
		return RestClient.builder();
	}

	@Bean
	public RestClient worldNewsRestClient(RestClient.Builder builder, WorldNewsProperties properties) {
		return builder
				.baseUrl(properties.getBaseUrl())
				.build();
	}
}