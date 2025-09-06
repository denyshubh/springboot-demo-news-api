package com.example.newsapi.service;

import com.example.newsapi.client.WorldNewsClient;
import com.example.newsapi.dto.TopNewsResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Service
public class TopNewsService {
	private final WorldNewsClient client;

	public TopNewsService(WorldNewsClient client) {
		this.client = client;
	}

	public WorldNewsClient.TopNewsResult getTopNews(String sourceCountry,
	                                               String language,
	                                               LocalDate date,
	                                               boolean headlinesOnly,
	                                               Integer maxNewsPerCluster) {
		if (!StringUtils.hasText(sourceCountry) || sourceCountry.length() != 2) {
			throw new IllegalArgumentException("source-country must be a 2-letter ISO 3166 code");
		}
		if (!StringUtils.hasText(language) || language.length() != 2) {
			throw new IllegalArgumentException("language must be a 2-letter ISO 639-1 code");
		}
		if (maxNewsPerCluster != null && maxNewsPerCluster <= 0) {
			throw new IllegalArgumentException("max-news-per-cluster must be positive");
		}
		WorldNewsClient.TopNewsResult result = client.fetchTopNews(sourceCountry.toLowerCase(), language.toLowerCase(), date, headlinesOnly, maxNewsPerCluster);
		TopNewsResponse body = result.body();
		if (body == null) {
			throw new IllegalStateException("Empty response from WorldNews API");
		}
		HttpHeaders headers = result.quotaHeaders();
		HttpHeaders copy = new HttpHeaders();
		copy.addAll(headers);
		return new WorldNewsClient.TopNewsResult(body, copy);
	}
}