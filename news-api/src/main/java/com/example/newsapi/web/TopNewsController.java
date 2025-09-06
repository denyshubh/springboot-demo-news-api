package com.example.newsapi.web;

import com.example.newsapi.client.WorldNewsClient;
import com.example.newsapi.dto.TopNewsResponse;
import com.example.newsapi.service.TopNewsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/top-news")
@Validated
public class TopNewsController {
	private final TopNewsService service;

	public TopNewsController(TopNewsService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<TopNewsResponse> getTopNews(
			@RequestParam("source-country") String sourceCountry,
			@RequestParam("language") String language,
			@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(value = "headlines-only", defaultValue = "false") boolean headlinesOnly,
			@RequestParam(value = "max-news-per-cluster", required = false) Integer maxNewsPerCluster
	) {
		WorldNewsClient.TopNewsResult result = service.getTopNews(sourceCountry, language, date, headlinesOnly, maxNewsPerCluster);
		HttpHeaders headers = new HttpHeaders();
		headers.addAll(result.quotaHeaders());
		return ResponseEntity.ok().headers(headers).body(result.body());
	}
}