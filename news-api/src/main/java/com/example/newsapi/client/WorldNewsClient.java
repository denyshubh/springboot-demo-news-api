package com.example.newsapi.client;

import com.example.newsapi.dto.TopNewsResponse;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;

public interface WorldNewsClient {
	record TopNewsResult(TopNewsResponse body, HttpHeaders quotaHeaders) {}

	TopNewsResult fetchTopNews(String sourceCountry,
	                          String language,
	                          LocalDate date,
	                          boolean headlinesOnly,
	                          Integer maxNewsPerCluster);
}