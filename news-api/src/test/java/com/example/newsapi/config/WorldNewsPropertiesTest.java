package com.example.newsapi.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldNewsPropertiesTest {
	@Test
	void gettersAndSettersWork() {
		WorldNewsProperties p = new WorldNewsProperties();
		p.setBaseUrl("https://x");
		p.setApiKey("k");
		assertEquals("https://x", p.getBaseUrl());
		assertEquals("k", p.getApiKey());
	}
}