package com.example.newsapi.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopNewsResponseTest {

    @Test
    void gettersAndSetters_coverNullAndNonNull() {
        TopNewsResponse resp = new TopNewsResponse();

        // Null branch
        resp.setTopNews(null);
        assertNull(resp.getTopNews());

        // Non-null branch
        TopNewsCluster cluster = new TopNewsCluster();
        cluster.setNews(List.of());
        resp.setTopNews(List.of(cluster));
        assertNotNull(resp.getTopNews());
        assertEquals(1, resp.getTopNews().size());

        // Simple scalars
        resp.setLanguage("en");
        resp.setCountry("us");
        assertEquals("en", resp.getLanguage());
        assertEquals("us", resp.getCountry());
    }
}

