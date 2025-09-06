package com.example.newsapi.client;

import com.example.newsapi.config.WebClientConfig;
import com.example.newsapi.config.WorldNewsProperties;
import com.example.newsapi.model.TopNewsResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WorldNewsClientWireMockTest {

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void startServer() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @AfterAll
    static void stopServer() {
        wireMockServer.stop();
    }

    private WorldNewsClient createClient() {
        WorldNewsProperties properties = new WorldNewsProperties();
        properties.setBaseUrl("http://localhost:" + wireMockServer.port());
        properties.setApiKey("test-key");
        properties.setTimeoutMs(3000);
        try {
            WebClient webClient = new WebClientConfig().worldNewsWebClient(properties);
            return new WorldNewsClient(webClient, properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getTopNews_success() {
        String body = "{\n" +
                "  \"top_news\": [{ \"news\": [{ \"id\": 1, \"title\": \"Hello\" }] }],\n" +
                "  \"language\": \"en\",\n" +
                "  \"country\": \"us\"\n" +
                "}";

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/top-news"))
                .withQueryParam("source-country", WireMock.equalTo("us"))
                .withQueryParam("language", WireMock.equalTo("en"))
                .withQueryParam("api-key", WireMock.equalTo("test-key"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(body)));

        WorldNewsClient client = createClient();
        TopNewsResponse response = client.getTopNews("us", "en", null, null, null);
        assertNotNull(response);
        assertEquals("en", response.getLanguage());
        assertEquals("us", response.getCountry());
        assertEquals(1, response.getTopNews().get(0).getNews().size());
        assertEquals("Hello", response.getTopNews().get(0).getNews().get(0).getTitle());
    }

    @Test
    void getTopNews_propagatesError() {
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/top-news"))
                .withQueryParam("source-country", WireMock.equalTo("us"))
                .withQueryParam("language", WireMock.equalTo("en"))
                .withQueryParam("api-key", WireMock.equalTo("test-key"))
                .willReturn(WireMock.aResponse().withStatus(402).withBody("quota exceeded")));

        WorldNewsClient client = createClient();
        assertThrows(RuntimeException.class, () -> client.getTopNews("us", "en", LocalDate.now(), true, 1));
    }
}

