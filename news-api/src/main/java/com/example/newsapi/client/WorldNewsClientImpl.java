package com.example.newsapi.client;

import com.example.newsapi.config.WorldNewsProperties;
import com.example.newsapi.dto.TopNewsResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@Component
public class WorldNewsClientImpl implements WorldNewsClient {
	private final RestClient restClient;
	private final WorldNewsProperties properties;

	public WorldNewsClientImpl(RestClient worldNewsRestClient, WorldNewsProperties properties) {
		this.restClient = worldNewsRestClient;
		this.properties = properties;
	}

	@Override
	public TopNewsResult fetchTopNews(String sourceCountry, String language, LocalDate date, boolean headlinesOnly, Integer maxNewsPerCluster) {
		ResponseEntity<TopNewsResponse> response = restClient.get()
				.uri(builder -> {
					UriComponentsBuilder ucb = UriComponentsBuilder.fromUri(builder.build());
					ucb.path("/top-news")
						.queryParam("source-country", sourceCountry)
						.queryParam("language", language)
						.queryParam("headlines-only", headlinesOnly)
						.queryParam("api-key", properties.getApiKey());
					if (date != null) {
						ucb.queryParam("date", date);
					}
					if (maxNewsPerCluster != null) {
						ucb.queryParam("max-news-per-cluster", maxNewsPerCluster);
					}
					return ucb.build(true).toUri();
				})
				.retrieve()
				.toEntity(TopNewsResponse.class);

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-API-Quota-Request", response.getHeaders().getFirst("X-API-Quota-Request"));
		headers.add("X-API-Quota-Used", response.getHeaders().getFirst("X-API-Quota-Used"));
		headers.add("X-API-Quota-Left", response.getHeaders().getFirst("X-API-Quota-Left"));
		return new TopNewsResult(response.getBody(), headers);
	}
}