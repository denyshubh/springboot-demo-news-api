package com.example.newsapi.web;

import com.example.newsapi.client.WorldNewsClient;
import com.example.newsapi.model.TopNewsResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/news")
@Validated
public class TopNewsController {

    private final WorldNewsClient worldNewsClient;

    public TopNewsController(WorldNewsClient worldNewsClient) {
        this.worldNewsClient = worldNewsClient;
    }

    @GetMapping("/top")
    public ResponseEntity<TopNewsResponse> getTopNews(
            @RequestParam("source-country") @NotBlank String sourceCountry,
            @RequestParam("language") @NotBlank String language,
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "headlines-only", required = false) Boolean headlinesOnly,
            @RequestParam(value = "max-news-per-cluster", required = false)
            @Min(1) @Max(50) Integer maxNewsPerCluster
    ) {
        TopNewsResponse response = worldNewsClient.getTopNews(sourceCountry, language, date, headlinesOnly, maxNewsPerCluster);
        return ResponseEntity.ok(response);
    }
}

