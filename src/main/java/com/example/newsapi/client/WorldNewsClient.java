package com.example.newsapi.client;

import com.example.newsapi.config.WorldNewsProperties;
import com.example.newsapi.model.TopNewsResponse;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
public class WorldNewsClient {

    private final WebClient webClient;
    private final WorldNewsProperties properties;

    @SuppressFBWarnings(value = {"EI_EXPOSE_REP2"}, justification = "WebClient and properties are framework-managed beans; safe to store references.")
    public WorldNewsClient(WebClient worldNewsWebClient, WorldNewsProperties properties) {
        this.webClient = worldNewsWebClient;
        this.properties = properties;
    }

    public TopNewsResponse getTopNews(String sourceCountry, String language, LocalDate date,
                                      Boolean headlinesOnly, Integer maxNewsPerCluster) {
        try {
            WebClient.RequestHeadersUriSpec<?> uriSpec = webClient.get();
            WebClient.RequestHeadersSpec<?> headersSpec = uriSpec.uri(uriBuilder -> {
                var b = uriBuilder
                        .path("/top-news")
                        .queryParam("source-country", sourceCountry)
                        .queryParam("language", language);
                if (date != null) {
                    b.queryParam("date", date);
                }
                if (headlinesOnly != null) {
                    b.queryParam("headlines-only", headlinesOnly);
                }
                if (maxNewsPerCluster != null) {
                    b.queryParam("max-news-per-cluster", maxNewsPerCluster);
                }
                b.queryParam("api-key", properties.getApiKey());
                return b.build();
            })
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

            return headersSpec.retrieve()
                    .bodyToMono(TopNewsResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw e;
        }
    }
}

