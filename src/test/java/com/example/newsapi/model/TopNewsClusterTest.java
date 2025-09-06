package com.example.newsapi.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopNewsClusterTest {

    @Test
    void gettersAndSetters_coverNullAndNonNull() {
        TopNewsCluster cluster = new TopNewsCluster();

        // Null branch
        cluster.setNews(null);
        assertNull(cluster.getNews());

        // Non-null branch
        WorldNewsItem item = new WorldNewsItem();
        item.setId(1);
        cluster.setNews(List.of(item));
        assertNotNull(cluster.getNews());
        assertEquals(1, cluster.getNews().size());
    }
}

