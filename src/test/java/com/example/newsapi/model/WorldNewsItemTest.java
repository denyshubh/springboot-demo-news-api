package com.example.newsapi.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldNewsItemTest {

    @Test
    void gettersAndSetters_coverAllFields() {
        WorldNewsItem item = new WorldNewsItem();
        item.setId(224767206L);
        item.setTitle("Title");
        item.setText("Text");
        item.setSummary("Summary");
        item.setUrl("https://example.com");
        item.setImage("https://example.com/image.jpg");
        item.setVideo("https://example.com/video.mp4");
        item.setPublish_date("2024-05-29 00:10:48");
        item.setAuthor("John Doe");
        item.setAuthors(List.of("John Doe", "Jane Doe"));

        assertEquals(224767206L, item.getId());
        assertEquals("Title", item.getTitle());
        assertEquals("Text", item.getText());
        assertEquals("Summary", item.getSummary());
        assertEquals("https://example.com", item.getUrl());
        assertEquals("https://example.com/image.jpg", item.getImage());
        assertEquals("https://example.com/video.mp4", item.getVideo());
        assertEquals("2024-05-29 00:10:48", item.getPublish_date());
        assertEquals("John Doe", item.getAuthor());
        assertEquals(List.of("John Doe", "Jane Doe"), item.getAuthors());
    }
}

