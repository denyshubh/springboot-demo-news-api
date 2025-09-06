package com.example.newsapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TopNewsResponse(
		@JsonProperty("top_news") List<NewsCluster> topNews,
		String language,
		String country
) {
	@JsonIgnoreProperties(ignoreUnknown = true)
	public record NewsCluster(
			List<NewsItem> news
	) { }

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record NewsItem(
			long id,
			String title,
			String text,
			String summary,
			String url,
			String image,
			String video,
			@JsonProperty("publish_date") LocalDateTime publishDate,
			String author,
			List<String> authors
	) { }
}