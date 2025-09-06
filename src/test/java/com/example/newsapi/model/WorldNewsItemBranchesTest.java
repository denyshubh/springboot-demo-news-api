package com.example.newsapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldNewsItemBranchesTest {

    @Test
    void authorsNullAndNonNullBranches() {
        WorldNewsItem item = new WorldNewsItem();
        item.setAuthors(null);
        assertNull(item.getAuthors());

        item.setAuthors(java.util.List.of("a"));
        assertEquals(1, item.getAuthors().size());
    }
}

